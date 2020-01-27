package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Display;
import android.widget.ImageView;

/**
 * Defines the FixedSprite class which maintains the specifics of a sprite
 * including its location, size, and bitmap.
 *
 * @author Computer Science, Pacific University. Modified by Forbes
 * @version 1.0
 */
public class FixedSprite extends ImageView
{
  protected Bitmap mcBitmapImage;
  protected Display mcDisplay;
  protected int mTopCoordinate;
  protected int mLeftCoordinate;
  private static int mCount = 0;
  private Paint mcPaint;
  protected int mRadius;
  private int mColor;
  private int mLastRadius = 0;
  private int mOpacityAlpha = 255;

  /**
   * Constructor that initializes the values associated with the sprite.
   *
   * @param context   reference to application-specific resources
   * @param display   the display
   * @param topCoord  the top coordinate of the sprite
   * @param leftCoord the left coordinate of the sprite
   * @since 1.0
   */
  public FixedSprite (Context context, Display display, int color,
                      int topCoord, int leftCoord)
  {
    super (context);
    mcDisplay = display;
    mTopCoordinate = topCoord;
    mLeftCoordinate = leftCoord;
    mColor = color;
    ++ mCount;
    mcPaint = new Paint ();
    mcPaint.setAntiAlias (true);
  }

  /**
   * Draws the bitmap to the canvas.
   *
   * @param canvas the canvas to draw to
   * @since 1.0
   */
  public void doDraw (Canvas canvas)
  {
    final int OPACITY_ALPHA_DECREMENT = 5;
    if (mColor == getResources ().getColor (R.color.coin))
    {
      mcBitmapImage = BitmapFactory.decodeResource
              (getContext ().getResources (), R.drawable.coin);
      canvas.drawBitmap (mcBitmapImage, (this.mLeftCoordinate),
        this.mTopCoordinate, null);
      mRadius = mcBitmapImage.getWidth ();
    } else
    {
      mcPaint.setColor (mColor);

      if (mRadius < mLastRadius && mOpacityAlpha > OPACITY_ALPHA_DECREMENT)
      {
        mOpacityAlpha = mOpacityAlpha - OPACITY_ALPHA_DECREMENT;
      }
      mcPaint.setStyle (Paint.Style.FILL);
      mcPaint.setAlpha (mOpacityAlpha);
      mLastRadius = mRadius;
      canvas.drawCircle (mLeftCoordinate, mTopCoordinate, mRadius, mcPaint);
    }

  }

  /**
   * Retrieves the value of the top y coordinate.
   *
   * @return the top y coordinate value
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
   * @since 1.0
   */
  public int getLeftCoordinate ()
  {
    return mLeftCoordinate;
  }
}