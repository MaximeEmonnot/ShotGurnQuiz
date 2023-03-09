package com.example.shotgurnquiz.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class QuizModel implements Parcelable {

    public QuizModel(String title, String theme, String difficulty, ArrayList<QuestionModel> questions){

        this.title = title;
        this.theme = theme;
        this.difficulty = difficulty;
        this.questions = questions;
    }

    public QuizModel (Parcel parcel) {
        this.title = parcel.readString();
        this.theme = parcel.readString();
        this.difficulty = parcel.readString();
        this.questions = parcel.readArrayList(QuestionModel.class.getClassLoader());
    }

    public static final Creator<QuizModel> CREATOR = new Creator<QuizModel>() {
        @Override
        public QuizModel createFromParcel(Parcel in) {
            return new QuizModel(in);
        }

        @Override
        public QuizModel[] newArray(int size) {
            return new QuizModel[size];
        }
    };

    public String getTitle() { return title; }

    public String getTheme() { return theme; }

    public String getDifficulty() { return difficulty; }

    public int getQuestionsCount(){
        return questions.size();
    }

    public ArrayList<QuestionModel> getQuestions(){ return questions; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(theme);
        parcel.writeString(difficulty);
        parcel.writeList(questions);
    }

    private String title;
    private String theme;
    private String difficulty;
    private ArrayList<QuestionModel> questions;
}
