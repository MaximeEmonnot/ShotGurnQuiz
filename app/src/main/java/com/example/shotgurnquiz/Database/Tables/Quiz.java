package com.example.shotgurnquiz.Database.Tables;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Quiz implements Parcelable{

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

    public Quiz (Parcel parcel) {
        id = parcel.readInt();
        title = parcel.readString();
        theme = parcel.readString();
        difficulty = parcel.readString();
    }

    public int GetID() { return id; }
    public String GetTitle() { return title; }
    public String GetTheme() { return theme; }
    public String GetDifficulty() { return difficulty; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(theme);
        parcel.writeString(difficulty);
    }

    public static final Parcelable.Creator<Quiz> CREATOR = new Parcelable.Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) { return new Quiz[size]; }
    };

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
