package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Display;

public class ExplodingBoundedMovingCircle extends BoundedMovingSprite {

  private int mExpandingSpeed;
  private float mScale;
  private float mRadius;
  private int mExplosionProgress;
  private boolean bExploding = true;
  protected final int mStartPositionTop;
  private final int mStartPositionLeft;
  ExplodingBoundedMovingCircle(Context context, Display display, int drawable,
                               int topCoord, int leftCoord, int speed, int topBound, int bottomBound,
                               int leftBound, int rightBound, int expandingSpeed, float radius)
  {
    super(context, display, drawable, topCoord, leftCoord, speed, topBound, bottomBound, leftBound, rightBound);
    mRadius = getSpriteWidth() / 2;
    mExplosionProgress = getSpriteWidth();
    mStartPositionTop = topCoord;
    mStartPositionLeft = leftCoord;
  }

//  @Override
//  public void doDraw(Canvas canvas) {
//
//  }

//  public void expandCircle() {
//    Bitmap bitmap = getBitmap();
//    long currentTimeMillis = System.currentTimeMillis();
//    long fiveSeconds = 5000;
//    int incrementWidth = 30;
//
////    while (System.currentTimeMillis() - currentTimeMillis < fiveSeconds) {
////      float aspectRatio = bitmap.getWidth() /
////              (float) bitmap.getHeight();
////      int width = getDisplayWidth() + incrementWidth;
////      int height = Math.round(width / aspectRatio);
////
////      bitmap = Bitmap.createScaledBitmap(
////              bitmap, width, height, false);
////      setmBitmapImage(bitmap);
//////      setLeftCoordinate(getLeftCoordinate() - getSpriteWidth());
////    }
//
//  }

  public void retractCircle() {

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

  private boolean isDoneRetracting () {
    return false;
  }

  public boolean handleExploding () {
    int positionShift;

    if (mExplosionProgress >= 300) {
      bExploding = false;
    }
    mExplosionProgress += bExploding ? 2 : -2; //TODO ASK
    Bitmap bitmap = getBitmap();
    float aspectRatio = bitmap.getWidth() /
              (float) bitmap.getHeight();
      int width = mExplosionProgress;
      int height = Math.round(width / aspectRatio);

      bitmap = Bitmap.createScaledBitmap(
              bitmap, width, height, true);
      setmBitmapImage(bitmap);

    mRadius = width / 2;

//    setLeftCoordinate(mStartPositionLeft - ((width - getSpriteWidth()) / 2));
//    setTopCoordinate(mStartPositionTop - ((width - getSpriteWidth()) / 2));

    positionShift = bExploding ? -1 : 1;
    setLeftCoordinate(getLeftCoordinate() + positionShift);
    setTopCoordinate(getTopCoordinate() + positionShift);

    return mExplosionProgress <= 2;
  }
}
