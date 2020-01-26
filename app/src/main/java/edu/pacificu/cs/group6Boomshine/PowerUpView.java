package edu.pacificu.cs.group6Boomshine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

public class PowerUpView extends ImageView {

  private int PIC_WIDTH;
  private int PIC_HEIGHT;
  private int PIC_Y_START;
  private int PIC_BUTTON_SPACING;
  private int BUY_BUTTON_HEIGHT;
  private int BUY_BUTTON_WIDTH;
  private int BACk_BUTTON_HEIGHT;
  private int BACK_BUTTON_WIDTH;
  private final double HEIGHT_SCALE_FACTOR = 10.22; //100px for 1022px height
  private final double WIDTH_SCALE_FACTOR = 7.68; //100px for 768px width
  private boolean bCanBuyMulti;
  private boolean bCanBuySuper;
  private boolean bCanBuyUlti;
  private boolean bFirstRender = true;
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
    int picSpacing;
    int backMargin;
    int point_x;
    int point_width;
    Drawable buyButton = getResources().getDrawable(R.drawable.buy, null);

    mHeight = getHeight();
    mWidth = getWidth();

    BACk_BUTTON_HEIGHT = (int) (mHeight / HEIGHT_SCALE_FACTOR  * 1.5);
    BACK_BUTTON_WIDTH = BACk_BUTTON_HEIGHT * 2;
    BUY_BUTTON_HEIGHT = BACk_BUTTON_HEIGHT / 3;
    PIC_BUTTON_SPACING = (int) (mWidth / WIDTH_SCALE_FACTOR);
    PIC_WIDTH = (int) (PIC_BUTTON_SPACING * 1.5);
    PIC_HEIGHT = PIC_WIDTH;
    PIC_Y_START = (int) (mHeight / HEIGHT_SCALE_FACTOR) * 3;

    point_x = (int)(mWidth / WIDTH_SCALE_FACTOR * 4);
    point_width = (int) (mWidth / WIDTH_SCALE_FACTOR * 2);
    picSpacing = (getWidth() - PIC_WIDTH * 3) / 4;
    backMargin = (getWidth() - BACK_BUTTON_WIDTH) / 2;

    Log.d("POWERUP", String.valueOf(BACk_BUTTON_HEIGHT));
    Log.d("POWERUP", String.valueOf(BACK_BUTTON_WIDTH));
    Log.d("POWERUP", String.valueOf(BUY_BUTTON_HEIGHT));
    Log.d("POWERUP", String.valueOf(PIC_BUTTON_SPACING));
    Log.d("POWERUP", String.valueOf(PIC_WIDTH));
    Log.d("POWERUP", String.valueOf(PIC_HEIGHT));
    Log.d("POWERUP", String.valueOf(PIC_Y_START));

    //Check if can buy
    bCanBuyMulti = mcGame.canBuyMulti();
    bCanBuySuper = mcGame.canBuySuper();
    bCanBuyUlti = mcGame.canBuyUlti();

    mPaint.setTextSize(mHeight * 0.05f);
    mPaint.setColor(getResources().getColor(R.color.cBlack));
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setAlpha(255);
    canvas.drawText("Points", getWidth() - point_x, 100, mPaint);
    canvas.drawText(String.valueOf(mcGame.getPoints()), getWidth() - point_width, 100, mPaint);

    //Draw multi ball pic
    Drawable multiBallPic = getResources().getDrawable(R.drawable.multiball, null);
    multiBallPic.setBounds(picSpacing, PIC_Y_START,
            picSpacing + PIC_WIDTH,
            PIC_Y_START + PIC_HEIGHT);
    multiBallPic.draw(canvas);
    //Draw Buy button
    buyButton.setBounds(picSpacing,
            PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
            picSpacing + PIC_WIDTH,
            PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
    buyButton.draw(canvas);
    //If can't buy draw shade over it
    if(!bCanBuyMulti) {
      @SuppressLint("DrawAllocation") Rect cantBuy = new Rect(picSpacing,
              PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
              picSpacing + PIC_WIDTH,
              PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
      mPaint.setColor(getResources().getColor(R.color.cGray));
      mPaint.setStyle(Paint.Style.FILL);
      mPaint.setAlpha(200);
      canvas.drawRect(cantBuy, mPaint);
    }
    //Draw super ball pic
    Drawable superBallPic = getResources().getDrawable(R.drawable.superball, null);
    superBallPic.setBounds(picSpacing * 2 + PIC_WIDTH, PIC_Y_START,
            PIC_WIDTH * 2 + picSpacing * 2,
            PIC_Y_START + PIC_HEIGHT);
    superBallPic.draw(canvas);
    //Draw Buy button
    buyButton.setBounds(picSpacing * 2 + PIC_WIDTH, PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
            PIC_WIDTH * 2 + picSpacing * 2,
            PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
    buyButton.draw(canvas);
    //If can't buy draw shade over it
    if(!bCanBuySuper) {
      @SuppressLint("DrawAllocation") Rect cantBuy = new Rect(picSpacing * 2 + PIC_WIDTH,
              PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
              PIC_WIDTH * 2 + picSpacing * 2,
              PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
      mPaint.setColor(getResources().getColor(R.color.cGray));
      mPaint.setStyle(Paint.Style.FILL);
      mPaint.setAlpha(200);
      canvas.drawRect(cantBuy, mPaint);
    }
    //Draw Ulti ball pic
    Drawable ultiBallPic = getResources().getDrawable(R.drawable.ulti, null);
    ultiBallPic.setBounds(PIC_WIDTH * 2 + picSpacing * 3, PIC_Y_START,
            PIC_WIDTH * 3 + picSpacing * 3,
            PIC_Y_START + PIC_HEIGHT);
    ultiBallPic.draw(canvas);
    //Draw Buy button
    buyButton.setBounds(PIC_WIDTH * 2 + picSpacing * 3, PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
            PIC_WIDTH * 3 + picSpacing * 3,
            PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
    buyButton.draw(canvas);
    //If can't buy draw shade over it
    if(!bCanBuyUlti) {
      @SuppressLint("DrawAllocation") Rect cantBuy = new Rect(PIC_WIDTH * 2 + picSpacing * 3,
              PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING,
              PIC_WIDTH * 3 + picSpacing * 3,
              PIC_Y_START + PIC_HEIGHT + PIC_BUTTON_SPACING + BUY_BUTTON_HEIGHT);
      mPaint.setColor(getResources().getColor(R.color.cGray));
      mPaint.setStyle(Paint.Style.FILL);
      mPaint.setAlpha(200);
      canvas.drawRect(cantBuy, mPaint);
    }
    //Draw back button
    Drawable back = getResources().getDrawable(R.drawable.backtomenu, null);
    back.setBounds(backMargin, getHeight() - 300,
            backMargin + BACK_BUTTON_WIDTH,
            getHeight() - 300 + BACk_BUTTON_HEIGHT);
    back.draw(canvas);

    if(bFirstRender)
    {
      invalidate();
      bFirstRender = false;
    }
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

