package edu.pacificu.cs.boomshine;

import android.content.Context;
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
  }
}
