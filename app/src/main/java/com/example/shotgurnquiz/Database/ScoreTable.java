package com.example.shotgurnquiz.Database;

public class ScoreTable {
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
            + "FOREIGN KEY (" + COLUMN_QUIZ_ID + ") REFERENCES " + QuizTable.TABLE + "(" + QuizTable.COLUMN_ID + "),"
            + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + UserTable.TABLE + "(" + UserTable.COLUMN_ID + ");";
    public static final String DELETE_QUERY = "DROP TABLE " + TABLE + ";";
}
