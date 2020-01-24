package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Rect;
import android.view.Display;
import java.util.ArrayList;
import java.util.Random;

/**
 * Defines the MultiballExplode class that generates an additional
 * initial expanding circle in a random location on the screen.
 *
 * This class extends the abstract IExplodingStrategy class.
 *
 * @author Thomas Robasciotti
 *
 * @version 1.0
 */

public class MultiballExplode {

    /**
     * Overrides the superclass create method.
     *
     * Generates an ArrayList of two expanding circles; one where the user touched
     * on the screen and another in a random location within the screen bounds.
     *
     * @return An ArrayList containing two expanding circles
     */

    public ArrayList<ExplodingBoundedMovingCircle> createMulti(Context context, Display display,
                                                          int drawable, int topCoord,
                                                          int leftCoord, int speed, int topBound,
                                                          int bottomBound, int leftBound,
                                                          int rightBound, int expandingSpeed,
                                                          int radius) {

        ArrayList<ExplodingBoundedMovingCircle> cRetList = new ArrayList<>();
        Random cRandom = new Random();
        Rect cDisplaySize = new Rect();

        display.getRectSize(cDisplaySize);

        int randomXCoord = cRandom.nextInt(cDisplaySize.width());
        int randomYCoord = cRandom.nextInt(cDisplaySize.height());

        cRetList.add (new ExplodingBoundedMovingCircle(context, display, drawable, topCoord,
                leftCoord, speed, topBound, bottomBound, leftBound, rightBound, expandingSpeed,
                radius));

        cRetList.add (new ExplodingBoundedMovingCircle(context, display, drawable, randomYCoord,
                randomXCoord, speed, topBound, bottomBound, leftBound, rightBound, expandingSpeed,
                radius));

        return cRetList;
    }
}
