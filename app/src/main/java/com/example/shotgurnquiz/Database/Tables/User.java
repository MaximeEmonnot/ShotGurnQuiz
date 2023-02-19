package com.example.shotgurnquiz.Database.Tables;

import android.database.Cursor;

public class User {

    public User(Cursor c) {
        id = c.getInt(0);
        username = c.getString(1);
        email = c.getString(2);
        password = c.getString(3);
    }

    public User(int _id, String _username, String _email, String _password){
        id = _id;
        username = _username;
        email = _email;
        password =_password;
    }

    public int GetID() {
        return id;
    }

    public String GetUsername(){
        return username;
    }

    public String GetEmail(){
        return email;
    }

    public String GetPassword(){
        return password;
    }

    @Override
    public String toString(){
        return "ID : " + id + "\nUsername : " + username + "\nEmail : " + email + "\nPassword : " + password;
    }


    public static final String TABLE = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";

    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String CREATION_QUERY = "CREATE TABLE " + TABLE
            + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_USERNAME + " TEXT NOT NULL, "
                    + COLUMN_EMAIL + " TEXT NOT NULL, "
                    + COLUMN_PASSWORD + " TEXT NOT NULL);";
    public static final String DELETE_QUERY = "DROP TABLE " + TABLE + ";";

    private int id;
    private String username;
    private String email;
    private String password;
}
