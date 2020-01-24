package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
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
  ArrayList<ExplodingBoundedMovingCircle> mMovingSprites;
  ArrayList<ExplodingBoundedMovingCircle> mExplodingSprites;
  private int mHeight;
  private int mWidth;
  private boolean firstClick = true;
  private boolean firstRender = true;
  private Context mContext;
  private Display mDisplay;
  private Level mLevel;
  private Paint mPaint;
  private MediaPlayer mcMediaPlayer;
  private boolean donePlayingSound = false;

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
    mMovingSprites = new ArrayList<>();
    mExplodingSprites = new ArrayList<>();
    mContext = context;
    mDisplay = display;
    mLevel = new Level(1);
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
  }

  /**
   * Draw method that is repeatedly called for animation
   *
   * @param canvas used to host the draw calls
   * @since 1.0
   */

  @Override
  public void onDraw(Canvas canvas) {
    ExplodingBoundedMovingCircle cCollidedMovingCircle = null;
    ExplodingBoundedMovingCircle mExplodedCircle = null;
    mHeight = getHeight();
    mWidth = getWidth();

    //Paint Score on screen
    mPaint.setColor(getResources().getColor(R.color.cWhite));
    mPaint.setTextSize(50);
    canvas.drawText(mLevel.getHitInfo(), 10, mHeight - 50, mPaint);

    if (firstRender) {
      setCircles(mLevel.getLevelNumber());
      firstRender = false;
    }

    for (ExplodingBoundedMovingCircle explodingSprite : mExplodingSprites) {
      explodingSprite.doDraw(canvas);
      if (explodingSprite.handleExploding()) {
        mExplodedCircle = explodingSprite;
      }
    }

    for (ExplodingBoundedMovingCircle movingSprite : mMovingSprites) {
      movingSprite.move();
      movingSprite.hitBound();
      movingSprite.doDraw(canvas);
      for (ExplodingBoundedMovingCircle explodingSprite : mExplodingSprites) {
        if (movingSprite.collide(explodingSprite)) {
          cCollidedMovingCircle = movingSprite;
          mLevel.incrememtCirclesHit();
        }
      }
    }

    if (mExplodedCircle != null) {
      mExplodingSprites.remove(mExplodedCircle);
    }
    if (cCollidedMovingCircle != null) {
      mExplodingSprites.add(cCollidedMovingCircle);
      mMovingSprites.remove(cCollidedMovingCircle);
    }

    if (!firstClick && mExplodingSprites.isEmpty()) {
      if (mLevel.levelOver()) {
        levelPassed();
      } else {
        levelFailed();
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
      mExplodingSprites.add(cNew);
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
      mMovingSprites.add(cNew);
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

  public void levelPassed() {
    mLevel.nextLevel();
    firstRender = true;
    firstClick = true;
    mMovingSprites.clear();
    mExplodingSprites.clear();
    mcMediaPlayer = MediaPlayer.create(getContext(), R.raw.win_sound);
    mcMediaPlayer.start();
  }

  public void levelFailed() {
    if (!donePlayingSound) {
      mcMediaPlayer = MediaPlayer.create(getContext(), R.raw.lose_sound);
      mcMediaPlayer.start();
    }
    donePlayingSound = true;
  }
}