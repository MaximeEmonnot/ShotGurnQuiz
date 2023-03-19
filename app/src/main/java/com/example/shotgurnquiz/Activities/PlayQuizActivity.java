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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);

        // Récupération des différentes informations envoyées par Intent précédemment
        Bundle bundle = getIntent().getExtras();
        quiz = bundle.getParcelable("quiz");
        userId = bundle.getInt("userId");
        questions = bundle.getParcelableArrayList("questions");

        // Références aux éléments du layout
        textViewScoreCount = findViewById(R.id.play_quiz_score_count);
        textViewIndexCount = findViewById(R.id.play_quiz_index_count);
        textViewQuestion = findViewById(R.id.play_quiz_question);
        textViewAnswerATxt = findViewById(R.id.play_quiz_answer_a_txt);
        textViewAnswerBTxt = findViewById(R.id.play_quiz_answer_b_txt);
        textViewTimeCount = findViewById(R.id.play_quiz_time_count);
        TextView textViewIndexMax = findViewById(R.id.play_quiz_index_max);

        // Affichage du nombre total de questions
        textViewIndexMax.setText(Integer.toString(questions.size()));

        // Affichage de la première question
        index = 0;
        score = 0;
        loadQuestion(score, index);

        // Création du CameraManager et vérification des permissions accordées
        createCameraManager();
        checkForPermission();

        // Lancement du thread de jeu : Toutes les 10 secondes, on vérifie la réponse du joueur puis on passe à la question suivante
        gameThread = new CountDownTimer(10000, 1000) {
            // A chaque Tick (toute les 1 seconde), le temps restant est mis à jour pour l'affichage
            public void onTick(long millisUntilFinished) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewTimeCount.setText(" " + Integer.toString((int) (millisUntilFinished / 1000)) + " ");
                    }
                });
            }
            // Une fois le temps écoulé, on vérifie la réponse du joueur
            public void onFinish() {
                // Si la réponse est correcte, on ajoute 1 point au score
                if (answer == questions.get(index).GetAnswer()) {
                    score++;
                }
                // On passe à la question suivante, si elle existe
                index++;
                if(index < questions.size()) {
                    loadQuestion(score, index);
                    this.start();
                }else{
                    // Sinon, le quiz est terminé : On lance l'activité QuizSummaryActivity
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

    // Lancement d'une question : On affiche l'intitulé de la question, ainsi que les différents choix de réponse
    // On affiche également le score mis à jour et le numéro de question
    private void loadQuestion(int score, int index){
        Question question = questions.get(index);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewScoreCount.setText(" " + String.valueOf(score));
                textViewIndexCount.setText(String.valueOf(index + 1));
                textViewQuestion.setText(question.GetTitle());
                textViewAnswerATxt.setText(question.GetChoice1());
                textViewAnswerBTxt.setText(question.GetChoice2());
            }
        });
    }

    // Vérification des permissions : si les permissions ne sont pas accordées, une requête à l'utilisateur est effectuée
    private void checkForPermission() {
        if (allPermissionsGranted()) {
            cameraManager.startCamera();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS );
        }
    }

    // Fonction Helper pour vérifier si l'ensemble des permissions requises sont accordées
    private boolean allPermissionsGranted(){
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // Traitement du résultat de la requêtes de permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            // Si les permissions sont accordées, on lance la camera
            if (allPermissionsGranted()) {
                cameraManager.startCamera();
            }
            // Sinon on quitte l'activité et on notifie l'utilisateur
            else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    // Fonction Helper pour la création du CameraManager
    private void createCameraManager() {
        cameraManager = new CameraManager(this, findViewById(R.id.play_quiz_camera_stream), this, findViewById(R.id.play_quiz_camera_graphicOverlay));
    }

    // Si l'on quitte l'application via le bouton retour, affichage d'un message d'alerte pour demander confirmation
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.closing_Quiz))
                .setMessage(getResources().getString(R.string.are_you_sure_you_want_to_quit) + "\n" + getResources().getString(R.string.progress_will_be_lost))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Si l'utilisateur confirme, on arrête la boucle de jeu et on quitte l'activité
                        gameThread.cancel();
                        finish();
                    }

                })
                .setNegativeButton(getResources().getString(R.string.no), null)
                .show();
    }

    // Differentes variables de l'activité
    private Quiz quiz;
    private int userId;
    private ArrayList<Question> questions;
    private TextView textViewScoreCount;
    private TextView textViewIndexCount;
    private TextView textViewQuestion;
    private TextView textViewAnswerATxt;
    private TextView textViewAnswerBTxt;
    private TextView textViewTimeCount;
    private CountDownTimer gameThread;
    public boolean answer = false;
    private int score;
    private int index;
    private final static String[] REQUIRED_PERMISSIONS = {android.Manifest.permission.CAMERA};
    private final static int REQUEST_CODE_PERMISSIONS = 10;
    private CameraManager cameraManager;
}