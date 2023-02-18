package com.example.shotgurnquiz.Database;

public class UserTable {
    public static final String TABLE = "user";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public static final String CREATION_QUERY = "CREATE TABLE " + TABLE
            + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_USERNAME + " TEXT NOT NULL, "
                    + COLUMN_PASSWORD + " TEXT NOT NULL);";
    public static final String DELETE_QUERY = "DROP TABLE " + TABLE + ";";
}
