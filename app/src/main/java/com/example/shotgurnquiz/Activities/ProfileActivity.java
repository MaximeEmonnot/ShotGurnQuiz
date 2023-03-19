package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shotgurnquiz.Database.DatabaseHelper;
import com.example.shotgurnquiz.Database.Tables.User;
import com.example.shotgurnquiz.Fragments.ChangePasswordDialogFragment;
import com.example.shotgurnquiz.Fragments.QuizInfoDialogFragment;
import com.example.shotgurnquiz.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Récupération de l'ID utilisateur via Intent
        int userId = getIntent().getExtras().getInt("userId");

        // Instanciation du DatabaseHelper (Singleton)
        DatabaseHelper db = DatabaseHelper.GetInstance(this);

        // Récupération de l'utilisateur en base de données (via ID)
        User user = db.FindUserByID(userId);

        // Références aux élémetns du layout
        TextView textViewUsername = (TextView) findViewById(R.id.profile_username);
        TextView textViewEmail = (TextView) findViewById(R.id.profile_email);
        Button buttonChangePassword = (Button) findViewById(R.id.quiz_profile_btn_change_password);

        // On affiche les informations de l'utilisateur
        textViewUsername.setText(user.GetUsername());
        textViewEmail.setText(user.GetEmail());

        // OnClick du bouton ChangePassword : On ouvrira le ChangePasswordDialogFragment
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordDialogFragment changePasswordDialogFragment = ChangePasswordDialogFragment.newInstance(userId);
                changePasswordDialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        });

    }
}