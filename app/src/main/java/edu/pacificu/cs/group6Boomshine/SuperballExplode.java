package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.view.Display;

import java.util.ArrayList;

/**
 * Creates a SuperballExplode class that defines creation of
 * the Super Ball ExplodingBoundedMovingCircle power up.
 *
 * @author Thomas Robasciotti
 * @version 1.0
 * @since 1.24.2019
 */

public class SuperballExplode
{

  /**
   * Generates an ArrayList of ExplodingBoundedMovingCircles with
   * required Super Ball behavior.
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

  public ArrayList<ExplodingBoundedMovingCircle> createSuper (
      ExplodingType eType, Context context, Display display, int drawable,
      int topCoord, int leftCoord, int speed, int topBound, int bottomBound,
      int leftBound, int rightBound, int radius)
  {
    ArrayList<ExplodingBoundedMovingCircle> cRetList = new ArrayList<> ();

    cRetList.add (new ExplodingBoundedMovingCircle (eType, context, display,
      drawable, topCoord, leftCoord, speed, topBound, bottomBound, leftBound,
      rightBound, radius));

    return cRetList;
  }
}
