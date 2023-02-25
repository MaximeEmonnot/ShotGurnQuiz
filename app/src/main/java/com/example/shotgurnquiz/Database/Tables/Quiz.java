package com.example.shotgurnquiz.Database.Tables;

import android.database.Cursor;

public class Quiz {

    public Quiz(Cursor cursor){
        id = cursor.getInt(0);
        title = cursor.getString(1);
        theme = cursor.getString(2);
        difficulty = cursor.getString(3);
    }

    public Quiz(int _id, String _title, String _theme, String _difficulty){
        id = _id;
        title = _title;
        theme = _theme;
        difficulty = _difficulty;
    }

    public int GetID() { return id; }
    public String GetTitle() { return title; }
    public String GetTheme() { return theme; }
    public String GetDifficulty() { return difficulty; }

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

    private int id;
    private String title;
    private String theme;
    private String difficulty;
}
