package com.example.finalproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private View drawable1, drawable2, drawable3, drawable4;
    private List<String> sequenceList;
    private List<String> userSequence;
    private TextView scoreDisplay;
    private int roundNumber;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (getIntent() != null && getIntent().hasExtra("score")) {
            score = getIntent().getIntExtra("score", 0);
        }

        scoreDisplay = findViewById(R.id.score_display);
            scoreDisplay.setText("Score: " + String.valueOf(score));
        drawable1 = findViewById(R.id.black_circle);
        drawable2 = findViewById(R.id.green_circle);
        drawable3 = findViewById(R.id.blue_circle);
        drawable4 = findViewById(R.id.red_circle);


        // Receive sequence and round number from SequenceActivity
        sequenceList = getIntent().getStringArrayListExtra("sequenceList");
        roundNumber = getIntent().getIntExtra("roundNumber", 1);
        userSequence = new ArrayList<>();

        // Setup click listeners for the images
        drawable1.setOnClickListener(v -> checkAnswer("drawable1"));
        drawable2.setOnClickListener(v -> checkAnswer("drawable2"));
        drawable3.setOnClickListener(v -> checkAnswer("drawable3"));
        drawable4.setOnClickListener(v -> checkAnswer("drawable4"));
    }

    private void checkAnswer(String drawableName) {
        userSequence.add(drawableName);

        // Check if the user made a correct choice
        if (userSequence.size() == sequenceList.size()) {
            boolean isCorrect = userSequence.equals(sequenceList);
            if (isCorrect) {
                score += 4; // Add score for correctly matched sequence
                roundNumber++;
                userSequence.clear();

                // If user passed the sequence, send incremented round number back to SequenceActivity
                Intent intent = new Intent(GameActivity.this, SequenceActivity.class);
                intent.putExtra("roundNumber", roundNumber);
                intent.putExtra("score", score);
                startActivity(intent);
                finish(); // End the game screen
            } else {
                // Game Over - Incorrect sequence
                gameOver();
            }
        }
    }

    private void gameOver() {
        // Start the Game Over Activity and pass the score
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }
}

