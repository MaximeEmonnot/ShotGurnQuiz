package com.example.shotgurnquiz.Models;

import android.database.Cursor;

public class SeasonPoints {

    private String username;
    private int points;

    public SeasonPoints(Cursor cursor){
        username = cursor.getString(0);
        points = cursor.getInt(1);
    }

    public SeasonPoints(String _username, int _points){
        this.username = _username;
        this.points = _points;
    }


    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }
}
