package edu.pacificu.cs.group6Boomshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Intent intent = getIntent();
        int finalScore = intent.getIntExtra("player_score", 0);

        TextView playerScoreTextView = findViewById(R.id.textViewPlayerScore);

        playerScoreTextView.setText(finalScore);
    }
}
