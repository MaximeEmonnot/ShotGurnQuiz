package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shotgurnquiz.Models.QuestionModel;
import com.example.shotgurnquiz.Models.QuizModel;
import com.example.shotgurnquiz.R;

import java.util.ArrayList;

public class PlayQuizActivity extends AppCompatActivity {

    private QuizModel quiz;
    private TextView textViewScoreCount;
    private TextView textViewIndexCount;
    private TextView textViewQuestion;
    private TextView textViewAnswerATxt;
    private TextView textViewAnswerBTxt;
    private TextView textViewTimeCount;
    public boolean answer = false;
    private int score;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);

        quiz = getIntent().getExtras().getParcelable("quiz");

        textViewScoreCount = findViewById(R.id.play_quiz_score_count);
        textViewIndexCount = findViewById(R.id.play_quiz_index_count);
        textViewQuestion = findViewById(R.id.play_quiz_question);
        textViewAnswerATxt = findViewById(R.id.play_quiz_answer_a_txt);
        textViewAnswerBTxt = findViewById(R.id.play_quiz_answer_b_txt);
        textViewTimeCount = findViewById(R.id.play_quiz_time_count);

        TextView textViewIndexMax = findViewById(R.id.play_quiz_index_max);

        ArrayList<QuestionModel> questions = quiz.getQuestions();

        textViewIndexMax.setText(Integer.toString(questions.size()));

        index = 0;
        score = 0;
        loadQuestion(score, index);

        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewTimeCount.setText(Integer.toString((int) (millisUntilFinished / 1000)));
                    }
                });
            }
            public void onFinish() {
                if (answer == quiz.getQuestions().get(index).correctAnswer) {
                    score++;
                }
                index++;
                if(index < questions.size()) {
                    loadQuestion(score, index);
                    this.start();
                }else{
                    //quiz terminé
                }
            }
        }.start();
    }

    private void loadQuestion(int score, int index){
        QuestionModel question = quiz.getQuestions().get(index);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewScoreCount.setText(String.valueOf(score));
                textViewIndexCount.setText(String.valueOf(index));
                textViewQuestion.setText(question.title);
                textViewAnswerATxt.setText(question.answerA);
                textViewAnswerBTxt.setText(question.answerB);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.closing_Quiz))
                .setMessage(getResources().getString(R.string.are_you_sure_you_want_to_quit) + "\n" + getResources().getString(R.string.progress_will_be_lost))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton(getResources().getString(R.string.no), null)
                .show();
    }
}