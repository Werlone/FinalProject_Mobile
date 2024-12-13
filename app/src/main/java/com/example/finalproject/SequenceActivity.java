package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceActivity extends AppCompatActivity {

    private ImageView sequenceImage;
    private List<String> sequenceList;
    private List<String> userSequence;
    private int roundNumber;
    private int currentIndex;
    private int score;

    private int[] drawableIds = {
            R.drawable.black_circle,
            R.drawable.green_circle,
            R.drawable.blue_circle,
            R.drawable.red_circle
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);

        sequenceImage = findViewById(R.id.sequence_image);
        sequenceList = new ArrayList<>();
        roundNumber = 1;
        score = 0;
        currentIndex = 0;

        // Get the round number and sequence list passed from the Game activity
        if (getIntent() != null && getIntent().hasExtra("roundNumber") && getIntent().hasExtra("score")) {
            roundNumber = getIntent().getIntExtra("roundNumber", 1); // Default to 1 if not passed
            score = getIntent().getIntExtra("score", 0);
        }

        // Create the sequence by choosing random drawables
        generateSequence();

        // Start showing the sequence
        showNextImage();
    }

    private void generateSequence() {
        Random random = new Random();
        for (int i = 0; i < 4 + (roundNumber-1)*2; i++) {
            int drawableIndex = random.nextInt(drawableIds.length);
            sequenceList.add("drawable" + (drawableIndex + 1)); // Add the name of the drawable to the list
        }
    }

    private void showNextImage() {
        if (currentIndex < sequenceList.size()) {
            // Get the drawable index from the sequence list
            String drawableName = sequenceList.get(currentIndex);
            int drawableIndex = getDrawableIndexFromName(drawableName);

            // Show the corresponding drawable for 1 second
            sequenceImage.setImageResource(drawableIds[drawableIndex]);

            // Increment the index to move to the next drawable in the sequence
            currentIndex++;

            // Hide the drawable after 1 second
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sequenceImage.setImageResource(0); // Hide the image
                }
            }, 1000); // After 1 second, hide the drawable

            // Schedule to show the next drawable after 2 seconds (1 second for showing and 1 second for hiding)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showNextImage();  // Recursively show the next drawable
                }
            }, 2000); // Wait for 2 seconds before showing the next image
        } else {
            // Once the sequence has been fully shown, move to the game screen
            startGame();
        }
    }

    private int getDrawableIndexFromName(String drawableName) {
        switch (drawableName) {
            case "drawable1":
                return 0;
            case "drawable2":
                return 1;
            case "drawable3":
                return 2;
            case "drawable4":
                return 3;
            default:
                return -1; // Invalid drawable name
        }
    }

    private void startGame() {
        // Send sequence and round number to the Game Activity
        Intent intent = new Intent(SequenceActivity.this, GameActivity.class);
        intent.putStringArrayListExtra("sequenceList", new ArrayList<>(sequenceList));
        intent.putExtra("roundNumber", roundNumber);
        intent.putExtra("score", score);
        startActivity(intent);
        finish(); // Finish the current SequenceActivity to prevent going back to it
    }
}
