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
 * @author Computer Science, Pacific University.
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
  public FixedSprite(Context context, Display display, int color,
                     int topCoord, int leftCoord)
  {
    super(context);
    mContext = context;
    mDisplay = display;
    mTopCoordinate = topCoord;
    mLeftCoordinate = leftCoord;
    mColor = color;
    ++mCount;
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
  }

  /**
   * Draws the bitmap to the canvas.
   *
   * @param canvas the canvas to draw to
   * @since 1.0
   */
  public void doDraw(Canvas canvas)
  {
    if (mColor == getResources().getColor(R.color.coin))
    {
      mBitmapImage = BitmapFactory.decodeResource(getContext().getResources(),
              R.drawable.coin);
      canvas.drawBitmap(mBitmapImage, (this.mLeftCoordinate),
              this.mTopCoordinate, null);
      mRadius = mBitmapImage.getWidth();
    } else
    {
      mPaint.setColor(mColor);
      if (mRadius < mLastRadius)
      {
        mOpacityAlpha = mOpacityAlpha - 5;
      }
      mPaint.setStyle(Paint.Style.FILL);
      mPaint.setAlpha(mOpacityAlpha);
      mLastRadius = mRadius;
      canvas.drawCircle(mLeftCoordinate, mTopCoordinate, mRadius, mPaint);
    }

  }

  /**
   * Retrieves the value of the top y coordinate.
   *
   * @return the top y coordinate value
   * @since 1.0
   */
  public int getTopCoordinate()
  {
    return mTopCoordinate;
  }

  /**
   * Retrieves the value of the left x coordinate.
   *
   * @return the left x coordinate value
   * @since 1.0
   */
  public int getLeftCoordinate()
  {
    return mLeftCoordinate;
  }

  /**
   * Retrieves the number of mMovingSprites created.
   *
   * @return number of mMovingSprites
   * @since 1.0
   */
  public int getCount()
  {
    return mCount;
  }

}