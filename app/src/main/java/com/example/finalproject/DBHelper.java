package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "highscores.db";
    protected static final int DATABASE_VERSION = 1;
    protected static final String TABLE_NAME = "highscores";
    protected static final String COLUMN_ID = "id";
    protected static final String COLUMN_NAME = "name";
    protected static final String COLUMN_SCORE = "score";

    // SQL query to create the table
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_SCORE + " INTEGER)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert a new high score into the database
    public void insertHighScore(String name, int score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SCORE, score);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Get the top 5 high scores
    public ArrayList<HighScore> getHighScores() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<HighScore> highScores = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_SCORE},
                null, null, null, null,
                COLUMN_SCORE + " DESC", "5");  // Sort by score descending, limit to 5

        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                    int score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE));
                    highScores.add(new HighScore(name, score));
                } catch (IllegalArgumentException e) {
                    // Log the error or handle it appropriately
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return highScores;
    }

    // HighScore class to store the name and score
    public static class HighScore {
        private String name;
        private int score;

        public HighScore(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }
}
