package edu.pacificu.cs.group6Boomshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView mPlayerScoreTextView;
    Button mBtnPlayAgain;
    private int mPlayerScore;
    private int mMultiPowerups;
    private int mSuperPowerups;
    private int mUltraPowerups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent tempIntent = getIntent();

        mPlayerScore = tempIntent.getIntExtra("player_score", 0);

        mPlayerScoreTextView = findViewById(R.id.textViewPlayerscoreFinal);
        mBtnPlayAgain = findViewById(R.id.btnPlayAgain);

        mPlayerScoreTextView.setText(Integer.toString(mPlayerScore));
    }

    public void onPlayAgianClicked (View view)
    {
        startActivity (new Intent(this,
                BoomshineGame.class));
    }
}
