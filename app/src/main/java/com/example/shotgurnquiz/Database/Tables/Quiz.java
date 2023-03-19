package com.example.shotgurnquiz.Database.Tables;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Quiz implements Parcelable{

    // Construction ayant un paramètre Cursor : Traitement des résultats des requêtes vers la base de données
    public Quiz(Cursor cursor){
        id = cursor.getInt(0);
        title = cursor.getString(1);
        themeIndex = cursor.getInt(2);
        difficultyIndex = cursor.getInt(3);
    }

    // Constructeur classique
    public Quiz(int _id, String _title, int _themeIndex, int _difficultyIndex){
        id = _id;
        title = _title;
        themeIndex = _themeIndex;
        difficultyIndex = _difficultyIndex;
    }

    public Quiz (Parcel parcel) {
        id = parcel.readInt();
        title = parcel.readString();
        themeIndex = parcel.readInt();
        difficultyIndex = parcel.readInt();
    }

    // Différents getters
    public int GetID() { return id; }
    public String GetTitle() { return title; }
    public int GetTheme() { return themeIndex; }
    public int GetDifficulty() { return difficultyIndex; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeInt(themeIndex);
        parcel.writeInt(difficultyIndex);
    }

    public static final Parcelable.Creator<Quiz> CREATOR = new Parcelable.Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) { return new Quiz[size]; }
    };

    // Différentes variables en base de données (Nom de la table, nom des colonnes, requête de création, requête de suppression)
    public static final String TABLE = "quiz";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_THEME = "theme";
    public static final String COLUMN_DIFFICULTY = "difficulty";
    public static final String CREATION_QUERY = "CREATE TABLE " + TABLE
            + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " TEXT NOT NULL, "
            + COLUMN_THEME + " INTEGER NOT NULL,"
            + COLUMN_DIFFICULTY + " INTEGER NOT NULL);";
    public static final String DELETE_QUERY = "DROP TABLE " + TABLE + ";";

    // Paramètres de la classe, pour des traitements futurs
    private int id;
    private String title;
    private int themeIndex;
    private int difficultyIndex;
}
