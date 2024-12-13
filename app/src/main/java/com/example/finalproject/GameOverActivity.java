package com.example.finalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button submitButton;
    private Button playAgainButton;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        nameInput = findViewById(R.id.name_input);
        submitButton = findViewById(R.id.submit_button);
        playAgainButton = findViewById(R.id.play_again_button);

        // Get score passed from the GameActivity
        score = getIntent().getIntExtra("score", 0);
        TextView scoreText = findViewById(R.id.score_text);
        scoreText.setText("Score: " + score);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the name entered by the user
                String name = nameInput.getText().toString();

                if (!name.isEmpty()) {
                    // Save the score and name to the database
                    saveHighScore(name, score);

                    // Go to Highscores Activity
                    Intent intent = new Intent(GameOverActivity.this, HighscoresActivity.class);
                    startActivity(intent);
                    finish(); // Close the GameOverActivity
                } else {
                    // Show error if name is empty
                    Toast.makeText(GameOverActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(GameOverActivity.this, SequenceActivity.class);
            startActivity(intent);
            finish(); // Close the GameOverActivity

            }
        });
    }

    private void saveHighScore(String name, int score) {
        // Create or update the database with the new score
        DBHelper dbHelper = new DBHelper(GameOverActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the new score into the HighScores table
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_NAME, name);
        values.put(dbHelper.COLUMN_SCORE, score);

        db.insert(dbHelper.TABLE_NAME, null, values);
        db.close();
    }
}
