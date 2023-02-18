package com.example.shotgurnquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.shotgurnquiz.Database.DatabaseHelper;
import com.example.shotgurnquiz.Database.Tables.User;

public class QuizListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        DatabaseHelper db = DatabaseHelper.GetInstance(this);

        TextView textView = (TextView) findViewById(R.id.quizlist_text);

        String text = "";

        for (User user : db.GetAllUser()) text += user.toString() + "\n";

        textView.setText(text);
    }
}