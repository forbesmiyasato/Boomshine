package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.view.Display;

public class ExplodingBoundedMovingCircle extends BoundedMovingSprite {

  private int mExplosionProgress;
  private boolean bExploding = true;
  protected final int mStartPositionTop;
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
    meType = eType;

    switch (meType)
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
      mSpeed = 0;
      bCollided = true;
    }


    return bCollided;
  }

  public boolean handleExploding () {
    int explosionRate = this.mStrategy.handleExploding();
    int explosionRadiusMult = this.mStrategy.getRadiusMultiplier();
    if (mExplosionProgress >= (explosionRadiusMult * mStartRadius)) {
      bExploding = false;
    }

    mExplosionProgress += bExploding ? explosionRate : explosionRate * -2;

    mRadius = mExplosionProgress;

    setLeftCoordinate(getLeftCoordinate());
    setTopCoordinate(getTopCoordinate());

    return mExplosionProgress <= 0;
  }
}
