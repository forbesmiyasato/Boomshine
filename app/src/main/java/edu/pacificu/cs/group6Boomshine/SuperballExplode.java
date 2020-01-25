package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Rect;
import android.view.Display;
import java.util.ArrayList;
import java.util.Random;

/**
 * Defines the SuperballExplode class that generates an initial
 * expanding circle that will expand to twice the size of a
 * normal expanding circle.
 *
 * This class extends the abstract IExplodingStrategy class.
 *
 * @author Thomas Robasciotti
 *
 * @version 1.0
 */

public class SuperballExplode {

    private final int EXPANTION_MULTIPLIER = 2;

    /**
     * Overrides the superclass create method.
     *
     * Generates an ArrayList of one expanding circle that expands
     * to twice the size of a normal expanding circle.
     *
     * @return An ArrayList containing one expanding circle
     */

    public ArrayList<ExplodingBoundedMovingCircle> createSuper(ExplodingType eType, Context context, Display display,
                                                          int drawable, int topCoord,
                                                          int leftCoord, int speed, int topBound,
                                                          int bottomBound, int leftBound,
                                                          int rightBound,
                                                          int radius) {

        ArrayList<ExplodingBoundedMovingCircle> cRetList = new ArrayList<>();


        cRetList.add (new ExplodingBoundedMovingCircle(eType, context, display, drawable, topCoord,
                leftCoord, speed, topBound, bottomBound, leftBound, rightBound,
                radius));

        return cRetList;
    }
}
