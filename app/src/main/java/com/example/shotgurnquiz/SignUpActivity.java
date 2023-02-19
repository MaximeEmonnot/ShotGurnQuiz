package com.example.shotgurnquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shotgurnquiz.Database.DatabaseHelper;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        DatabaseHelper db = DatabaseHelper.GetInstance(this);

        Button createAccount = (Button) findViewById(R.id.btn_create_account);
        EditText username = (EditText) findViewById(R.id.edit_text_sign_up_username);
        EditText email = (EditText) findViewById(R.id.edit_text_sign_up_email);
        EditText confirmEmail = (EditText) findViewById(R.id.edit_text_sign_up_confirm_email);
        EditText password = (EditText) findViewById(R.id.edit_text_sign_up_password);
        EditText confirmPassword = (EditText) findViewById(R.id.edit_text_sign_up_confirm_password);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = username.getText().toString();
                String emailText = email.getText().toString();
                String confirmEmailText = confirmEmail.getText().toString();
                String passwordText = password.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();

                if(!usernameText.isEmpty() && !emailText.isEmpty() && !passwordText.isEmpty()){
                    if(db.FindUserByUsername(usernameText) == null && db.FindUserByEmail(emailText) == null){
                        db.CreateNewUser(usernameText, emailText, passwordText);
                    }
                }
            }
        });
    }
}