package com.example.shotgurnquiz.Models;

import java.util.ArrayList;

public class QuizModel {

    public QuizModel(String title, String theme, String difficulty, ArrayList<QuestionModel> questions){

        this.title = title;
        this.theme = theme;
        this.difficulty = difficulty;
        this.questions = questions;
    }



    private String title;
    private String theme;
    private String difficulty;
    private ArrayList<QuestionModel> questions;
}
