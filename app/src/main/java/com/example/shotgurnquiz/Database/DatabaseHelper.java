package com.example.shotgurnquiz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.shotgurnquiz.Database.Tables.Question;
import com.example.shotgurnquiz.Database.Tables.Quiz;
import com.example.shotgurnquiz.Database.Tables.Score;
import com.example.shotgurnquiz.Database.Tables.User;
import com.example.shotgurnquiz.Models.QuestionModel;

import java.util.ArrayList;
import java.util.List;

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
        db.execSQL(User.CREATION_QUERY);
        db.execSQL(Quiz.CREATION_QUERY);
        db.execSQL(Question.CREATION_QUERY);
        db.execSQL(Score.CREATION_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Score.DELETE_QUERY);
        db.execSQL(Question.DELETE_QUERY);
        db.execSQL(Quiz.DELETE_QUERY);
        db.execSQL(User.DELETE_QUERY);
        onCreate(db);
    }

    public User FindUserByID(int id){
        Cursor c = database.query(User.TABLE,
                new String[] { User.COLUMN_ID, User.COLUMN_USERNAME, User.COLUMN_EMAIL, User.COLUMN_PASSWORD},
                User.COLUMN_ID + " = ?", new String[]{Integer.toString(id)}, null, null, null);

        if (c.getCount() > 0){
            c.moveToNext();
            return new User(c);
        }
        return null;
    }

    public User FindUserByUsername(String username){
        Cursor c = database.query(User.TABLE,
                new String[] { User.COLUMN_ID, User.COLUMN_USERNAME, User.COLUMN_EMAIL, User.COLUMN_PASSWORD},
                User.COLUMN_USERNAME + " = ?", new String[]{username}, null, null, null);

        if (c.getCount() > 0){
            c.moveToNext();
            return new User(c);
        }
        return null;
    }

    public User FindUserByEmail(String email){
        Cursor c = database.query(User.TABLE,
                new String[] { User.COLUMN_ID, User.COLUMN_USERNAME, User.COLUMN_EMAIL, User.COLUMN_PASSWORD},
                User.COLUMN_EMAIL + " = ?", new String[]{email}, null, null, null);

        if (c.getCount() > 0){
            c.moveToNext();
            return new User(c);
        }
        return null;
    }

    public void CreateNewUser(String username, String email, String password){
        ContentValues values = new ContentValues();
        values.put(User.COLUMN_USERNAME, username);
        values.put(User.COLUMN_EMAIL, email);
        values.put(User.COLUMN_PASSWORD, password);
        database.insert(User.TABLE, null, values);
    }

    public List<User> GetAllUser() {
        Cursor c = database.query(User.TABLE,
                new String[] {User.COLUMN_ID, User.COLUMN_USERNAME, User.COLUMN_EMAIL, User.COLUMN_PASSWORD},
                null, null, null, null, null);
        ArrayList<User> output = new ArrayList<User>();

        while (c.moveToNext()){
            output.add(new User(c));
        }

        c.close();
        return output;
    }

    public void CreateNewQuiz(String title, String theme, String difficulty, ArrayList<QuestionModel> questions){
        ContentValues quizValues = new ContentValues();
        quizValues.put(Quiz.COLUMN_TITLE, title);
        quizValues.put(Quiz.COLUMN_THEME, theme);
        quizValues.put(Quiz.COLUMN_DIFFICULTY, difficulty);
        long quizID = database.insert(Quiz.TABLE, null, quizValues);
        if (quizID != -1) {
            for (QuestionModel question : questions){
                ContentValues questionValues = new ContentValues();
                questionValues.put(Question.COLUMN_TITLE, question.title);
                questionValues.put(Question.COLUMN_CHOICE1, question.answerA);
                questionValues.put(Question.COLUMN_CHOICE2, question.answerB);
                questionValues.put(Question.COLUMN_ANSWER, question.correctAnswer ? 1 : 0);
                database.insert(Question.TABLE, null, questionValues);
            }
        }
    }

    public List<Score> GetScores() {
        Cursor c = database.rawQuery("SELECT " + Score.COLUMN_ID +
                ", " + Quiz.COLUMN_TITLE +
                ", " + User.COLUMN_USERNAME +
                ", " + Score.COLUMN_SCORE +
                " FROM " + Score.TABLE + " NATURAL JOIN " + Quiz.TABLE + " NATURAL JOIN " + User.TABLE, null);

        ArrayList<Score> output = new ArrayList<Score>();

        while (c.moveToNext()){
            output.add(new Score(c));
        }

        c.close();
        return output;
    }

    private static DatabaseHelper instance = null;

    private static final int BASE_VERSION = 1;
    private static final String BASE_NOM = "shotgurnquiz.db";

    private SQLiteDatabase database;
}
