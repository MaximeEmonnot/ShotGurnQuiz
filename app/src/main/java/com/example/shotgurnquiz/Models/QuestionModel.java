package com.example.shotgurnquiz.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class QuestionModel implements Parcelable {

    public QuestionModel(String title, String answerA, String answerB, boolean correctAnswer){
        this.title = title;
        this.answerA = answerA;
        this.answerB = answerB;
        this.correctAnswer = correctAnswer;
    }

    public QuestionModel (Parcel parcel) {
        this.title = parcel.readString();
        this.answerA = parcel.readString();
        this.answerB = parcel.readString();
        this.correctAnswer = parcel.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(answerA);
        parcel.writeString(answerB);
        parcel.writeByte((byte) (correctAnswer ? 1 : 0));
    }

    public static final Creator<QuestionModel> CREATOR = new Creator<QuestionModel>() {
        @Override
        public QuestionModel createFromParcel(Parcel in) {
            return new QuestionModel(in);
        }

        @Override
        public QuestionModel[] newArray(int size) {
            return new QuestionModel[size];
        }
    };


    public String title;
    public String answerA;
    public String answerB;
    public boolean correctAnswer; // True = A, False = B
}
