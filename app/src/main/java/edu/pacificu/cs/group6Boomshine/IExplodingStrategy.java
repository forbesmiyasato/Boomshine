package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.view.Display;
import java.util.ArrayList;

/**
 * Defines the abstract IExplodingStrategy class that creates
 * expanding circles with special behavior/attributes (power-ups).
 *
 * All power-up classes must inherit from this superclass
 * and implement a create() function.
 *
 * @author Thomas Robasciotti
 *
 * @version 1.0
 */

public abstract class IExplodingStrategy {

    /**
     * Creates and returns a collection of ExplodingBoundedMovingCircles
     * based on individual strategy behavior. Requires all information for
     * creating a normal ExplodingBoundedMovingCircle.
     *
     * @return A collection of ExplodingBoundedMovingCircles
     */

    public abstract ArrayList<ExplodingBoundedMovingCircle> create(Context context,
                                                                   Display display, int drawable,
                                                                   int topCoord, int leftCoord,
                                                                   int speed, int topBound,
                                                                   int bottomBound, int leftBound,
                                                                   int rightBound,
                                                                   int expandingSpeed,
                                                                   float radius);
}
