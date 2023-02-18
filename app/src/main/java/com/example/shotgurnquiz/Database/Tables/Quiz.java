package com.example.shotgurnquiz.Database.Tables;

public class Quiz {
    public static final String TABLE = "quiz";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_THEME = "theme";
    public static final String COLUMN_DIFFICULTY = "difficulty";

    public static final String CREATION_QUERY = "CREATE TABLE " + TABLE
            + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " TEXT NOT NULL, "
            + COLUMN_THEME + " TEXT NOT NULL,"
            + COLUMN_DIFFICULTY + " INTEGER NOT NULL);";
    public static final String DELETE_QUERY = "DROP TABLE " + TABLE + ";";
}
