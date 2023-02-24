package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shotgurnquiz.Database.DatabaseHelper;
import com.example.shotgurnquiz.Database.Tables.User;
import com.example.shotgurnquiz.R;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        DatabaseHelper db = DatabaseHelper.GetInstance(this);

        Button signin = (Button) findViewById(R.id.btn_sign_in);
        EditText username = (EditText) findViewById(R.id.edit_text_sign_in_username);
        EditText password = (EditText) findViewById(R.id.edit_text_sign_in_password);
        TextView errorField = (TextView) findViewById(R.id.sign_in_errorField);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
                if(usernameText.isEmpty()){
                    username.setError(getResources().getText(R.string.empty_field));
                    username.requestFocus();
                }
                else if(passwordText.isEmpty()){
                    password.setError(getResources().getText(R.string.empty_field));
                    password.requestFocus();
                }
                else {
                    User user = db.FindUserByUsername(usernameText);
                    if(user == null)
                        errorField.setText(getResources().getText(R.string.incorrect_username));
                    else{
                        if (!user.GetPassword().equals(passwordText))
                            errorField.setText(getResources().getText(R.string.incorrect_password));
                        else{
                            startActivity(new Intent(SignInActivity.this, TestActivity.class));
                        }
                    }
                }
            }
        });


        Button signup = (Button) findViewById(R.id.btn_sign_up);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.slidein_from_bottom, R.anim.slideout_to_top);
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slidein_from_right, R.anim.slideout_to_left);
    }
}