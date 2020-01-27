package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Defines the BoomshineView class display and
 * interaction logic for the Boomshine view.
 *
 * @author Forbes Miyasato
 * @version 1.0
 * @since 1.26.2019
 */

//@SuppressLint("AppCompatCustomView")
public class BoomshineView extends ImageView
{
  //View variables
  private final int SCALE_BY_TWO = 2;
  private final int MAX_LEVEL_ATTEMPTS = 3;
  private final int CIRCLE_LEVEL_MULTIPLIER = 5;
  final int BALL_SCALE_FACTOR = 60;
  private final BoomshineGame mcGameReference;
  ArrayList<ExplodingBoundedMovingCircle> mcMovingSprites;
  ArrayList<ExplodingBoundedMovingCircle> mcExplodingSprites;
  ArrayList<ExplodingBoundedMovingCircle> mcCoinSprites;
  ExplodingCircleFactory mcFactory;
  private double mDifficultyScale;
  private int mHeight;
  private int mWidth;
  private boolean mbFirstClick = true;
  private boolean firstRender = true;
  private Context mcContext;
  private Display mcDisplay;
  private Level mcLevel;
  private Paint mcPaint;
  private MediaPlayer mcMediaPlayer;
  private IconHandler mcIconHandler;
  private boolean donePlayingSound = false;
  private int mTotalScore;
  private int mNumAttempts;
  private int mCurrentLevel;
  private boolean mGameEnd;
  private ExplodingType meType;
  static int DEFAULT_BALL_RADIUS;
  private int mUserMultiPowerups;
  private int mUserSuperPowerups;
  private int mUserUltraPowerups;
  /**
   * Constructor that initializes the BoomshineView
   *
   * @param context reference to application-specific resources
   * @param display the display
   */
  public BoomshineView(Context context, Display display)
  {
    super(context);
    setFocusable(true); // make sure we get key events
    mcMovingSprites = new ArrayList<>();
    mcExplodingSprites = new ArrayList<>();
    mcCoinSprites = new ArrayList<>();
    mcContext = context;
    mcDisplay = display;
    mcLevel = new Level(1);
    mCurrentLevel = 1;
    mcPaint = new Paint();
    mcPaint.setAntiAlias(true);
    mTotalScore = 0;
    mNumAttempts = 0;
    mGameEnd = false;
    mcFactory = new ExplodingCircleFactory();
    mcIconHandler = new IconHandler(context);
    meType = ExplodingType.NORMAL;
    mDifficultyScale = 1;
    mcGameReference = (BoomshineGame) context;
  }

  /**
   * Draw method that is repeatedly called for animation
   *
   * @param canvas used to host the draw calls
   */
  @Override
  public void onDraw(Canvas canvas)
  {
    //Constants for drawing components on view
    final int POSITION_FIFTY = 50;
    final int POSITION_HUNDRED = 100;
    final int POSITION_TEN = 10;
    final int SCALE_FOUR = 4;
    final int SCALE_EIGHT = 8;
    final int SCALE_FIFTEEN = 15;
    final String sLevel = "Level: ";
    final String sAttempts = "Attempts: ";
    final String sTotalScore = "Total Score: ";
    final String sPoints = "Points: ";
    final String sSlash = " / ";
    if (!mGameEnd)
    {
      mUserMultiPowerups = ((BoomshineGame) mcContext).getPWMulti();
      mUserSuperPowerups = ((BoomshineGame) mcContext).getPWSuper();
      mUserUltraPowerups = ((BoomshineGame) mcContext).getPWUlti();
      ExplodingBoundedMovingCircle cCollidedMovingCircle = null;
      ExplodingBoundedMovingCircle cExplodedCircle = null;
      ExplodingBoundedMovingCircle cCollidedCoin = null;
      mHeight = getHeight();
      mWidth = getWidth();

      DEFAULT_BALL_RADIUS = mHeight / BALL_SCALE_FACTOR;
      if (firstRender)
      {
        setCircles(mcLevel.getLevelNumber());
        setCoin(mcLevel.getLevelNumber());
        firstRender = false;
      }

      for (ExplodingBoundedMovingCircle cExplodingSprite : mcExplodingSprites)
      {
        cExplodingSprite.doDraw(canvas);
        if (cExplodingSprite.handleExploding())
        {
          cExplodedCircle = cExplodingSprite;
        }
      }

      for (ExplodingBoundedMovingCircle cCoin : mcCoinSprites)
      {
        cCoin.move();
        cCoin.hitBound();
        cCoin.doDraw(canvas);
        for (ExplodingBoundedMovingCircle cExplodingSprite
                : mcExplodingSprites)
        {
          if (cCoin.collide(cExplodingSprite))
          {
            cCollidedCoin = cCoin;
            coinCollision();
          }
        }
      }

      for (ExplodingBoundedMovingCircle cMovingSprite : mcMovingSprites)
      {
        cMovingSprite.move();
        cMovingSprite.hitBound();
        cMovingSprite.doDraw(canvas);
        for (ExplodingBoundedMovingCircle cExplodingSprite
                : mcExplodingSprites)
        {
          if (cMovingSprite.collide(cExplodingSprite))
          {
            cCollidedMovingCircle = cMovingSprite;
          }
        }
      }

      if (cExplodedCircle != null)
      {
        mcExplodingSprites.remove(cExplodedCircle);
      }
      if (cCollidedMovingCircle != null)
      {
        mcExplodingSprites.add(cCollidedMovingCircle);
        mcLevel.incrememtCirclesHit(mcMediaPlayer, mcContext);
        mcMovingSprites.remove(cCollidedMovingCircle);
      }

      if (cCollidedCoin != null)
      {
        mcCoinSprites.remove(cCollidedCoin);
      }

      if (!mbFirstClick && mcExplodingSprites.isEmpty())
      {
        if (mcLevel.levelOver())
        {
          levelPassed();
        } else
        {
          levelFailed();
        }
      }

      //Paint Score on screen
      mcPaint.setColor(getResources().getColor(R.color.cWhite));
      setTextSizeForWidth(mcPaint, mWidth / SCALE_FOUR, mcLevel.getHitInfo());
      //mcPaint.setTextSize(50);
      canvas.drawText(mcLevel.getHitInfo(), POSITION_TEN,
              POSITION_FIFTY, mcPaint);
      canvas.drawText(sLevel + mCurrentLevel, mWidth - SCALE_EIGHT *
              (mWidth / SCALE_FIFTEEN), POSITION_FIFTY, mcPaint);
      canvas.drawText(sAttempts + (mNumAttempts + 1) + sSlash
                      + MAX_LEVEL_ATTEMPTS,
              mWidth - (mWidth / SCALE_FOUR), POSITION_FIFTY, mcPaint);

      canvas.drawText(sTotalScore + mTotalScore, POSITION_TEN,
              mHeight - POSITION_FIFTY, mcPaint);
      canvas.drawText(sPoints + mcGameReference.getPoints(), POSITION_TEN,
              mHeight - POSITION_HUNDRED, mcPaint);

      mcIconHandler.drawIcons(canvas, mUserMultiPowerups, mUserUltraPowerups,
              mUserSuperPowerups);

      super.onDraw(canvas);
      invalidate();
    } else
    {
      mcGameReference.onGameOver(mTotalScore, mUserMultiPowerups, mUserSuperPowerups,
              mUserUltraPowerups);
    }
  }

  /**
   * Handles the behavior when the user touches the screen. Places ball
   * or selects power ups based on touched coordinates
   *
   * @param event event input details from the touch screen event
   */
  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
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
    int xTouchPos = (int) event.getX();
    int yTouchPos = (int) event.getY();

    if (event.getAction() != MotionEvent.ACTION_DOWN)
    {
      return super.onTouchEvent(event);
    }

    //If first click, check if selected power up or placed ball
    //doesn't disable click behavior (setting mbFirstClick to false) until
    //player places ball on screen.
    if (mbFirstClick)
    {
      color = getRandomColor();

      if (mcIconHandler.checkIconBounds(xTouchPos, yTouchPos))
      {
        meType = mcIconHandler.checkPress(xTouchPos, yTouchPos);
      } else
      {
        if (meType != ExplodingType.NORMAL)
        {
          switch (meType)
          {
            case MULTI:
              mUserMultiPowerups--;
              ((BoomshineGame) mcContext).setPWMulti(mUserMultiPowerups);
              break;
            case SUPER:
              mUserSuperPowerups--;
              ((BoomshineGame) mcContext).setPWSuper(mUserSuperPowerups);
              break;
            case ULTIMATE:
              mUserUltraPowerups--;
              ((BoomshineGame) mcContext).setPWUlti(mUserUltraPowerups);
              break;
            default:
              break;
          }
        }
        mcExplodingSprites.addAll(mcFactory.create(meType, getContext(),
                getDisplay(), color, (int) event.getY() +
                        DEFAULT_BALL_RADIUS / SCALE_BY_TWO,
                (int) event.getX() + DEFAULT_BALL_RADIUS / SCALE_BY_TWO,
                0, 0, mHeight,
                0, mWidth, DEFAULT_BALL_RADIUS));
        mbFirstClick = false;
      }
    }

    return true;
  }

  /**
   * Sets the circles that will placed on the screen based on current level
   *
   * @param level The current game level
   */
  void setCircles(int level)
  {
    final int MEDIUM_DIFFICULTY = 6;
    final double MEDIUM_SCALE = 1.5;
    final int HARD_DIFFICULTY = 10;
    final double HARD_SCALE = 2;
    final int CIRCLE_SPEED = 3;
    int topBound;
    int leftBound;
    Random cRandom = new Random();
    int color;
    float defaultRadius = DEFAULT_BALL_RADIUS;
    if (level > MEDIUM_DIFFICULTY && level < HARD_DIFFICULTY)
    {
      mDifficultyScale = MEDIUM_SCALE;
    }
    if (level > HARD_DIFFICULTY)
    {
      mDifficultyScale = HARD_SCALE;
    }

    for (int i = 0; i < level * CIRCLE_LEVEL_MULTIPLIER; i++)
    {
      color = getRandomColor();
      topBound = cRandom.nextInt(mHeight - DEFAULT_BALL_RADIUS * SCALE_BY_TWO)
              + DEFAULT_BALL_RADIUS;
      leftBound = cRandom.nextInt(mWidth - DEFAULT_BALL_RADIUS * SCALE_BY_TWO)
              + DEFAULT_BALL_RADIUS;
      int speed = CIRCLE_SPEED;

      ExplodingBoundedMovingCircle cNew
              = new ExplodingBoundedMovingCircle(ExplodingType.NORMAL,
              mcContext, mcDisplay, color, topBound - DEFAULT_BALL_RADIUS,
              leftBound - DEFAULT_BALL_RADIUS, speed, 0,
              mHeight, 0,
              mWidth, (int) (defaultRadius / mDifficultyScale));
      mcMovingSprites.add(cNew);
    }
  }

  /**
   * Sets the coins that will placed on the screen based on current level
   *
   * @param level The current game level
   */
  void setCoin(int level)
  {
    final int DEFAUlT_COIN_WIDTH = 58;
    final int COIN_SPEED = 3;
    int topBound;
    int leftBound;
    Random cRandom = new Random();
    int color = getResources().getColor(R.color.coin);

    for (int i = 0; i < level; i++)
    {
      topBound = cRandom.nextInt(mHeight - DEFAUlT_COIN_WIDTH * SCALE_BY_TWO)
              + DEFAUlT_COIN_WIDTH;
      leftBound = cRandom.nextInt(mWidth - DEFAUlT_COIN_WIDTH * SCALE_BY_TWO)
              + DEFAUlT_COIN_WIDTH;
      int speed = COIN_SPEED;

      ExplodingBoundedMovingCircle cNew
              = new ExplodingBoundedMovingCircle(ExplodingType.NORMAL,
              mcContext, mcDisplay, color,
              topBound - DEFAUlT_COIN_WIDTH / SCALE_BY_TWO,
              leftBound - DEFAUlT_COIN_WIDTH / SCALE_BY_TWO, speed, 0,
              mHeight, 0,
              mWidth, 0);
      mcCoinSprites.add(cNew);
    }
  }

  /**
   * Utility function to get random colors for the balls
   */
  public int getRandomColor()
  {
    final int FIVE_COLORS = 5;
    Random cRandom = new Random();
    int randomColor = cRandom.nextInt(FIVE_COLORS);
    int color = 0;
    switch (randomColor)
    {
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

  /**
   * Handles the logic when the level is passed
   */
  public void levelPassed()
  {
    mTotalScore += mcLevel.getLevelScore();
    mNumAttempts = 0;
    mcLevel.nextLevel();
    mCurrentLevel++;
    resetLevelFlags();
    if (mcMediaPlayer != null)
    {
      mcMediaPlayer.release();
    }
    mcMediaPlayer = MediaPlayer.create(getContext(), R.raw.win_sound);
    mcMediaPlayer.start();
  }

  /**
   * Handles the logic when the level is failed
   */
  public void levelFailed()
  {
    mNumAttempts++;
    if (!donePlayingSound)
    {
      mcMediaPlayer = MediaPlayer.create(getContext(), R.raw.lose_sound);
      mcMediaPlayer.start();
    }
    donePlayingSound = true;
    if (mNumAttempts < MAX_LEVEL_ATTEMPTS)
    {
      resetLevelFlags();
      mcLevel = new Level(mCurrentLevel);
      donePlayingSound = false;
    } else
    {
      mGameEnd = true;
    }
  }

  /**
   * Resets and cleans up data when the level is over
   */
  private void resetLevelFlags()
  {
    firstRender = true;
    mbFirstClick = true;
    mcMovingSprites.clear();
    mcExplodingSprites.clear();
    mcCoinSprites.clear();
    meType = ExplodingType.NORMAL;
    mcIconHandler.resetIcons();
  }

  /**
   * Makes the text size responsive based on the canvas width
   *
   * @param paint the paint object we use to paint the text
   * @param desiredWidth The desired width for the text
   * @param text The text we want to render on the view
   */
  private static void setTextSizeForWidth(Paint paint, float desiredWidth,
                                          String text)
  {

    // Pick a reasonably large value for the test. Larger values produce
    // more accurate results, but may cause problems with hardware
    // acceleration. But there are workarounds for that, too; refer to
    // http://stackoverflow.com/questions/6253528
    // /font-size-too-large-to-fit-in-cache
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

  /**
   * Handles the logic when the coin collides with the exploding circles
   */
  public void coinCollision()
  {
    mcGameReference.setPoints(mcGameReference.getPoints() + 1);
    mcMediaPlayer = MediaPlayer.create(getContext(), R.raw.coin_collision);
    mcMediaPlayer.start();
  }
}