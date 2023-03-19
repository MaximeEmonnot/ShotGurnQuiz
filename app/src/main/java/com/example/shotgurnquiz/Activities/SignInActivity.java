package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

        // Instanciation du DatabaseHelper (Singleton)
        DatabaseHelper db = DatabaseHelper.GetInstance(this);

        // Références aux éléments du layout
        Button signin = (Button) findViewById(R.id.btn_sign_in);
        Button signup = (Button) findViewById(R.id.btn_sign_up);
        EditText username = (EditText) findViewById(R.id.edit_text_sign_in_username);
        EditText password = (EditText) findViewById(R.id.edit_text_sign_in_password);
        TextView errorField = (TextView) findViewById(R.id.sign_in_errorField);

        // OnClick du bouton SignIn
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
                // Si tous les champs ne sont pas remplis, on affiche un message d'erreur
                if(usernameText.isEmpty()){
                    username.setError(getResources().getText(R.string.empty_field));
                    username.requestFocus();
                }
                else if(passwordText.isEmpty()){
                    password.setError(getResources().getText(R.string.empty_field));
                    password.requestFocus();
                }
                else {
                    // On vérifie l'existence d'un utilisateur ayant le même nom. Si inexistant, on affiche un message d'erreur
                    User user = db.FindUserByUsername(usernameText);
                    if(user == null)
                        errorField.setText(getResources().getText(R.string.incorrect_username));
                    else{
                        // Sinon, on vérifie si le mot de passe est correct. On affiche un message d'erreur le cas échéant
                        if (!user.GetPassword().equals(passwordText))
                            errorField.setText(getResources().getText(R.string.incorrect_password));
                        else{
                            // Si le mot de passe est effectivement correct, on démarre l'activité QuizListActivity avec comme information l'état connecté et l'ID utilisateur
                            Intent intent = new Intent(SignInActivity.this, QuizListActivity.class);
                            intent.putExtra("bIsConnected", true);
                            intent.putExtra("userId", user.GetID());
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        // OnClick du bouton SignUp : On lancera l'activité SignUpActivity
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.slidein_from_bottom, R.anim.slideout_to_top);
            }
        });
    }

    // Animation lors de l'arrêt de l'activité
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slidein_from_right, R.anim.slideout_to_left);
    }
}