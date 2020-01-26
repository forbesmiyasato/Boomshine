package edu.pacificu.cs.group6Boomshine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

import static edu.pacificu.cs.group6Boomshine.FixedSprite.DEFAULT_BALL_RADIUS;

/**
 * Defines the View for displaying the animation.
 *
 * @author Computer Science, Pacific University.
 * @version 1.0
 */

@SuppressLint("AppCompatCustomView")
public class BoomshineView extends ImageView {
  private final int MAX_LEVEL_ATTEMPTS = 3;
  ArrayList<ExplodingBoundedMovingCircle> mMovingSprites;
  ArrayList<ExplodingBoundedMovingCircle> mExplodingSprites;
  ExplodingCircleFactory mcFactory;
  private int mHeight;
  private int mWidth;
  private boolean firstClick = true;
  private boolean firstRender = true;
  private Context mContext;
  private Display mDisplay;
  private Level mLevel;
  private Paint mPaint;
  private MediaPlayer mcMediaPlayer;
  private IconHandler mcIconHandler;
  private boolean donePlayingSound = false;
  private int mTotalScore;
  private int mNumAttempts;
  private int mCurrentLevel;
  private boolean mGameEnd;
  private ExplodingType meType;

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
    mCurrentLevel = 1;
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mTotalScore = 0;
    mNumAttempts = 0;
    mGameEnd = false;
    mcFactory = new ExplodingCircleFactory();
    mcIconHandler = new IconHandler(context);
    meType = ExplodingType.NORMAL;
  }

  /**
   * Draw method that is repeatedly called for animation
   *
   * @param canvas used to host the draw calls
   * @since 1.0
   */

  @Override
  public void onDraw(Canvas canvas) {
    if (!mGameEnd){
      ExplodingBoundedMovingCircle cCollidedMovingCircle = null;
      ExplodingBoundedMovingCircle cExplodedCircle = null;
      mHeight = getHeight();
      mWidth = getWidth();

      if (firstRender) {
        setCircles(mLevel.getLevelNumber());
        firstRender = false;
      }

      for (ExplodingBoundedMovingCircle explodingSprite : mExplodingSprites) {
        explodingSprite.doDraw(canvas);
        if (explodingSprite.handleExploding()) {
          cExplodedCircle = explodingSprite;
        }
      }

      for (ExplodingBoundedMovingCircle movingSprite : mMovingSprites) {
        movingSprite.move();
        movingSprite.hitBound();
        movingSprite.doDraw(canvas);
        for (ExplodingBoundedMovingCircle explodingSprite : mExplodingSprites) {
          if (movingSprite.collide(explodingSprite)) {
            cCollidedMovingCircle = movingSprite;
          }
        }
      }

      if (cExplodedCircle != null) {
        mExplodingSprites.remove(cExplodedCircle);
      }
      if (cCollidedMovingCircle != null) {
        mExplodingSprites.add(cCollidedMovingCircle);
        mLevel.incrememtCirclesHit();
        mMovingSprites.remove(cCollidedMovingCircle);
      }

      if (!firstClick && mExplodingSprites.isEmpty()) {
        if (mLevel.levelOver()) {
          levelPassed();
        } else {
          levelFailed();
        }
      }

      //Paint Score on screen
      mPaint.setColor(getResources().getColor(R.color.cWhite));
      setTextSizeForWidth(mPaint, mWidth / 4, mLevel.getHitInfo());
      //mPaint.setTextSize(50);
      canvas.drawText(mLevel.getHitInfo(), 10, 50, mPaint);
      canvas.drawText("Level: " + mCurrentLevel, mWidth - 8 * (mWidth / 15), 50, mPaint);
      canvas.drawText("Attempts: " + (mNumAttempts + 1) + " / " + MAX_LEVEL_ATTEMPTS,
              mWidth - (mWidth / 4), 50, mPaint);

      canvas.drawText("Total Score: " + mTotalScore, 10,mHeight - 50, mPaint);

      mcIconHandler.drawIcons(canvas);

      super.onDraw(canvas);
      invalidate();
    }
    else
    {
      Intent gameOverIntent = new Intent (this.mContext, GameOverActivity.class);
      gameOverIntent.putExtra ("player_score", mTotalScore);
      mContext.startActivity(gameOverIntent);
    }
  }


  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (mcMediaPlayer != null)
    {
      mcMediaPlayer.release();
    }
    if (!donePlayingSound)
    {
      mcMediaPlayer = MediaPlayer.create(getContext(), R.raw.click_sound);
      mcMediaPlayer.start();
    }

    int color;
    ExplodingType eType = ExplodingType.NORMAL;
    int xTouchPos = (int) event.getX();
    int yTouchPos = (int) event.getY();

    if (event.getAction() != MotionEvent.ACTION_DOWN) {
      return super.onTouchEvent(event);
    }

    if (firstClick) {
      color = getRandomColor();

      if (mcIconHandler.checkIconBounds(xTouchPos, yTouchPos))
      {
        meType = mcIconHandler.checkPress(xTouchPos, yTouchPos);
      }
      else
      {
        mExplodingSprites.addAll(mcFactory.create(meType, getContext(), getDisplay(),
                color, (int) event.getY() + DEFAULT_BALL_RADIUS / 2,
                (int) event.getX() + DEFAULT_BALL_RADIUS / 2, 0, 0, mHeight,
                0, mWidth, DEFAULT_BALL_RADIUS));
        firstClick = false;
      }
    }

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
      int speed = 3;
      //int speed = new Random().nextInt(20) + 2;

      ExplodingBoundedMovingCircle cNew = new ExplodingBoundedMovingCircle(ExplodingType.NORMAL,
              mContext, mDisplay, color, topBound - DEFAULT_BALL_RADIUS,
              leftBound - DEFAULT_BALL_RADIUS, speed, 0,
              mHeight, 0,
              mWidth, DEFAULT_BALL_RADIUS);
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
    mTotalScore += mLevel.getLevelScore();
    mNumAttempts = 0;
    mLevel.nextLevel();
    mCurrentLevel++;
    resetLevelFlags();
    if (mcMediaPlayer != null)
    {
      mcMediaPlayer.release();
    }
    mcMediaPlayer = MediaPlayer.create(getContext(), R.raw.win_sound);
    mcMediaPlayer.start();
  }

  public void levelFailed() {
    mNumAttempts++;
    if (!donePlayingSound) {
      mcMediaPlayer = MediaPlayer.create(getContext(), R.raw.lose_sound);
      mcMediaPlayer.start();
    }
    donePlayingSound = true;
    if (mNumAttempts < MAX_LEVEL_ATTEMPTS)
    {
      resetLevelFlags();
      mLevel = new Level(mCurrentLevel);
      donePlayingSound = false;
    }
    else
    {
      mGameEnd = true;
    }
  }

  private void resetLevelFlags ()
  {
    firstRender = true;
    firstClick = true;
    mMovingSprites.clear();
    mExplodingSprites.clear();
  }


  private static void setTextSizeForWidth(Paint paint, float desiredWidth,
                                          String text) {

    // Pick a reasonably large value for the test. Larger values produce
    // more accurate results, but may cause problems with hardware
    // acceleration. But there are workarounds for that, too; refer to
    // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
    final float testTextSize = 48f;

    // Get the bounds of the text, using our testTextSize.
    paint.setTextSize(testTextSize);
    Rect bounds = new Rect();
    paint.getTextBounds(text, 0, text.length(), bounds);

    // Calculate the desired size as a proportion of our testTextSize.
    float desiredTextSize = testTextSize * desiredWidth / bounds.width();

    // Set the paint for that size.
    paint.setTextSize(desiredTextSize);
  }
}