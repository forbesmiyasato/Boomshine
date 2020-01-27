package edu.pacificu.cs.group6Boomshine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Defines the GameOverActivity class that contains display and
 * interaction logic for the Game Over screen.
 *
 * @author Forbes Miyasato & Thomas Robasciotti
 * @version 1.0
 * @since 1.26.2019
 */

public class GameOverActivity extends AppCompatActivity {

    TextView mPlayerScoreTextView;
    Button mBtnPlayAgain;
    private int mPlayerScore;
    private String mPlayerName;

    /**
     * Initializes member variables to default values.
     * Extracts the player's overall score and name
     * from the triggering Intent object.
     *
     * @param savedInstanceState A Bundle containing previously saved
     *                           Activity state (un-used)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent tempIntent = getIntent();

        mPlayerScore = tempIntent.getIntExtra("player_score", 0);
        mPlayerName = tempIntent.getStringExtra("Username");

        mPlayerScoreTextView = findViewById(R.id.textViewPlayerscoreFinal);
        mBtnPlayAgain = findViewById(R.id.btnPlayAgain);

        mPlayerScoreTextView.setText(Integer.toString(mPlayerScore));
    }

    /**
     * Event handler for the Play Again button.
     *
     * Uses the player name (if available) to redirect the user
     * to the Game Menu screen
     *
     * @param view The view that triggered the event
     */

    public void onPlayAgianClicked (View view)
    {
        Intent playAgain = new Intent(this, BoomshineGame.class);
        if (mPlayerName != null)
        {
            playAgain.putExtra("Username", mPlayerName);

        }
        startActivity (playAgain);
    }

    /**
     * Event handler for the Quit button.
     *
     * Creates and starts an Intent that returns the user to the
     * Main page. This is equivalent to logging out (if logged in)
     *
     * @param view The view that triggered the event
     */
    public void onQuitClicked (View view)
    {
        Intent quit = new Intent(this, MainActivity.class);
        startActivity(quit);
    }
}
