package com.example.shotgurnquiz.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.shotgurnquiz.Activities.PlayQuizActivity;
import com.example.shotgurnquiz.Models.QuizModel;
import com.example.shotgurnquiz.R;

public class QuizInfoDialogFragment extends DialogFragment {

    private QuizModel quiz;

    public QuizInfoDialogFragment(QuizModel quiz){
        this.quiz = quiz;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        View rootView = this.getLayoutInflater().inflate(R.layout.fragment_quiz_info, null);

        TextView textViewTitle = (TextView) rootView.findViewById(R.id.quiz_info_title);
        TextView textViewTheme = (TextView) rootView.findViewById(R.id.quiz_info_theme);
        TextView textViewDifficulty = (TextView) rootView.findViewById(R.id.quiz_info_difficulty);
        TextView textViewNumberOfQuestions = (TextView) rootView.findViewById(R.id.quiz_info_nb_questions);

        textViewTitle.setText(quiz.getTitle());
        textViewTheme.setText(quiz.getTheme());
        textViewDifficulty.setText(quiz.getDifficulty());
        textViewNumberOfQuestions.setText(String.valueOf(quiz.getQuestionsCount()));

        Button buttonPlay = (Button) rootView.findViewById(R.id.quiz_info_btn_play);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PlayQuizActivity.class);
                intent.putExtra("quiz", quiz);
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