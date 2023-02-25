package com.example.shotgurnquiz.Database.Tables;

import android.database.Cursor;

public class Score {

    public Score(Cursor c){
        id = c.getInt(0);
        quizTitle = c.getString(1);
        username = c.getString(2);
        score = c.getInt(3);
    }

    public int GetID() {
        return id;
    }

    public String GetQuizTitle() {
        return quizTitle;
    }

    public String GetUsername() {
        return username;
    }

    public int GetScore() {
        return score;
    }

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

    private int id;
    private String quizTitle;
    private String username;
    private int score;
}
