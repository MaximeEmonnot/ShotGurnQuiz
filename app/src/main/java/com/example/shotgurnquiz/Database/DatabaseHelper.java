package com.example.shotgurnquiz.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        database = getWritableDatabase();
    }

    public static synchronized DatabaseHelper GetInstance(Context context){
        if (instance == null)
            instance = new DatabaseHelper(context.getApplicationContext(), BASE_NOM, null, BASE_VERSION);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTable.CREATION_QUERY);
        db.execSQL(QuizTable.CREATION_QUERY);
        db.execSQL(QuestionTable.CREATION_QUERY);
        db.execSQL(ScoreTable.CREATION_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ScoreTable.DELETE_QUERY);
        db.execSQL(QuestionTable.DELETE_QUERY);
        db.execSQL(QuizTable.DELETE_QUERY);
        db.execSQL(UserTable.DELETE_QUERY);
        onCreate(db);
    }

    private static DatabaseHelper instance = null;

    private static final int BASE_VERSION = 1;
    private static final String BASE_NOM = "shotgurnquiz.db";

    private SQLiteDatabase database;
}
