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

        int userId = getIntent().getExtras().getInt("userId");

        DatabaseHelper db = DatabaseHelper.GetInstance(this);

        User user = db.FindUserByID(userId);

        TextView textViewUsername = (TextView) findViewById(R.id.profile_username);
        TextView textViewEmail = (TextView) findViewById(R.id.profile_email);
        Button buttonChangePassword = (Button) findViewById(R.id.quiz_profile_btn_change_password);

        textViewUsername.setText(user.GetUsername());
        textViewEmail.setText(user.GetEmail());

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordDialogFragment changePasswordDialogFragment = new ChangePasswordDialogFragment(userId);
                changePasswordDialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        });

    }
}