package edu.pacificu.cs.boomshine;

import android.content.Context;
import android.view.Display;

import java.util.Random;

/**
 * Defines the MovingSprite class which maintains the specifics of a sprite
 * including its location, size, and bitmap.
 *
 * @author Computer Science, Pacific University.
 *
 * @version 1.0
 */
public class MovingSprite extends FixedSprite
{
  protected int mSpeed;
  private double mAngle;
  protected int mDx;
  protected int mDy;
  private final static double ANGLE = 360;
  private Random cRandom = new Random();
  /**
   * Constructor that initializes the values associated with the sprite.
   *
   * @param context
   *          reference to application-specific resources
   *
   * @param display
   *          the display
   *
   * @param drawable
   *          reference to a bitmap
   *
   * @param topCoord
   *          the top coordinate of the sprite
   *
   * @param leftCoord
   *          the left coordinate of the sprite
   *
   *
   * @since 1.0
   */
  public MovingSprite (Context context, Display display, int drawable,
                       int topCoord, int leftCoord, int speed)
  {
    super (context, display, drawable, topCoord, leftCoord);
    mSpeed = speed;
    randomDirection();
  }

  /**
   * Sets the value of the top y coordinate.
   *
   * @return the top y coordinate value
   *
   * @since 1.0
   */
  public void setTopCoordinate (int topCoordinate)
  {
    mTopCoordinate = topCoordinate;
  }

  /**
   * Sets the value of the left x coordinate.
   *
   * @return the left x coordinate value
   *
   * @since 1.0
   */
  public void setLeftCoordinate (int leftCoordinate)
  {
    mLeftCoordinate = leftCoordinate;
  }

  public void move ()
  {
    setTopCoordinate(getTopCoordinate() + mDy);
    setLeftCoordinate(getLeftCoordinate() + mDx);
  }

  public void randomDirection() {
    mAngle = cRandom.nextDouble() * ANGLE;

    mDx = (int) (mSpeed * Math.sin(mAngle));
    mDy = (int) (mSpeed * Math.cos(mAngle));
  }
}
