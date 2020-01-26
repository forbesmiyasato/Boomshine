package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.Random;

import static edu.pacificu.cs.group6Boomshine.FixedSprite.DEFAULT_BALL_RADIUS;

public class PowerUpView extends ImageView {

  final int PIC_WIDTH = 150;
  final int PIC_HEIGHT = 150;
  final int PIC_Y_START = 300;
  final int PIC_BUTTON_SPACING = 100;
  final int BUY_BUTTON_HEIGHT = 50;
  final int BUY_BUTTON_WIDTH = 150;
  final int BACk_BUTTON_HEIGHT = 150;
  final int BACK_BUTTON_WIDTH = 300;

  private Paint mPaint;
  private final BoomshineGame mcGame;
  public PowerUpView(Context context, Display display) {
    super(context);
    setFocusable(true); // make sure we get key events
    mPaint = new Paint();
    this.mcGame = (BoomshineGame) context;

  }

  /**
   * Draw method that is repeatedly called for animation
   *
   * @param canvas used to host the draw calls
   * @since 1.0
   */

  @Override
  public void onDraw(Canvas canvas) {
    int picSpacing = (getWidth() - PIC_WIDTH * 3) / 4;
    int backMargin = (getWidth() - BACK_BUTTON_WIDTH) / 2;
    Drawable buyButton = getResources().getDrawable(R.drawable.buy, null);

    canvas.drawText("Points", getWidth() - 400, 100, mPaint);
    canvas.drawText(String.valueOf(mcGame.getPoints()), getWidth() - 200, 100, mPaint);

    Drawable multiBallPic = getResources().getDrawable(R.drawable.multiball, null);
    multiBallPic.setBounds(picSpacing, (int) PIC_Y_START,
            (int)picSpacing + PIC_WIDTH,
            (int)PIC_Y_START + PIC_HEIGHT);
    multiBallPic.draw(canvas);

    buyButton.setBounds(picSpacing, (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
            (int)picSpacing + PIC_WIDTH,
            (int)PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
    buyButton.draw(canvas);

    Drawable superBallPic = getResources().getDrawable(R.drawable.superball, null);
    superBallPic.setBounds(picSpacing * 2 + PIC_WIDTH, (int) PIC_Y_START,
            (int)PIC_WIDTH * 2  + picSpacing * 2,
            (int)PIC_Y_START + PIC_HEIGHT);
    superBallPic.draw(canvas);

    buyButton.setBounds(picSpacing * 2 + PIC_WIDTH, (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
            (int)PIC_WIDTH * 2  + picSpacing * 2,
            (int)PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
    buyButton.draw(canvas);

    Drawable ultiBallPic = getResources().getDrawable(R.drawable.ulti, null);
    ultiBallPic.setBounds(PIC_WIDTH * 2 + picSpacing * 3, (int) PIC_Y_START,
            (int)PIC_WIDTH * 3 + picSpacing * 3,
            (int)PIC_Y_START + PIC_HEIGHT);
    ultiBallPic.draw(canvas);

    buyButton.setBounds(PIC_WIDTH * 2 + picSpacing * 3, (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
            (int)PIC_WIDTH * 3 + picSpacing * 3,
            (int)PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
    buyButton.draw(canvas);


    Drawable back = getResources().getDrawable(R.drawable.backtomenu, null);
    back.setBounds(backMargin, (int) getHeight() - 300,
            (int)backMargin + BACK_BUTTON_WIDTH,
            (int) getHeight() - 300 + BACk_BUTTON_HEIGHT);
    back.draw(canvas);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {

  return true;
  }

}

