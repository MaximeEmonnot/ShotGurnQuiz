package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.shotgurnquiz.Database.DatabaseHelper;
import com.example.shotgurnquiz.Database.Tables.Score;
import com.example.shotgurnquiz.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        DatabaseHelper db = DatabaseHelper.GetInstance(this);

        TextView test = (TextView) findViewById(R.id.test_leaderboard);

        List<Score> scores = db.GetScores();
        test.setText(Integer.toString(scores.size()));
    }
}