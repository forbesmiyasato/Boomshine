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
  static final int DEFAULT_BALL_RADIUS = 30;
  ArrayList<ExplodingBoundedMovingCircle> movingSprites;
  ArrayList<ExplodingBoundedMovingCircle> explodingSprites;
  private int mHeight;
  private int mWidth;
  private boolean firstClick = true;
  private boolean firstRender = true;
  private Context mContext;
  private Display mDisplay;
  private Level mLevel;

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
    mLevel = new Level(1);
  }

  /**
   * Draw method that is repeatedly called for animation
   *
   * @param canvas used to host the draw calls
   * @since 1.0
   */

  @Override
  public void onDraw(Canvas canvas) {
    ExplodingBoundedMovingCircle cTemp = null;
    ExplodingBoundedMovingCircle cTemp1 = null;
    mHeight = getHeight();
    mWidth = getWidth();

    if (firstRender) {
      setCircles(mLevel.getLevelNumber());
      firstRender = false;
    }

    for (ExplodingBoundedMovingCircle explodingSprite : explodingSprites) {
      explodingSprite.doDraw(canvas);
      if (explodingSprite.handleExploding()) {
        cTemp1 = explodingSprite;
      }
    }

    for (ExplodingBoundedMovingCircle movingSprite : movingSprites) {
      movingSprite.move();
      movingSprite.hitBound();
      movingSprite.doDraw(canvas);
      for (ExplodingBoundedMovingCircle explodingSprite : explodingSprites) {
        if (movingSprite.collide(explodingSprite)) {
          cTemp = movingSprite;
          mLevel.incrememtCirclesHit();
        }
      }
    }

    if (cTemp1 != null) {
      explodingSprites.remove(cTemp1);
    }
    if (cTemp != null) {
      explodingSprites.add(cTemp);
      movingSprites.remove(cTemp);
    }

    if (explodingSprites.isEmpty()) {
      if (mLevel.levelOver()) {
        mLevel.nextLevel();
        firstRender = true;
        firstClick = true;
        movingSprites.clear();
        explodingSprites.clear();
      }
    }

    super.onDraw(canvas);
    invalidate();
  }


  @Override
  public boolean onTouchEvent(MotionEvent event) {
    int color;
    if (event.getAction() != MotionEvent.ACTION_DOWN) {
      return super.onTouchEvent(event);
    }

    if (firstClick) {
      color = getRandomColor();
      ExplodingBoundedMovingCircle cNew = new ExplodingBoundedMovingCircle(getContext(), getDisplay(),
              color, (int) event.getY(),
              (int) event.getX(), 0, 0, mHeight,
              0, mWidth, 0, 25);
      explodingSprites.add(cNew);
    }

    firstClick = false;
    return true;
  }

  void setCircles(int level) {
    int topBound;
    int leftBound;
    Random random = new Random();
    int color;
    for (int i = 0; i < level * 5; i++) {
      color = getRandomColor();
      topBound = random.nextInt(mHeight - DEFAULT_BALL_RADIUS * 2) + DEFAULT_BALL_RADIUS;
      leftBound = random.nextInt(mWidth - DEFAULT_BALL_RADIUS * 2) + DEFAULT_BALL_RADIUS;
      int speed = new Random().nextInt(20) + 2;
      ExplodingBoundedMovingCircle cNew = new ExplodingBoundedMovingCircle(mContext, mDisplay,
              color, topBound - DEFAULT_BALL_RADIUS,
              leftBound - DEFAULT_BALL_RADIUS, speed, 0,
              mHeight, 0,
              mWidth, 0, 25);
      movingSprites.add(cNew);
    }
  }

  public int getRandomColor() {
    Random random = new Random();
    int randomColor = random.nextInt(5);
    int color = 0;
    switch (randomColor) {
      case 0:
        color = getResources().getColor(R.color.cGrass1);
        break;
      case 1:
        color = getResources().getColor(R.color.cYellow);
        break;
      case 2:
        color = getResources().getColor(R.color.cBlue);
        break;
      case 3:
        color = getResources().getColor(R.color.cOrange);
        break;
      case 4:
        color = getResources().getColor(R.color.cPurple);
        break;
    }
    return color;
  }
}