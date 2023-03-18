package com.example.shotgurnquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shotgurnquiz.Database.Tables.Question;
import com.example.shotgurnquiz.Database.Tables.Quiz;
import com.example.shotgurnquiz.R;
import com.example.shotgurnquiz.Camerax.CameraManager;

import java.util.ArrayList;

public class PlayQuizActivity extends AppCompatActivity {

    private Quiz quiz;
    private int userId;
    private ArrayList<Question> questions;
    private TextView textViewScoreCount;
    private TextView textViewIndexCount;
    private TextView textViewQuestion;
    private TextView textViewAnswerATxt;
    private TextView textViewAnswerBTxt;
    private TextView textViewTimeCount;
    public boolean answer = false;
    private int score;
    private int index;
    private final static String[] REQUIRED_PERMISSIONS = {android.Manifest.permission.CAMERA};
    private final static int REQUEST_CODE_PERMISSIONS = 10;
    private CameraManager cameraManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);

        Bundle bundle = getIntent().getExtras();
        quiz = bundle.getParcelable("quiz");
        userId = bundle.getInt("userId");
        questions = bundle.getParcelableArrayList("questions");

        textViewScoreCount = findViewById(R.id.play_quiz_score_count);
        textViewIndexCount = findViewById(R.id.play_quiz_index_count);
        textViewQuestion = findViewById(R.id.play_quiz_question);
        textViewAnswerATxt = findViewById(R.id.play_quiz_answer_a_txt);
        textViewAnswerBTxt = findViewById(R.id.play_quiz_answer_b_txt);
        textViewTimeCount = findViewById(R.id.play_quiz_time_count);

        TextView textViewIndexMax = findViewById(R.id.play_quiz_index_max);

        textViewIndexMax.setText(Integer.toString(questions.size()));

        index = 0;
        score = 0;
        loadQuestion(score, index);

        createCameraManager();
        checkForPermission();

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
                if (answer == questions.get(index).GetAnswer()) {
                    score++;
                }
                index++;
                if(index < questions.size()) {
                    loadQuestion(score, index);
                    this.start();
                }else{
                    Intent intent = new Intent(PlayQuizActivity.this, QuizSummaryActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("questionCount", questions.size());
                    intent.putExtra("quizTitle", quiz.GetTitle());
                    intent.putExtra("quizId", quiz.GetID());
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }

    private void loadQuestion(int score, int index){
        Question question = questions.get(index);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewScoreCount.setText(String.valueOf(score));
                textViewIndexCount.setText(String.valueOf(index + 1));
                textViewQuestion.setText(question.GetTitle());
                textViewAnswerATxt.setText(question.GetChoice1());
                textViewAnswerBTxt.setText(question.GetChoice2());
            }
        });
    }

    private void checkForPermission() {
        if (allPermissionsGranted()) {
            cameraManager.startCamera();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS );
        }
    }

    private boolean allPermissionsGranted(){
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                cameraManager.startCamera();
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void createCameraManager() {
        cameraManager = new CameraManager(this, findViewById(R.id.play_quiz_camera_stream), this, findViewById(R.id.play_quiz_camera_graphicOverlay));
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