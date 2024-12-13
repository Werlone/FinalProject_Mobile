package com.example.finalproject;

import static com.example.finalproject.DBHelper.COLUMN_NAME;
import static com.example.finalproject.DBHelper.COLUMN_SCORE;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class HighscoresActivity extends AppCompatActivity {

    private TextView highscore1, highscore2, highscore3, highscore4, highscore5;
    private Button homeButton, playAgainButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        // Initialize buttons
        homeButton = findViewById(R.id.home_button);
        playAgainButton = findViewById(R.id.play_again_button);
        highscore1 = findViewById(R.id.highscore_1);
        highscore2 = findViewById(R.id.highscore_2);
        highscore3 = findViewById(R.id.highscore_3);
        highscore4 = findViewById(R.id.highscore_4);
        highscore5 = findViewById(R.id.highscore_5);

        loadHighScores();

        // Home button
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighscoresActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity so the user cannot return to it with the back button
            }
        });

        // Play Again button
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighscoresActivity.this, SequenceActivity.class);
                startActivity(intent);
                finish(); //Finish the current activity so the user cannot return to it with the back button
            }
        });
    }

    private void loadHighScores() {
        // Create the database helper
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to retrieve top 5 high scores sorted by score in descending order
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null,
                COLUMN_SCORE + " DESC", "5");

        // Initialize the high score list
        List<DBHelper.HighScore> highScores = new ArrayList<DBHelper.HighScore>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                    int score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE));
                    highScores.add(new DBHelper.HighScore(name, score));
                } catch (IllegalArgumentException e) {
                    // Log the error or handle it appropriately
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Close the database
        db.close();

        // Set the high scores to the TextViews, if any scores exist
        setHighScores(highScores);
    }

    private void setHighScores(List<DBHelper.HighScore> highScores) {
        // Check the number of high scores and set the TextViews accordingly
        if (highScores.size() > 0) {
            highscore1.setText(highScores.get(0).getName() + " - " + highScores.get(0).getScore());
        } else {
            highscore1.setText("No score yet");
        }

        if (highScores.size() > 1) {
            highscore2.setText(highScores.get(1).getName() + " - " + highScores.get(1).getScore());
        } else {
            highscore2.setText("No score yet");
        }

        if (highScores.size() > 2) {
            highscore3.setText(highScores.get(2).getName() + " - " + highScores.get(2).getScore());
        } else {
            highscore3.setText("No score yet");
        }

        if (highScores.size() > 3) {
            highscore4.setText(highScores.get(3).getName() + " - " + highScores.get(3).getScore());
        } else {
            highscore4.setText("No score yet");
        }

        if (highScores.size() > 4) {
            highscore5.setText(highScores.get(4).getName() + " - " + highScores.get(4).getScore());
        } else {
            highscore5.setText("No score yet");
        }
    }
}
