package com.example.shotgurnquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shotgurnquiz.Database.DatabaseHelper;
import com.example.shotgurnquiz.Database.Tables.User;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        DatabaseHelper db = DatabaseHelper.GetInstance(this);

        Button signin = (Button) findViewById(R.id.btn_sign_in);
        EditText username = (EditText) findViewById(R.id.edit_text_sign_in_username);
        EditText password = (EditText) findViewById(R.id.edit_text_sign_in_password);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
                if (!usernameText.isEmpty() && !passwordText.isEmpty()) {
                    User user = db.FindUserByUsername(usernameText);
                    if (user != null) {
                        if (user.GetPassword().equals(passwordText)) {
                            Intent intent = new Intent(SignInActivity.this, QuizListActivity.class);
                            startActivity(intent);
                        }
                    }
                    else {
                        db.CreateNewUser(usernameText, passwordText);
                        Intent intent = new Intent(SignInActivity.this, QuizListActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}