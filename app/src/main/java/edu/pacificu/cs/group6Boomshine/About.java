package edu.pacificu.cs.group6Boomshine;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Creates an About class that defines the About activity.
 * This class sets the content view of the application to
 * the activity_about layout.
 *
 * @author Thomas Robasciotti
 * @version 1.0
 * @since 1.23.2019
 */

public class About extends AppCompatActivity {

    /**
     * Overrides the super.onCreate method to define activity creation.
     * CSets the content view to the About activity.
     *
     * @param savedInstanceState a bundle containing any saved state from a
     *                           previous instance of this activity
     */

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
