package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
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
  ArrayList<ExplodingBoundedMovingCircle> movingSprites;
  ArrayList<ExplodingBoundedMovingCircle> explodingSprites;
  private int mHeight;
  private int mWidth;
  private boolean firstClick = true;
  private boolean firstRender = true;
  private Context mContext;
  private Display mDisplay;

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
    movingSprites = new ArrayList<>();
    explodingSprites = new ArrayList<>();
    mContext = context;
    mDisplay = display;
  }

  /**
   * Draw method that is repeatedly called for animation
   *
   * @param canvas used to host the draw calls
   * @since 1.0
   */

  @Override
  public void onDraw(Canvas canvas) {
    mHeight = getHeight();
    mWidth = getWidth();
//    if (firstClick)
//    {
//
//    }

    if (firstRender) {
      setCircles(1, mHeight, mWidth);
      firstRender = false;
    }
    for (ExplodingBoundedMovingCircle movingSprite : movingSprites) {
      movingSprite.move();
      movingSprite.hitBound();
      movingSprite.doDraw(canvas);
      for (ExplodingBoundedMovingCircle explodingSprite : explodingSprites) {
        explodingSprite.doDraw(canvas);
        movingSprite.collide(explodingSprite);
        explodingSprites.add(movingSprite);
        movingSprites.remove(movingSprite);
      }
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
    if (firstClick) {
      BitmapFactory.Options dimensions = new BitmapFactory.Options();
      dimensions.inJustDecodeBounds = true;
      Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
              R.drawable.ball_blue, dimensions);
      int spriteHeight = dimensions.outHeight;
      int spriteWidth = dimensions.outWidth;
      Point size = new Point();
      getDisplay().getSize(size);
      int Width = size.x;
      int Height = size.y;
      int speed = new Random().nextInt(20) + 1;
      ExplodingBoundedMovingCircle cNew = new ExplodingBoundedMovingCircle(getContext(), getDisplay(),
              id, (int) event.getY() - spriteHeight, (int) event.getX() - spriteWidth, 0, 0, mHeight, 0, mWidth, 0, 25);
      explodingSprites.add(cNew);
    }

    firstClick = false;
    return true;
  }

  void setCircles(int level, int topCoord, int LeftCoord) {
    Random random = new Random();
    int topBound;
    int leftBound;

    for (int i = 0; i < level * 100; i++) {
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
      int spriteWidth = dimensions.outWidth;
      Log.d("setCircles", String.valueOf(LeftCoord));
      Log.d("setCircles", String.valueOf(spriteWidth));
      topBound = random.nextInt(topCoord - spriteHeight * 2) + spriteHeight;
      leftBound = random.nextInt(LeftCoord - spriteWidth * 2) + spriteWidth;
      Log.d("setCircles", String.valueOf(leftBound));
      int speed = new Random().nextInt(20) + 2;
      Log.d("setCircles", String.valueOf(speed));
      ExplodingBoundedMovingCircle cNew = new ExplodingBoundedMovingCircle(mContext, mDisplay,
              id, topBound - spriteHeight, leftBound - spriteWidth, speed, 0, mHeight - spriteHeight, 0, mWidth - spriteWidth, 0, 25);
      movingSprites.add(cNew);
    }
  }
}