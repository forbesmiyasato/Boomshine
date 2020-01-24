package edu.pacificu.cs.group6Boomshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView mPlayerScoreTextView;
    private int mPlayerScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent tempIntent = getIntent();
        mPlayerScore = tempIntent.getIntExtra("player_score", 0);

        mPlayerScoreTextView = findViewById(R.id.textViewPlayerscoreFinal);

        mPlayerScoreTextView.setText(Integer.toString(mPlayerScore));
    }
}
