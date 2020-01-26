package edu.pacificu.cs.group6Boomshine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.Random;

public class PowerUpView extends ImageView {

  final int PIC_WIDTH = 150;
  final int PIC_HEIGHT = 150;
  final int PIC_Y_START = 300;
  final int PIC_BUTTON_SPACING = 100;
  final int BUY_BUTTON_HEIGHT = 50;
  final int BUY_BUTTON_WIDTH = 150;
  final int BACk_BUTTON_HEIGHT = 150;
  final int BACK_BUTTON_WIDTH = 300;

  private boolean bCanBuyMulti;
  private boolean bCanBuySuper;
  private boolean bCanBuyUlti;

  private Paint mPaint;
  private final BoomshineGame mcGame;
  private int mHeight;
  private int mWidth;

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

    //Check if can buy
    bCanBuyMulti = mcGame.canBuyMulti();
    bCanBuySuper = mcGame.canBuySuper();
    bCanBuyUlti = mcGame.canBuyUlti();

    mHeight = getHeight();
    mWidth = getWidth();

    mPaint.setTextSize(mHeight * 0.05f);
    mPaint.setColor(getResources().getColor(R.color.cBlack));
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setAlpha(255);
    canvas.drawText("Points", getWidth() - 400, 100, mPaint);
    canvas.drawText(String.valueOf(mcGame.getPoints()), getWidth() - 200, 100, mPaint);

    //Draw multi ball pic
    Drawable multiBallPic = getResources().getDrawable(R.drawable.multiball, null);
    multiBallPic.setBounds(picSpacing, (int) PIC_Y_START,
            (int) picSpacing + PIC_WIDTH,
            (int) PIC_Y_START + PIC_HEIGHT);
    multiBallPic.draw(canvas);
    //Draw Buy button
    buyButton.setBounds(picSpacing, (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
            (int) picSpacing + PIC_WIDTH,
            (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
    buyButton.draw(canvas);
    //If can't buy draw shade over it
    if(!bCanBuyMulti) {
      @SuppressLint("DrawAllocation") Rect cantBuy = new Rect(picSpacing, (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
              (int) picSpacing + PIC_WIDTH,
              (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
      mPaint.setColor(getResources().getColor(R.color.cGray));
      mPaint.setStyle(Paint.Style.FILL);
      mPaint.setAlpha(200);
      canvas.drawRect(cantBuy, mPaint);
    }
    //Draw super ball pic
    Drawable superBallPic = getResources().getDrawable(R.drawable.superball, null);
    superBallPic.setBounds(picSpacing * 2 + PIC_WIDTH, (int) PIC_Y_START,
            (int) PIC_WIDTH * 2 + picSpacing * 2,
            (int) PIC_Y_START + PIC_HEIGHT);
    superBallPic.draw(canvas);
    //Draw Buy button
    buyButton.setBounds(picSpacing * 2 + PIC_WIDTH, (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
            (int) PIC_WIDTH * 2 + picSpacing * 2,
            (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
    buyButton.draw(canvas);
    //If can't buy draw shade over it
    if(!bCanBuySuper) {
      @SuppressLint("DrawAllocation") Rect cantBuy = new Rect(picSpacing * 2 + PIC_WIDTH, (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
              (int) PIC_WIDTH * 2 + picSpacing * 2,
              (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
      mPaint.setColor(getResources().getColor(R.color.cGray));
      mPaint.setStyle(Paint.Style.FILL);
      mPaint.setAlpha(200);
      canvas.drawRect(cantBuy, mPaint);
    }
    //Draw Ulti ball pic
    Drawable ultiBallPic = getResources().getDrawable(R.drawable.ulti, null);
    ultiBallPic.setBounds(PIC_WIDTH * 2 + picSpacing * 3, (int) PIC_Y_START,
            (int) PIC_WIDTH * 3 + picSpacing * 3,
            (int) PIC_Y_START + PIC_HEIGHT);
    ultiBallPic.draw(canvas);
    //Draw Buy button
    buyButton.setBounds(PIC_WIDTH * 2 + picSpacing * 3, (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
            (int) PIC_WIDTH * 3 + picSpacing * 3,
            (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
    buyButton.draw(canvas);
    //If can't buy draw shade over it
    if(!bCanBuyUlti) {
      @SuppressLint("DrawAllocation") Rect cantBuy = new Rect(PIC_WIDTH * 2 + picSpacing * 3, (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
              (int) PIC_WIDTH * 3 + picSpacing * 3,
              (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
      mPaint.setColor(getResources().getColor(R.color.cGray));
      mPaint.setStyle(Paint.Style.FILL);
      mPaint.setAlpha(200);
      canvas.drawRect(cantBuy, mPaint);
    }
    //Draw back button
    Drawable back = getResources().getDrawable(R.drawable.backtomenu, null);
    back.setBounds(backMargin, (int) getHeight() - 300,
            (int) backMargin + BACK_BUTTON_WIDTH,
            (int) getHeight() - 300 + BACk_BUTTON_HEIGHT);
    back.draw(canvas);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    int xPosition = (int) event.getX();
    int yPosition = (int) event.getY();
    int picSpacing = (getWidth() - PIC_WIDTH * 3) / 4;
    int backMargin = (getWidth() - BACK_BUTTON_WIDTH) / 2;


    if (event.getAction() != MotionEvent.ACTION_DOWN) {
      return super.onTouchEvent(event);
    }

    //Check if back button is clicked
    if ((yPosition >= mHeight - 300 && yPosition <= mHeight - 300 + BACk_BUTTON_HEIGHT)
    && (xPosition >= backMargin && xPosition <= backMargin + BACK_BUTTON_WIDTH))
    {
      mcGame.onBackClicked();
    }

    //Check if buy PWMulti is clicked
    if ((xPosition >= picSpacing && xPosition <= picSpacing + PIC_WIDTH)
            && (yPosition >= (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING
            && yPosition <= PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT))
    {
      Log.d("Touch", "BUY MULTI");
      if(bCanBuyMulti) {
        mcGame.onMultiBuy();
      }
      this.invalidate();
    }
    //Check if buy PWSuper is clicked
    if ((xPosition >= picSpacing * 2 + PIC_WIDTH && xPosition <= PIC_WIDTH * 2 + picSpacing * 2)
            && (yPosition >= (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING
            && yPosition <= PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT))
    {
      Log.d("Touch", "BUY SUPER");
      if(bCanBuySuper) {
        mcGame.onSuperBuy();
      }
      this.invalidate();
    }
    //Check if buy PWSuper is clicked
    if ((xPosition >= PIC_WIDTH * 2 + picSpacing * 3 && xPosition <= PIC_WIDTH * 3 + picSpacing * 3)
            && (yPosition >= (int) PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING
            && yPosition <= PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT))
    {
      Log.d("Touch", "BUY SUPER");
      if (bCanBuyUlti) {
        mcGame.onUltiBuy();
      }
      this.invalidate();
    }

    return true;
  }

}

