package com.example.shotgurnquiz.Database.Tables;

import android.database.Cursor;

public class Score {

    // Construction ayant un paramètre Cursor : Traitement des résultats des requêtes vers la base de données
    public Score(Cursor c){
        id = c.getInt(0);
        quizId = c.getInt(1);
        userId = c.getInt(2);
        score = c.getInt(3);
    }

    // Différents getters
    public int GetID() {
        return id;
    }
    public int GetQuizId() {
        return quizId;
    }
    public int GetUserId() {
        return userId;
    }
    public int GetScore() {
        return score;
    }

    // Différentes variables en base de données (Nom de la table, nom des colonnes, requête de création, requête de suppression)
    public static final String TABLE = "score";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUIZ_ID = "quiz_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_SCORE = "score";
    public static final String CREATION_QUERY = "CREATE TABLE " + TABLE
            + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_QUIZ_ID + " INTEGER NOT NULL,"
            + COLUMN_USER_ID + " INTEGER NOT NULL,"
            + COLUMN_SCORE + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + COLUMN_QUIZ_ID + ") REFERENCES " + Quiz.TABLE + "(" + Quiz.COLUMN_ID + "),"
            + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + User.TABLE + "(" + User.COLUMN_ID + "));";
    public static final String DELETE_QUERY = "DROP TABLE " + TABLE + ";";

    // Paramètres de la classe, pour des traitements futurs
    private int id;
    private int quizId;
    private int userId;
    private int score;
}
