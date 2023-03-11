package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.shotgurnquiz.Database.DatabaseHelper;
import com.example.shotgurnquiz.Fragments.AddQuestionDialogFragment;
import com.example.shotgurnquiz.Models.QuestionModel;
import com.example.shotgurnquiz.Models.QuizModel;
import com.example.shotgurnquiz.R;
import com.example.shotgurnquiz.RecyclerViewConfigs.Question_RecyclerViewAdapter;
import com.example.shotgurnquiz.RecyclerViewConfigs.RecyclerView_ItemClickListener;
import com.example.shotgurnquiz.RecyclerViewConfigs.RecyclerView_SpacesItemDecoration;

public class AddQuizActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_quiz);

            DatabaseHelper db = DatabaseHelper.GetInstance(this);

            Spinner spinnerTheme = findViewById(R.id.spinner_theme);
            // Liste des themes a recuperer dans la bd
            String[] themes = new String[]{"Country", "Music", "Soccer"};
            ArrayAdapter<String> adapterTheme = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, themes);
            spinnerTheme.setAdapter(adapterTheme);

            Spinner spinnerDifficulty = findViewById(R.id.spinner_difficulty);
            String[] difficulties = new String[]{getResources().getString(R.string.easy) , getResources().getString(R.string.medium), getResources().getString(R.string.hard)};
            ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, difficulties);
            spinnerDifficulty.setAdapter(adapterDifficulty);

            RecyclerView recyclerViewQuestion = findViewById(R.id.recycler_view_question);
            Question_RecyclerViewAdapter questionAdapter = new Question_RecyclerViewAdapter(this);
            recyclerViewQuestion.setAdapter(questionAdapter);
            recyclerViewQuestion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewQuestion.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.HORIZONTAL));

            recyclerViewQuestion.addOnItemTouchListener(new RecyclerView_ItemClickListener(new RecyclerView_ItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    AddQuestionDialogFragment addQuestionFragment = new AddQuestionDialogFragment(questionAdapter, position);
                    addQuestionFragment.show(getSupportFragmentManager(), "dialog");
                }
            }));

            EditText editTextTitle = (EditText) findViewById(R.id.quiz_title);

            Button buttonCreate = (Button) findViewById(R.id.btn_create);
            buttonCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = editTextTitle.getText().toString();
                    String difficulty = spinnerDifficulty.getSelectedItem().toString();
                    String theme = spinnerTheme.getSelectedItem().toString();
                    QuizModel quiz = new QuizModel(title, difficulty, theme, questionAdapter.getAllItems());
                    // ajout du quiz en BD ici
                    db.CreateNewQuiz(title, theme, difficulty, quiz.getQuestions());
                    finish();
                }
            });
        }
}