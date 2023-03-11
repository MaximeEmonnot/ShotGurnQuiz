package com.example.shotgurnquiz.Database.Tables;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Question implements Parcelable {

    public Question(Cursor cursor){
        id = cursor.getInt(0);
        quizID = cursor.getInt(1);
        title = cursor.getString(2);
        choice1 = cursor.getString(3);
        choice2 = cursor.getString(4);
        answer = (cursor.getInt(5) == 1);
    }

    public Question(String _title, String _choice1, String _choice2, boolean _answer){
        title = _title;
        choice1 = _choice1;
        choice2 = _choice2;
        answer = _answer;
    }

    public Question (Parcel parcel) {
        id = parcel.readInt();
        quizID = parcel.readInt();
        title = parcel.readString();
        choice1 = parcel.readString();
        choice2 = parcel.readString();
        answer = parcel.readByte() != 0;
    }

    public int GetID() { return id; }
    public int GetQuizID() { return quizID; }
    public String GetTitle() { return title; }
    public String GetChoice1() { return choice1; }
    public String GetChoice2() { return choice2; }
    public boolean GetAnswer() { return answer; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(quizID);
        parcel.writeString(title);
        parcel.writeString(choice1);
        parcel.writeString(choice2);
        parcel.writeByte((byte) (answer ? 1 : 0));
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

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
