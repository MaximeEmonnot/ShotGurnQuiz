package com.example.shotgurnquiz.Database.Tables;

import android.database.Cursor;

public class Question {

    public Question(Cursor cursor){
        id = cursor.getInt(0);
        quizID = cursor.getInt(1);
        title = cursor.getString(2);
        choice1 = cursor.getString(3);
        choice2 = cursor.getString(4);
        answer = (cursor.getInt(5) == 1);
    }

    public Question(int _id, int _quizID, String _title, String _choice1, String _choice2, boolean _answer){
        id = _id;
        quizID = _quizID;
        title = _title;
        choice1 = _choice1;
        choice2 = _choice2;
        answer = _answer;
    }

    public int GetID() { return id; }
    public int GetQuizID() { return quizID; }
    public String GetTitle() { return title; }
    public String GetChoice1() { return choice1; }
    public String GetChoice2() { return choice2; }
    public boolean GetAnswer() { return answer; }

    public static final String TABLE = "question";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUIZ_ID = "quiz_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CHOICE1 = "choice1";
    public static final String COLUMN_CHOICE2 = "choice2";
    public static final String COLUMN_ANSWER = "answer";

    public static final String CREATION_QUERY = "CREATE TABLE " + TABLE
            + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_QUIZ_ID + " INTEGER NOT NULL,"
            + COLUMN_TITLE + " TEXT NOT NULL, "
            + COLUMN_CHOICE1 + " TEXT NOT NULL,"
            + COLUMN_CHOICE2 + " TEXT NOT NULL,"
            + COLUMN_ANSWER + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + COLUMN_QUIZ_ID + ") REFERENCES " + Quiz.TABLE + "(" + Quiz.COLUMN_ID + "));";
    public static final String DELETE_QUERY = "DROP TABLE " + TABLE + ";";

    private int id;
    private int quizID;
    private String title;
    private String choice1;
    private String choice2;
    private boolean answer;
}
