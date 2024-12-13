package com.example.finalproject;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        Button playButton = findViewById(R.id.play_button);
        Button highscoresButton = findViewById(R.id.highscores_button);

        // Set up click listeners for buttons
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the GameActivity when the "Play" button is pressed
                Intent intent = new Intent(MainActivity.this, SequenceActivity.class);
                startActivity(intent);
            }
        });

        highscoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the HighscoresActivity when the "Highscores" button is pressed
                Intent intent = new Intent(MainActivity.this, HighscoresActivity.class);
                startActivity(intent);
            }
        });
    }
}