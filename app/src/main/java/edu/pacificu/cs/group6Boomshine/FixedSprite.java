package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

/**
 * Defines the FixedSprite class which maintains the specifics of a sprite
 * including its location, size, and bitmap.
 *
 * @author Computer Science, Pacific University.
 *
 * @version 1.0
 */
public class FixedSprite extends ImageView
{
  protected Bitmap mBitmapImage;
  protected Display mDisplay;
  protected int mTopCoordinate;
  protected int mLeftCoordinate;
  private Context mContext;
  private static int mCount = 0;
  private Paint mPaint;
  protected int mRadius = BoomshineView.DEFAULT_BALL_RADIUS;
  private int mColor;
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
   * @since 1.0
   */
  public FixedSprite (Context context, Display display, int color,
                      int topCoord, int leftCoord)
  {
    super (context);
    mContext = context;
    mDisplay = display;
    mTopCoordinate = topCoord;
    mLeftCoordinate = leftCoord;
    mColor = color;
    ++mCount;
    mPaint = new Paint();
  }

  /**
   * Draws the bitmap to the canvas.
   *
   * @param canvas
   *          the canvas to draw to
   *
   * @since 1.0
   */
  public void doDraw (Canvas canvas)
  {
//    canvas.drawBitmap (mBitmapImage, (this.mLeftCoordinate),
//            this.mTopCoordinate, null);
    mPaint.setColor(mColor);
    mPaint.setAntiAlias(true);
    canvas.drawCircle(mLeftCoordinate, mTopCoordinate, mRadius, mPaint);

  }

  /**
   * Retrieves the value of the top y coordinate.
   *
   * @return the top y coordinate value
   *
   * @since 1.0
   */
  public int getTopCoordinate ()
  {
    return mTopCoordinate;
  }

  /**
   * Retrieves the value of the left x coordinate.
   *
   * @return the left x coordinate value
   *
   * @since 1.0
   */
  public int getLeftCoordinate ()
  {
    return mLeftCoordinate;
  }

  /**
   * Retrieves the number of movingSprites created.
   *
   * @return number of movingSprites
   *
   * @since 1.0
   */
  public int getCount ()
  {
    return mCount;
  }

}