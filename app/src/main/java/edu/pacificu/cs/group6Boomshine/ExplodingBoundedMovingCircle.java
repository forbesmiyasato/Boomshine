package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.view.Display;

/**
 * Creates an ExplodingBoundedMovingCircle class that extends a BoundedMovingSprite.
 * Defines a BoundedMovingSprite that has "explosion" resizing behavior.
 * Exploding behavior is defined by the ExplodingStrategy (defined by the ExplodingType)
 * and is modified by power ups when available.
 *
 * @author Forbes Miyasato & Thomas Robasciotti
 * @version 1.0
 * @since 1.24.2019
 */

public class ExplodingBoundedMovingCircle extends BoundedMovingSprite
{

  private int mExplosionProgress;
  private boolean bExploding = true;
  protected final int mStartPositionTop;
  private int mStartRadius;
  private ExplodingType meType;
  private IExpansionStrategy mStrategy;

  /**
   * Initializes member variables with passed-in values
   * after calling superclass constructor.
   * Sets circle explosion strategy based on passed-in
   * explosion type value
   *
   * @param eType       The ExplodingType for the created circle
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
   */
  ExplodingBoundedMovingCircle (ExplodingType eType, Context context,
                                Display display, int drawable, int topCoord,
                                int leftCoord, int speed, int topBound,
                                int bottomBound, int leftBound, int rightBound,
                                int radius)
  {
    super (context, display, drawable, topCoord, leftCoord, speed, topBound,
      bottomBound, leftBound, rightBound);
    mRadius = radius;
    mStartRadius = radius;
    mExplosionProgress = mRadius;
    mStartPositionTop = topCoord;
    meType = eType;

    switch (meType)
    {
      case NORMAL:
        mStrategy = new handleExplodingNormal ();
        break;
      case SUPER:
        mStrategy = new handleExplodingSuper ();
        break;
      case MULTI:
        mStrategy = new handleExplodingMulti ();
        break;
      case ULTIMATE:
        mStrategy = new handleExplodingUltra ();
        break;
    }
  }

  /**
   * Retrieves the ExplodingBoundedMovingCircle radius value
   *
   * @return The radius value
   */

  public float getRadius ()
  {
    return mRadius;
  }

  /**
   * Determines if a collision has occurred between two circles by
   * calculating distances between radius values.
   *
   * @param cOtherCircle The circle to check against this circle
   * @return True if a collision has occurred, otherwise false.
   */

  public boolean collide (ExplodingBoundedMovingCircle cOtherCircle)
  {
    final int SQUARE = 2;
    boolean bCollided = false;
    //(x2-x1)^2 + (y1-y2)^2 <= (r1+r2)^2 to check if collides
    float dRadius = cOtherCircle.getRadius () + this.getRadius ();
    float dX = (getLeftCoordinate ()) - (cOtherCircle.getLeftCoordinate ());
    float dY = (getTopCoordinate ()) - (cOtherCircle.getTopCoordinate ());

    if (Math.pow (dX, SQUARE) + Math.pow (dY, SQUARE) <=
      Math.pow (dRadius, SQUARE))
    {
      mSpeed = 0;
      bCollided = true;
    }

    return bCollided;
  }

  /**
   * Triggers a circle's explosion strategy and re-sizes/re-locates the circle
   * location based on results of explosion strategy method calls.
   *
   * @return True if the circle has finished its exploding behavior,
   * otherwise false.
   */

  public boolean handleExploding ()
  {
    int explosionRate = this.mStrategy.handleExploding ();
    int explosionRadiusMult = this.mStrategy.getRadiusMultiplier ();
    if (mExplosionProgress >= (explosionRadiusMult * mStartRadius))
    {
      bExploding = false;
    }

    mExplosionProgress += bExploding ? explosionRate : explosionRate * - 2;

    mRadius = mExplosionProgress;

    setLeftCoordinate (getLeftCoordinate ());
    setTopCoordinate (getTopCoordinate ());

    return mExplosionProgress <= 0;
  }
}
