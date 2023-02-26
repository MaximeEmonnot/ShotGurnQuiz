package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shotgurnquiz.Models.QuestionModel;
import com.example.shotgurnquiz.R;
import com.example.shotgurnquiz.RecyclerViewConfigs.Question_RecyclerViewAdapter;
import com.example.shotgurnquiz.RecyclerViewConfigs.RecyclerView_ItemClickListener;
import com.example.shotgurnquiz.RecyclerViewConfigs.RecyclerView_SpacesItemDecoration;

import java.util.ArrayList;

public class AddQuizActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_quiz);

            questions = new ArrayList<QuestionModel>();

            questions.add(new QuestionModel("This is my question.","Answer A", "Answer B", true));

            recyclerViewQuestions = findViewById(R.id.recycler_view_questions);

            recyclerViewQuestions.setAdapter(new Question_RecyclerViewAdapter(this, questions));
            recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewQuestions.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.HORIZONTAL));

            EditText questionTitle = (EditText) findViewById(R.id.question_title);
            EditText questionAnswerA = (EditText) findViewById(R.id.answerA);
            EditText questionAnswerB = (EditText) findViewById(R.id.answerB);

            Button save = (Button) findViewById(R.id.btn_save);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = questionTitle.getText().toString();
                    String answerA = questionAnswerA.getText().toString();
                    String answerB = questionAnswerB.getText().toString();
                    boolean correctAnswer = true;
                    questions.set(currentIndex, new QuestionModel(title, answerA, answerB, correctAnswer));
                    updateRecyclerView();
                }
            });

            recyclerViewQuestions.addOnItemTouchListener(
                    new RecyclerView_ItemClickListener(new RecyclerView_ItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            if(position == questions.size()){
                                questions.add(new QuestionModel("This is my question.", "Answer A", "Answer B", true));
                                updateRecyclerView();
                            }
                            QuestionModel question = questions.get(position);
                            questionTitle.setText(question.getTitle());
                            questionAnswerA.setText(question.getAnswerA());
                            questionAnswerB.setText(question.getAnswerB());
                            currentIndex = position;
                        }
                    })
            );
        }

        private void updateRecyclerView(){
            recyclerViewQuestions.setAdapter(new Question_RecyclerViewAdapter(this, questions));
        }
        private RecyclerView recyclerViewQuestions;
        private ArrayList<QuestionModel> questions;
        private int currentIndex = 0;
}