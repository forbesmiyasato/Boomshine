package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.Display;

public class ExplodingBoundedMovingCircle extends BoundedMovingSprite {

  private int mExpandingSpeed;
  private float mScale;
  private float mRadius;

  ExplodingBoundedMovingCircle(Context context, Display display, int drawable,
                               int topCoord, int leftCoord, int speed, int topBound, int bottomBound,
                               int leftBound, int rightBound, int expandingSpeed, float radius)
  {
    super(context, display, drawable, topCoord, leftCoord, speed, topBound, bottomBound, leftBound, rightBound);
    mRadius = getSpriteWidth() / 2;
  }

//  @Override
//  public void doDraw(Canvas canvas) {
//
//  }

  public void expandCircle() {
    Bitmap bitmap = getBitmap();
    long currentTimeMillis = System.currentTimeMillis();
    long fiveSeconds = 5000;
    int incrementWidth = 30;

//    while (System.currentTimeMillis() - currentTimeMillis < fiveSeconds) {
//      float aspectRatio = bitmap.getWidth() /
//              (float) bitmap.getHeight();
//      int width = getDisplayWidth() + incrementWidth;
//      int height = Math.round(width / aspectRatio);
//
//      bitmap = Bitmap.createScaledBitmap(
//              bitmap, width, height, false);
//      setmBitmapImage(bitmap);
////      setLeftCoordinate(getLeftCoordinate() - getSpriteWidth());
//    }
     float aspectRatio = bitmap.getWidth() /
              (float) bitmap.getHeight();
      int width = getDisplayWidth() + incrementWidth;
      int height = Math.round(width / aspectRatio);

      bitmap = Bitmap.createScaledBitmap(
              bitmap, width, height, false);
      setmBitmapImage(bitmap);
  }

  public void retractCircle() {

  }

  public float getRadius () {
    return mRadius;
  }

  public void collide (ExplodingBoundedMovingCircle cOtherCircle) {
    final int SQUARE = 2;
    //(x2-x1)^2 + (y1-y2)^2 <= (r1+r2)^2 to check if collides
    float dRadius = cOtherCircle.mRadius + this.getRadius();
    float dX = getLeftCoordinate() - cOtherCircle.getLeftCoordinate();
    float dY = getTopCoordinate() - cOtherCircle.getTopCoordinate();

    if (Math.pow(dX, SQUARE) + Math.pow(dY, SQUARE) <= Math.pow(dRadius, SQUARE))
    {
      Log.d("collide", "Circles collided");
      mSpeed = 0;
      expandCircle();
      cOtherCircle.expandCircle();
    }
  }

  boolean isDoneRetracting () {
    return false;
  }
}
