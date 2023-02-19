package com.example.shotgurnquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class QuizListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        RecyclerView recyclerViewHorizontal = findViewById(R.id.recycle_view_horizontal);
        RecyclerView recyclerViewVertical1 = findViewById(R.id.recycle_view_vertical1);
        RecyclerView recyclerViewVertical2 = findViewById(R.id.recycle_view_vertical2);

        ArrayList<QuizCardModel> quizCards = new ArrayList<QuizCardModel>();

        quizCards.add(new QuizCardModel("Quiz1"));
        quizCards.add(new QuizCardModel("Quiz2"));
        quizCards.add(new QuizCardModel("Quiz3"));
        quizCards.add(new QuizCardModel("Quiz4"));
        quizCards.add(new QuizCardModel("Quiz5"));
        quizCards.add(new QuizCardModel("Quiz6"));
        quizCards.add(new QuizCardModel("Quiz7"));
        QuizCard_RecyclerViewAdapter adapter = new QuizCard_RecyclerViewAdapter(this, quizCards);

        recyclerViewHorizontal.setAdapter(adapter);
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewVertical1.setAdapter(adapter);
        recyclerViewVertical1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerViewVertical2.setAdapter(adapter);
        recyclerViewVertical2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}