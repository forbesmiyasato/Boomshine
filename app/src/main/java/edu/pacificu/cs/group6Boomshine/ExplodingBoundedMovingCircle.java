package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.util.Log;
import android.view.Display;

public class ExplodingBoundedMovingCircle extends BoundedMovingSprite {

  private int mExplosionProgress;
  private boolean bExploding = true;
  protected final int mStartPositionTop;
  private final int mStartPositionLeft;
  private int mStartRadius;
  private ExplodingType meType;
  private IExpansionStrategy mStrategy;

  ExplodingBoundedMovingCircle(ExplodingType eType, Context context, Display display, int drawable,
                               int topCoord, int leftCoord, int speed, int topBound, int bottomBound,
                               int leftBound, int rightBound, int radius)
  {
    super(context, display, drawable, topCoord, leftCoord, speed, topBound, bottomBound, leftBound, rightBound);
    mRadius = radius;
    mStartRadius = radius;
    mExplosionProgress = mRadius;
    mStartPositionTop = topCoord;
    mStartPositionLeft = leftCoord;
    meType = eType;

    switch (eType)
    {
      case NORMAL:
        mStrategy = new handleExplodingNormal();
        break;
      case SUPER:
        mStrategy = new handleExplodingSuper();
        break;
      case MULTI:
        mStrategy = new handleExplodingMulti();
        break;
      case ULTIMATE:
        mStrategy = new handleExplodingUltra();
        break;
    }
  }

  public float getRadius () {
    return mRadius;
  }

  public boolean collide (ExplodingBoundedMovingCircle cOtherCircle) {
    final int SQUARE = 2;
    boolean bCollided = false;
    //(x2-x1)^2 + (y1-y2)^2 <= (r1+r2)^2 to check if collides
    float dRadius = cOtherCircle.getRadius() + this.getRadius();
    float dX = (getLeftCoordinate()) - (cOtherCircle.getLeftCoordinate());
    float dY = (getTopCoordinate()) - (cOtherCircle.getTopCoordinate());

    if (Math.pow(dX, SQUARE) + Math.pow(dY, SQUARE) <= Math.pow(dRadius, SQUARE))
    {
      Log.d("collide", "Circles collided");
      mSpeed = 0;
      bCollided = true;
      Log.d("collide", "this.x = " + dX);
      Log.d("collide", "this.y = " + dY);
      Log.d("collide", "other.x = " + mRadius);
      Log.d("collide", "other.y = " + cOtherCircle.mRadius);
    }


    return bCollided;
  }

  public boolean handleExploding () {
    int positionShift;

    int explosionRate = this.mStrategy.handleExploding();

    if (mExplosionProgress >= (6 * mStartRadius)) {
      bExploding = false;
    }

    mExplosionProgress += bExploding ? explosionRate : explosionRate * -2;

    mRadius = mExplosionProgress;

    positionShift = bExploding ? (explosionRate / -2) : explosionRate;

    Log.d("Handle", "Explosion: " + explosionRate / -2);
    setLeftCoordinate(getLeftCoordinate() + positionShift);
    setTopCoordinate(getTopCoordinate() + positionShift);

    return mExplosionProgress <= 0;
  }
}
