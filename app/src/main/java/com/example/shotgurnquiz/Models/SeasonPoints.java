package com.example.shotgurnquiz.Models;

import android.database.Cursor;

public class SeasonPoints {

    // Construction ayant un paramètre Cursor : Traitement des résultats des requêtes vers la base de données
    public SeasonPoints(Cursor cursor){
        username = cursor.getString(0);
        points = cursor.getInt(1);
    }

    // Constructeur classique
    public SeasonPoints(String _username, int _points){
        this.username = _username;
        this.points = _points;
    }

    // Différents getters
    public String getUsername() { return username; }
    public int getPoints() { return points; }

    // Paramètres de la classe, pour des traitements futurs
    private String username;
    private int points;
}
