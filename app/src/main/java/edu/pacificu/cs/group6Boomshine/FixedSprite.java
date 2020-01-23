package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
  private int mResID;
  protected int mTopCoordinate;
  protected int mLeftCoordinate;
  private int mWidth;
  private int mHeight;
  private Context mContext;
  private static int mCount = 0;

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
  public FixedSprite (Context context, Display display, int drawable,
                      int topCoord, int leftCoord)
  {
    super (context);

    BitmapFactory.Options opts = new BitmapFactory.Options ();
    opts.inJustDecodeBounds = true;
    mBitmapImage = BitmapFactory.decodeResource (context.getResources (),
            drawable);
    mContext = context;
    mDisplay = display;
    mTopCoordinate = topCoord;
    mLeftCoordinate = leftCoord;
    mWidth = mBitmapImage.getWidth ();
    mHeight = mBitmapImage.getHeight ();
    mResID = drawable;
    ++mCount;
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
    canvas.drawBitmap (mBitmapImage, (this.mLeftCoordinate),
            this.mTopCoordinate, null);
  }

  /**
   * Retrieves the bitmap.
   *
   * @return the bitmap
   *
   * @since 1.0
   */
  public Bitmap getBitmap ()
  {
    return mBitmapImage;
  }

  /**
   * Retrieves the sprite's reference id.
   *
   * @return the reference id
   *
   * @since 1.0
   */
  public int getResID ()
  {
    return this.mResID;
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
   * Retrieves the number of sprites created.
   *
   * @return number of sprites
   *
   * @since 1.0
   */
  public int getCount ()
  {
    return mCount;
  }

  /**
   * Retrieves the width of the sprite.
   *
   * @return the width of the sprite
   *
   * @since 1.0
   */
  public int getSpriteWidth ()
  {
    return mWidth;
  }

  /**
   * Retrieves the height of the sprite
   *
   * @return the height of the sprite
   *
   * @since 1.0
   */
  public int getSpriteHeight ()
  {
    return mHeight;
  }

  /**
   * Retrieves the height of the display.
   *
   * @return the height of the display
   *
   * @since 1.0
   */
  public int getDisplayHeight ()
  {
    return mContext.getResources ().getDisplayMetrics ().heightPixels;
  }

  /**
   * Retrieves the width of the display.
   *
   * @return the width of the display
   *
   * @since 1.0
   */
  public int getDisplayWidth ()
  {
    return mContext.getResources ().getDisplayMetrics ().widthPixels;
  }

  public void setmBitmapImage(Bitmap mBitmapImage) {
    this.mBitmapImage = mBitmapImage;
  }
}