package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shotgurnquiz.Database.DatabaseHelper;
import com.example.shotgurnquiz.Database.Tables.Score;
import com.example.shotgurnquiz.R;

public class QuizSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_summary);

        Bundle bundle = getIntent().getExtras();

        int score = bundle.getInt("score");
        int questionCount = bundle.getInt("questionCount");
        String quizTitle = bundle.getString("quizTitle");
        int quizId = bundle.getInt("quizId");
        int userId = bundle.getInt("userId");

        TextView textViewTitle = (TextView) findViewById(R.id.quiz_summary_title);
        TextView textViewScoreCount = (TextView) findViewById(R.id.quiz_summary_score_count);
        TextView textViewScoreMax = (TextView) findViewById(R.id.quiz_summary_score_max);
        TextView textViewPointsCount = (TextView) findViewById(R.id.quiz_summary_points_count);
        Button buttonQuizList = (Button) findViewById(R.id.quiz_summary_btn_quiz_list);

        //Ajout du score en bd ou modification si deja existant et calcul des points

        textViewTitle.setText(quizTitle);
        textViewScoreCount.setText(Integer.toString(score));
        textViewScoreMax.setText(Integer.toString(questionCount));

        DatabaseHelper db = DatabaseHelper.GetInstance(this);
        Score dbScore = db.GetUserScore(quizId, userId);
        int points = 0;

        if(dbScore == null || dbScore.GetScore() < score){
            db.AddNewScore(quizId, userId, score);
            points = score - (dbScore == null ? 0 : dbScore.GetScore());
        }

        textViewPointsCount.setText(Integer.toString(points)+ " " + getString(R.string.points_added_season_points));

        buttonQuizList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}