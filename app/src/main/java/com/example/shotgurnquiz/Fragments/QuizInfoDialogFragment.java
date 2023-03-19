package com.example.shotgurnquiz.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
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

    public QuizInfoDialogFragment(){}

    public static QuizInfoDialogFragment newInstance(Quiz quiz, int userId) {
        QuizInfoDialogFragment fragment = new QuizInfoDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, quiz);
        args.putInt(ARG_PARAM2, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (getArguments() != null) {
            quiz = getArguments().getParcelable(ARG_PARAM1);
            userId = getArguments().getInt(ARG_PARAM2);
        }

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
                intent.putExtra("userId", userId);
                startActivity(intent);
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics());
            getDialog().getWindow().setLayout(width,height);
        }
        else {
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 600, getResources().getDisplayMetrics());
            getDialog().getWindow().setLayout(width,height);
        }
    }

    private static final String ARG_PARAM1 = "quiz";
    private static final String ARG_PARAM2 = "userId";

    private Quiz quiz;
    private int userId;
}
