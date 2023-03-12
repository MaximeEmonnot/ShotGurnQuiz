package com.example.shotgurnquiz.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.shotgurnquiz.Activities.PlayQuizActivity;
import com.example.shotgurnquiz.Database.DatabaseHelper;
import com.example.shotgurnquiz.Database.Tables.Question;
import com.example.shotgurnquiz.Database.Tables.Quiz;
import com.example.shotgurnquiz.R;

import java.util.ArrayList;
import java.util.List;

public class QuizInfoDialogFragment extends DialogFragment {

    private Quiz quiz;

    public QuizInfoDialogFragment(Quiz quiz){
        this.quiz = quiz;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        View rootView = this.getLayoutInflater().inflate(R.layout.fragment_quiz_info, null);

        TextView textViewTitle = (TextView) rootView.findViewById(R.id.quiz_info_title);
        TextView textViewTheme = (TextView) rootView.findViewById(R.id.quiz_info_theme);
        TextView textViewDifficulty = (TextView) rootView.findViewById(R.id.quiz_info_difficulty);
        TextView textViewNumberOfQuestions = (TextView) rootView.findViewById(R.id.quiz_info_nb_questions);

        DatabaseHelper db = DatabaseHelper.GetInstance(getActivity().getBaseContext());
        ArrayList<Question> questions = db.GetAllQuestionsFromQuiz(quiz.GetID());

        textViewTitle.setText(quiz.GetTitle());
        textViewTheme.setText(getResources().getStringArray(R.array.theme_list)[quiz.GetTheme()]);
        textViewDifficulty.setText(getResources().getStringArray(R.array.difficulty_list)[quiz.GetDifficulty()]);
        textViewNumberOfQuestions.setText(String.valueOf(questions.size()));

        Button buttonPlay = (Button) rootView.findViewById(R.id.quiz_info_btn_play);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PlayQuizActivity.class);
                intent.putExtra("quiz", quiz);
                intent.putParcelableArrayListExtra("questions", questions);
                startActivity(intent);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 600, getResources().getDisplayMetrics());
        getDialog().getWindow().setLayout(width,height);
    }
}
