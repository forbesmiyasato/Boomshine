package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Defines the View for displaying the animation.
 *
 * @author Computer Science, Pacific University.
 * @version 1.0
 */
public class BoomshineView extends ImageView {
  ArrayList<BoundedMovingSprite> sprites;
  private int mHeight;
  private int mWidth;
  /**
   * Constructor that initializes the values associated with the sprite.
   *
   * @param context reference to application-specific resources
   * @param display the display
   * @since 1.0
   */
  public BoomshineView(Context context, Display display) {
    super(context);
    setFocusable(true); // make sure we get key events
    sprites = new ArrayList<BoundedMovingSprite>();
  }

  /**
   * Draw method that is repeatedly called for animation
   *
   * @param canvas used to host the draw calls
   * @since 1.0
   */

  @Override
  public void onDraw(Canvas canvas) {
    mHeight = canvas.getHeight();
    mWidth = canvas.getWidth();
    for (BoundedMovingSprite sprite : sprites) {
      sprite.move();
      sprite.hitBound();
      sprite.doDraw(canvas);
    }
    super.onDraw(canvas);
    invalidate();
  }


  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() != MotionEvent.ACTION_DOWN) {
      return super.onTouchEvent(event);
    }

    Random random = new Random();
    int randomId = random.nextInt(3);
    int id = 0;

    switch (randomId) {
      case 0:
        id = R.drawable.ball_blue;
        break;
      case 1:
        id = R.drawable.ball_green;
        break;
      case 2:
        id = R.drawable.ball_yellow;
        break;
    }
    BitmapFactory.Options dimensions = new BitmapFactory.Options();
    dimensions.inJustDecodeBounds = true;
    Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
            R.drawable.ball_blue, dimensions);
    int spriteHeight = dimensions.outHeight;
    int spriteWidth =  dimensions.outWidth;
    Point size = new Point();
    getDisplay().getSize(size);
    int Width = size.x;
    int Height = size.y;
    int speed = new Random().nextInt(20) + 1;
    BoundedMovingSprite cNew = new BoundedMovingSprite(getContext(), getDisplay(),
            id, (int) event.getY() - spriteHeight, (int) event.getX() - spriteWidth, speed,0, mHeight, 0, mWidth);
    sprites.add(cNew);
    return true;
  }
}