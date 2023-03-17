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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public int CreateNewUser(String username, String email, String password){
        ContentValues values = new ContentValues();
        values.put(User.COLUMN_USERNAME, username);
        values.put(User.COLUMN_EMAIL, email);
        values.put(User.COLUMN_PASSWORD, password);
        return (int) database.insert(User.TABLE, null, values);
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

    public void CreateNewQuiz(String title, int themeIndex, int difficultyIndex, ArrayList<Question> questions){
        ContentValues quizValues = new ContentValues();
        quizValues.put(Quiz.COLUMN_TITLE, title);
        quizValues.put(Quiz.COLUMN_THEME, themeIndex);
        quizValues.put(Quiz.COLUMN_DIFFICULTY, difficultyIndex);
        long quizID = database.insert(Quiz.TABLE, null, quizValues);
        if (quizID != -1) {
            for (Question question : questions){
                ContentValues questionValues = new ContentValues();
                questionValues.put(Question.COLUMN_QUIZ_ID, quizID);
                questionValues.put(Question.COLUMN_TITLE, question.GetTitle());
                questionValues.put(Question.COLUMN_CHOICE1, question.GetChoice1());
                questionValues.put(Question.COLUMN_CHOICE2, question.GetChoice2());
                questionValues.put(Question.COLUMN_ANSWER, question.GetAnswer() ? 1 : 0);
                database.insert(Question.TABLE, null, questionValues);
            }
        }
    }

    public ArrayList<Quiz> GetAllQuizzes() {

        Cursor c = database.query(Quiz.TABLE,
                new String[]{Quiz.COLUMN_ID, Quiz.COLUMN_TITLE, Quiz.COLUMN_THEME, Quiz.COLUMN_DIFFICULTY},
                null, null, null, null, null);
        ArrayList<Quiz> output = new ArrayList<Quiz>();

        while (c.moveToNext()) output.add(new Quiz(c));
        c.close();

        return output;
    }

    public ArrayList<Quiz> GetAllLatestQuizzes(){
        Cursor c = database.query(Quiz.TABLE,
                new String[]{Quiz.COLUMN_ID, Quiz.COLUMN_TITLE, Quiz.COLUMN_THEME, Quiz.COLUMN_DIFFICULTY},
                null, null, null, null, Quiz.COLUMN_ID + " DESC");
        ArrayList<Quiz> output = new ArrayList<Quiz>();

        while (c.moveToNext()) output.add(new Quiz(c));
        c.close();

        return output;
    }

    public ArrayList<Quiz> GetAllQuizzesFromTheme(int themeIndex){
        Cursor c = database.query(Quiz.TABLE,
                new String[]{Quiz.COLUMN_ID, Quiz.COLUMN_TITLE, Quiz.COLUMN_THEME, Quiz.COLUMN_DIFFICULTY},
                Quiz.COLUMN_THEME + " =  ?", new String[]{ Integer.toString(themeIndex) }, null, null, null);
        ArrayList<Quiz> output = new ArrayList<Quiz>();

        while (c.moveToNext()) output.add(new Quiz(c));
        c.close();

        return output;
    }

    public ArrayList<Quiz> GetAllQuizzesFromDifficulty(int difficultyIndex){
        Cursor c = database.query(Quiz.TABLE,
                new String[]{Quiz.COLUMN_ID, Quiz.COLUMN_TITLE, Quiz.COLUMN_THEME, Quiz.COLUMN_DIFFICULTY},
                Quiz.COLUMN_DIFFICULTY + " =  ?", new String[]{ Integer.toString(difficultyIndex) }, null, null, null);
        ArrayList<Quiz> output = new ArrayList<Quiz>();

        while (c.moveToNext()) output.add(new Quiz(c));
        c.close();

        return output;
    }

    public ArrayList<Quiz> GetAllPopularQuizzes(){
        // On récupère la liste des quiz dans le tableau de score
        Cursor cScore = database.query(Score.TABLE, new String[]{Score.COLUMN_QUIZ_ID}, null, null, null, null, null);

        Map<Integer, Integer> quizIDs = new HashMap<Integer, Integer>();
        while(cScore.moveToNext()){
            int id = cScore.getInt(0);
            if (!quizIDs.containsKey(id)) quizIDs.put(id, 0);
            else quizIDs.put(id, quizIDs.get(id) + 1);
        }
        cScore.close();
        // On trie par nombre d'occurrence
        List<Map.Entry<Integer, Integer>> quizList = new ArrayList<Map.Entry<Integer, Integer>>(quizIDs.entrySet());
        Collections.sort(quizList, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        // On récupère à présent les quiz dans l'ordre de la liste précédente
        ArrayList<Quiz> output = new ArrayList<Quiz>();
        for (Map.Entry<Integer, Integer> entry : quizList){
            Cursor cQuiz = database.query(Quiz.TABLE,
                    new String[]{Quiz.COLUMN_ID, Quiz.COLUMN_TITLE, Quiz.COLUMN_THEME, Quiz.COLUMN_DIFFICULTY},
                    Quiz.COLUMN_ID + " =  ?", new String[]{ entry.getValue().toString() }, null, null, null);
            if (cQuiz.moveToNext()) output.add(new Quiz(cQuiz));
            cQuiz.close();
        }

        return output;
    }

    public ArrayList<Question> GetAllQuestionsFromQuiz(int quizID){
        Cursor c = database.query(Question.TABLE,
                new String[]{Question.COLUMN_ID, Question.COLUMN_QUIZ_ID, Question.COLUMN_TITLE, Question.COLUMN_CHOICE1, Question.COLUMN_CHOICE2, Question.COLUMN_ANSWER},
                Question.COLUMN_QUIZ_ID + " = ?", new String[]{ Integer.toString(quizID) }, null, null, Question.COLUMN_ID + " ASC");

        ArrayList<Question> output = new ArrayList<Question>();
        while (c.moveToNext()) output.add(new Question(c));
        return output;
    }

    public void AddNewScore(int quizID, int userID, int score){
        ContentValues values = new ContentValues();
        values.put(Score.COLUMN_QUIZ_ID, quizID);
        values.put(Score.COLUMN_USER_ID, userID);
        values.put(Score.COLUMN_SCORE, score);
        database.insert(Score.TABLE, null, values);
    }

    public Score GetUserScore(int quizId, int userId){
        Cursor c = database.query(Score.TABLE,
                new String[] { Score.COLUMN_ID, Score.COLUMN_QUIZ_ID, Score.COLUMN_USER_ID, Score.COLUMN_SCORE},
                Score.COLUMN_QUIZ_ID + " = ? and " + Score.COLUMN_USER_ID + " = ?", new String[]{Integer.toString(quizId), Integer.toString(userId)}, null, null, null);

        if (c.getCount() > 0){
            c.moveToNext();
            return new Score(c);
        }
        return null;
    }

    public ArrayList<Score> GetScores() {
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
