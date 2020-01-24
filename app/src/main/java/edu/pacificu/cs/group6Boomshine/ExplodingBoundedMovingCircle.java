package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Display;

public class ExplodingBoundedMovingCircle extends BoundedMovingSprite {

  private int mExpandingSpeed;
  private float mScale;
  private int mExplosionProgress;
  private boolean bExploding = true;
  protected final int mStartPositionTop;
  private final int mStartPositionLeft;
  ExplodingBoundedMovingCircle(Context context, Display display, int drawable,
                               int topCoord, int leftCoord, int speed, int topBound, int bottomBound,
                               int leftBound, int rightBound, int expandingSpeed, float radius)
  {
    super(context, display, drawable, topCoord, leftCoord, speed, topBound, bottomBound, leftBound, rightBound);
    mExplosionProgress = mRadius;
    mStartPositionTop = topCoord;
    mStartPositionLeft = leftCoord;
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

    if (mExplosionProgress >= 150) {
      bExploding = false;
    }
    mExplosionProgress += bExploding ? 2 : -4; //TODO ASK

    mRadius = mExplosionProgress;

//    setLeftCoordinate(mStartPositionLeft - ((width - getSpriteWidth()) / 2));
//    setTopCoordinate(mStartPositionTop - ((width - getSpriteWidth()) / 2));

    Log.d("EXPLOSION", String.valueOf(mRadius));
    positionShift = bExploding ? -1 : 2;
    setLeftCoordinate(getLeftCoordinate() + positionShift);
    setTopCoordinate(getTopCoordinate() + positionShift);

    return mExplosionProgress <= 0;
  }
}
