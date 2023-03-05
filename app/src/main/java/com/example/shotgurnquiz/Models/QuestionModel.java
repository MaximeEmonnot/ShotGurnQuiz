package com.example.shotgurnquiz.Models;

public class QuestionModel {

    public QuestionModel(String title, String answerA, String answerB, boolean correctAnswer){
        this.title = title;
        this.answerA = answerA;
        this.answerB = answerB;
        this.correctAnswer = correctAnswer;
    }

    public String getTitle() {
        return title;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setTitle(String title) { this.title = title; }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }



    private String title;
    private String answerA;
    private String answerB;
    private boolean correctAnswer; // True = A, False = B

}
