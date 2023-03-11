package com.example.shotgurnquiz.Database.Tables;

public class Question {



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
