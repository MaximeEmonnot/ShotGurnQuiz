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
import com.example.shotgurnquiz.R;
import com.example.shotgurnquiz.RecyclerViewConfigs.QuestionCard_RecyclerViewAdapter;
import com.example.shotgurnquiz.RecyclerViewConfigs.RecyclerView_ItemClickListener;
import com.example.shotgurnquiz.RecyclerViewConfigs.RecyclerView_SpacesItemDecoration;

public class AddQuizActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_quiz);

            // Instanciation du DatabaseHelper (Singleton)
            DatabaseHelper db = DatabaseHelper.GetInstance(this);

            // Références aux éléments du layout
            Spinner spinnerTheme = findViewById(R.id.spinner_theme);
            Spinner spinnerDifficulty = findViewById(R.id.spinner_difficulty);
            RecyclerView recyclerViewQuestion = findViewById(R.id.recycler_view_question);
            EditText editTextTitle = (EditText) findViewById(R.id.quiz_title);
            Button buttonCreate = (Button) findViewById(R.id.btn_create);

            // Récupération de la liste des themes et difficultés dans les variables de l'application
            String[] themes = getResources().getStringArray(R.array.theme_list);
            String[] difficulties = getResources().getStringArray(R.array.difficulty_list);

            // Configurations des différents éléments du layout
            ArrayAdapter<String> adapterTheme = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, themes);
            spinnerTheme.setAdapter(adapterTheme);

            ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, difficulties);
            spinnerDifficulty.setAdapter(adapterDifficulty);

            QuestionCard_RecyclerViewAdapter questionAdapter = new QuestionCard_RecyclerViewAdapter(this);
            recyclerViewQuestion.setAdapter(questionAdapter);
            recyclerViewQuestion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewQuestion.addItemDecoration(new RecyclerView_SpacesItemDecoration(20, 10, LinearLayoutManager.HORIZONTAL));

            // OnTouch du RecyclerView Question : On affiche un Dialog correspondant à la question selectionnée ou ajoutée
            recyclerViewQuestion.addOnItemTouchListener(new RecyclerView_ItemClickListener(new RecyclerView_ItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    AddQuestionDialogFragment addQuestionFragment = new AddQuestionDialogFragment(questionAdapter, position);
                    addQuestionFragment.show(getSupportFragmentManager(), "dialog");
                }
            }));

            // OnClick du bouton Create : Création du quiz et retour à l'activité QuizListActivity
            buttonCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = editTextTitle.getText().toString();
                    int difficultyIndex = spinnerDifficulty.getSelectedItemPosition();
                    int themeIndex = spinnerTheme.getSelectedItemPosition();

                    db.CreateNewQuiz(title, themeIndex, difficultyIndex, questionAdapter.getAllItems());

                    finish();
                }
            });
        }
}