package com.example.shotgurnquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shotgurnquiz.Database.DatabaseHelper;
import com.example.shotgurnquiz.Database.Tables.User;

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

                if(usernameText.isEmpty()) {
                    username.setError(getResources().getText(R.string.empty_field));
                    username.requestFocus();
                }
                else if(emailText.isEmpty()) {
                    email.setError(getResources().getText(R.string.empty_field));
                    email.requestFocus();
                }
                else if(passwordText.isEmpty()) {
                    password.setError(getResources().getText(R.string.empty_field));
                    password.requestFocus();
                }
                else if(!emailText.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    email.setError(getResources().getText(R.string.email_not_valid));
                    email.requestFocus();
                }
                else if(!emailText.equals(confirmEmailText)) {
                    confirmEmail.setError(getResources().getText(R.string.fields_not_match));
                    confirmEmail.requestFocus();
                }
                else if(!passwordText.equals(confirmPasswordText)) {
                    confirmPassword.setError(getResources().getText(R.string.fields_not_match));
                    confirmPassword.requestFocus();
                }
                else{
                    if(db.FindUserByUsername(usernameText) != null) {
                        username.setError(getResources().getText(R.string.username_already_exist));
                        username.requestFocus();
                    }
                    else if (db.FindUserByEmail(emailText) != null) {
                        email.setError(getResources().getText(R.string.email_already_exist));
                        email.requestFocus();
                    }
                    else
                        db.CreateNewUser(usernameText, emailText, passwordText);
                }
            }
        });
    }
}