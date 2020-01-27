package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Rect;
import android.view.Display;

import java.util.ArrayList;
import java.util.Random;

/**
 * Creates a MultiballExplode class that defines creation of
 * the Multi Ball ExplodingBoundedMovingCircle power up.
 *
 * @author Thomas Robasciotti
 * @version 1.0
 * @since 1.24.2019
 */

public class MultiballExplode
{

  private final int RADIUS_MULTIPLIER = 2;
  /**
   * Generates an ArrayList of ExplodingBoundedMovingCircles with
   * required Multi Ball behavior.
   * Generates an additional normal ball at a random location on the
   * screen.
   *
   * @param eType       The desired ExplosionType type
   * @param context     The calling context object
   * @param display     The calling display object
   * @param drawable    The drawable id value
   * @param topCoord    The top coordinate of created circle
   * @param leftCoord   The left coordinate of created circle
   * @param speed       The initial speed of the created circle
   * @param topBound    The top bound for the created circle
   * @param bottomBound The bottom bound for the created circle
   * @param leftBound   The left bound for the created circle
   * @param rightBound  The right bound for the created circle
   * @param radius      The initial radius of the created circle
   */

  public ArrayList<ExplodingBoundedMovingCircle> createMulti (
      ExplodingType eType, Context context, Display display, int drawable,
      int topCoord, int leftCoord, int speed, int topBound, int bottomBound,
      int leftBound, int rightBound, int radius)
  {

    ArrayList<ExplodingBoundedMovingCircle> cRetList = new ArrayList<> ();
    Random cRandom = new Random ();
    Rect cDisplaySize = new Rect ();

    display.getRectSize (cDisplaySize);

    int randomXCoord = cRandom.nextInt (cDisplaySize.width () -
      BoomshineView.DEFAULT_BALL_RADIUS * RADIUS_MULTIPLIER) +
      BoomshineView.DEFAULT_BALL_RADIUS;
    int randomYCoord = cRandom.nextInt (cDisplaySize.height () -
      BoomshineView.DEFAULT_BALL_RADIUS * RADIUS_MULTIPLIER) +
      BoomshineView.DEFAULT_BALL_RADIUS;

    cRetList.add (new ExplodingBoundedMovingCircle (eType, context, display,
      drawable, topCoord, leftCoord, speed, topBound, bottomBound, leftBound,
      rightBound, radius));

    cRetList.add (new ExplodingBoundedMovingCircle (eType, context, display,
      drawable, randomYCoord, randomXCoord, speed, topBound, bottomBound,
      leftBound, rightBound, radius));

    return cRetList;
  }
}
