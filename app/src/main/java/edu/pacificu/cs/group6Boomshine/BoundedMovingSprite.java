package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.view.Display;

public class BoundedMovingSprite extends MovingSprite {
  private int mTopBound;
  private int mBottomBound;
  private int mLeftBound;
  private int mRightBound;
  private int mDrawable;
  BoundedMovingSprite(Context context, Display display, int drawable,
                      int topCoord, int leftCoord, int speed, int topBound, int bottomBound, int leftBound, int rightBound)
  {
    super(context, display, drawable, topCoord, leftCoord, speed);
    mTopBound = topBound;
    mBottomBound = bottomBound;
    mLeftBound = leftBound;
    mRightBound = rightBound;
    mDrawable = drawable;
  }

  public void hitBound () {
    if (getTopCoordinate() <= mTopBound) {
      mDy = mDy * -1;
    }
    if (getLeftCoordinate() <= mLeftBound)
    {
      mDx = mDx * -1;
    }
    if (DEFAULT_BALL_RADIUS * 2 + getTopCoordinate() >= mBottomBound)
    {
      mDy = mDy * -1;
    }
    if (DEFAULT_BALL_RADIUS * 2 + getLeftCoordinate() >= mRightBound)
    {
      mDx = mDx * -1;
    }
  }
}