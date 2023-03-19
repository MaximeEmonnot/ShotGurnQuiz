package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.shotgurnquiz.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Références aux éléments du layout et application des différentes animations
        Animation popUpAnimation = AnimationUtils.loadAnimation(this, R.anim.pop_up);
        TextView titleTextView = findViewById(R.id.textview_title);
        titleTextView.startAnimation(popUpAnimation);

        Animation slideInFromLeftAnimation = AnimationUtils.loadAnimation(this, R.anim.slidein_from_left);
        Button signInButton = findViewById(R.id.btn_welcome_sign_in);
        signInButton.startAnimation(slideInFromLeftAnimation);

        Animation slideInFromRightAnimation = AnimationUtils.loadAnimation(this, R.anim.slidein_from_right);
        Button playOfflineButton = findViewById(R.id.btn_play_offline);
        playOfflineButton.startAnimation(slideInFromRightAnimation);

        // OnClick du bouton SignIn : on lancera l'activité SignInActivity
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, SignInActivity.class));
                overridePendingTransition(R.anim.slidein_from_left, R.anim.slideout_to_right);
            }
        });

        // OnClick du bouton PlayOffline : On lancera l'activité QuizListActivity avec comme information l'état non-connecté
        playOfflineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, QuizListActivity.class);
                intent.putExtra("bIsConnected", false);
                startActivity(intent);
            }
        });
    }
}