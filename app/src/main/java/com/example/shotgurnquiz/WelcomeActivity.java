package com.example.shotgurnquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected  void onStart() {
        super.onStart();

        TextView titleTextView = findViewById(R.id.textview_title);
        Animation popUpAnimation = AnimationUtils.loadAnimation(this, R.anim.pop_up);
        titleTextView.startAnimation(popUpAnimation);

        Button signInButton = findViewById(R.id.btn_welcome_sign_in);
        Animation slideInFromLeftAnimation = AnimationUtils.loadAnimation(this, R.anim.slidein_from_left);
        signInButton.startAnimation(slideInFromLeftAnimation);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, SignInActivity.class));
            }
        });

        Button playOfflineButton = findViewById(R.id.btn_play_offline);
        Animation slideInFromRightAnimation = AnimationUtils.loadAnimation(this, R.anim.slidein_from_right);
        playOfflineButton.startAnimation(slideInFromRightAnimation);

        playOfflineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, QuizListActivity.class));
            }
        });
    }
}