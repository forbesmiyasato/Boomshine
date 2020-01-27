package edu.pacificu.cs.group6Boomshine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Defines the PowerUpView class that contains display and
 * interaction logic for the power up view.
 *
 * @author Forbes Miyasato
 * @version 1.0
 * @since 1.26.2019
 */

public class PowerUpView extends ImageView
{
  //Variables used to position the components in the view
  private final int BACK_BUTTON_Y_POSITION = 300;
  private final double HEIGHT_SCALE_FACTOR = 10.22; //100px for 1022px height
  private final double WIDTH_SCALE_FACTOR = 7.68; //100px for 768px width
  private final int SHIFT_TWO = 2;
  private final int SHIFT_THREE = 3;
  private final int POINT_Y_POSITION = 100;
  private int mPicSpacing;
  private int mBackMargin;
  private int picWidth;
  private int picHeight;
  private int picYStart;
  private int picButtonSpacing;
  private int buyButtonHeight;
  private int backButtonHeight;
  private int backButtonWidth;
  private boolean mbCanBuyMulti;
  private boolean mbCanBuySuper;
  private boolean mbCanBuyUlti;
  private boolean mbFirstRender = true;
  private Paint mcPaint;
  private final BoomshineGame mcGameReference;
  private int mHeight;
  private int mWidth;

  public PowerUpView(Context context, Display display)
  {
    super(context);
    setFocusable(true); // make sure we get key events
    mcPaint = new Paint();
    this.mcGameReference = (BoomshineGame) context;

  }

  /**
   * Draw method that draws the view on screen
   *
   * @param canvas used to host the draw calls
   */

  @Override
  public void onDraw(Canvas canvas)
  {
    final int BUY_OPACITY = 255;
    final int CANT_BUY_OPACITY = 200;
    final float TEXT_SCALE_VALUE = 0.05f;
    final double SCALE_BY_ONE_POINT_FIVE = 1.5;
    final int SCALE_BY_TWO = 2;
    final int SCALE_BY_THREE = 3;
    final int SCALE_BY_FOUR = 4;
    final String sPOINTS = "POINTS";
    int point_x;
    int point_width;
    int text_y;
    Drawable buyButton = getResources().getDrawable(R.drawable.buy, null);

    mHeight = getHeight();
    mWidth = getWidth();

    //To make the draw responsive for nexus 4 and pixel 3
    backButtonHeight = (int) (mHeight / HEIGHT_SCALE_FACTOR
            * SCALE_BY_ONE_POINT_FIVE);
    backButtonWidth = backButtonHeight * SCALE_BY_TWO;
    picButtonSpacing = (int) (mWidth / WIDTH_SCALE_FACTOR);
    picWidth = (int) (picButtonSpacing
            * SCALE_BY_ONE_POINT_FIVE);
    buyButtonHeight = backButtonHeight / SCALE_BY_THREE;
    picHeight = picWidth;
    picYStart = (int) (mHeight / HEIGHT_SCALE_FACTOR) * SCALE_BY_THREE;
    text_y = picYStart + picHeight + (picButtonSpacing / SCALE_BY_TWO);
    point_x = (int) (mWidth / WIDTH_SCALE_FACTOR * SCALE_BY_FOUR);
    point_width = (int) (mWidth / WIDTH_SCALE_FACTOR * SCALE_BY_ONE_POINT_FIVE);
    mPicSpacing = (getWidth() - picWidth * SCALE_BY_THREE) / SCALE_BY_FOUR;
    mBackMargin = (getWidth() - backButtonWidth) / SCALE_BY_TWO;

    //Check if can buy
    mbCanBuyMulti = mcGameReference.canBuyMulti();
    mbCanBuySuper = mcGameReference.canBuySuper();
    mbCanBuyUlti = mcGameReference.canBuyUlti();

    mcPaint.setTextSize(mHeight * TEXT_SCALE_VALUE);
    mcPaint.setColor(getResources().getColor(R.color.cBlack));
    mcPaint.setStyle(Paint.Style.FILL);
    mcPaint.setAlpha(BUY_OPACITY);
    canvas.drawText(sPOINTS, getWidth() - point_x,
            POINT_Y_POSITION, mcPaint);
    canvas.drawText(String.valueOf(mcGameReference.getPoints()),
            getWidth() - point_width,
            POINT_Y_POSITION, mcPaint);

    //Draw multi ball pic
    Drawable multiBallPic = getResources().getDrawable(R.drawable.multiball,
            null);
    multiBallPic.setBounds(mPicSpacing, picYStart,
            mPicSpacing + picWidth,
            picYStart + picHeight);
    multiBallPic.draw(canvas);
    //Draw multi ball amount
    canvas.drawText(String.valueOf(mcGameReference.getPWMulti()), mPicSpacing,
            text_y, mcPaint);
    //Draw Buy button
    buyButton.setBounds(mPicSpacing,
            picYStart + picHeight + picButtonSpacing,
            mPicSpacing + picWidth,
            picYStart + picHeight + picButtonSpacing + buyButtonHeight);
    buyButton.draw(canvas);
    //If can't buy draw shade over it
    if (!mbCanBuyMulti)
    {
      @SuppressLint("DrawAllocation")
      Rect cMultiCantBuy = new Rect(mPicSpacing,
              picYStart + picHeight + picButtonSpacing,
              mPicSpacing + picWidth,
              picYStart + picHeight + picButtonSpacing + buyButtonHeight);
      mcPaint.setColor(getResources().getColor(R.color.cGray));
      mcPaint.setStyle(Paint.Style.FILL);
      mcPaint.setAlpha(CANT_BUY_OPACITY);
      canvas.drawRect(cMultiCantBuy, mcPaint);
    }
    //Draw super ball pic
    Drawable superBallPic = getResources().getDrawable(R.drawable.superball,
            null);
    superBallPic.setBounds(mPicSpacing * SHIFT_TWO + picWidth, picYStart,
            picWidth * SHIFT_TWO + mPicSpacing * SHIFT_TWO,
            picYStart + picHeight);
    superBallPic.draw(canvas);
    //Draw multi ball amount
    mcPaint.setColor(getResources().getColor(R.color.cBlack));
    canvas.drawText(String.valueOf(mcGameReference.getPWSuper()),
            mPicSpacing * SHIFT_TWO + picWidth,
            text_y, mcPaint);
    //Draw Buy button
    buyButton.setBounds(mPicSpacing * SHIFT_TWO + picWidth,
            picYStart + picHeight + picButtonSpacing,
            picWidth * SHIFT_TWO + mPicSpacing * SHIFT_TWO,
            picYStart + picHeight + picButtonSpacing + buyButtonHeight);
    buyButton.draw(canvas);
    //If can't buy draw shade over it
    if (!mbCanBuySuper)
    {
      @SuppressLint("DrawAllocation")
      Rect cSuperBuy = new Rect(mPicSpacing * SHIFT_TWO + picWidth,
              picYStart + picHeight + picButtonSpacing,
              picWidth * SHIFT_TWO + mPicSpacing * 2,
              picYStart + picHeight + picButtonSpacing + buyButtonHeight);
      mcPaint.setColor(getResources().getColor(R.color.cGray));
      mcPaint.setStyle(Paint.Style.FILL);
      mcPaint.setAlpha(CANT_BUY_OPACITY);
      canvas.drawRect(cSuperBuy, mcPaint);
    }
    //Draw Ulti ball pic
    Drawable ultiBallPic = getResources().getDrawable(R.drawable.ulti, null);
    ultiBallPic.setBounds(picWidth * SHIFT_TWO
                    + mPicSpacing * SHIFT_THREE, picYStart,
            picWidth * SHIFT_THREE + mPicSpacing * SHIFT_THREE,
            picYStart + picHeight);
    ultiBallPic.draw(canvas);
    //Draw ulti ball amount
    mcPaint.setColor(getResources().getColor(R.color.cBlack));
    canvas.drawText(String.valueOf(mcGameReference.getPWUlti()),
            picWidth * SHIFT_TWO + mPicSpacing * SHIFT_THREE,
            text_y, mcPaint);
    //Draw Buy button
    buyButton.setBounds(picWidth * SHIFT_TWO + mPicSpacing * SHIFT_THREE,
            picYStart + picHeight + picButtonSpacing,
            picWidth * SHIFT_THREE + mPicSpacing * SHIFT_THREE,
            picYStart + picHeight + picButtonSpacing + buyButtonHeight);
    buyButton.draw(canvas);
    //If can't buy draw shade over it
    if (!mbCanBuyUlti)
    {
      @SuppressLint("DrawAllocation")
      Rect cUltiCantBuy = new Rect(picWidth * SHIFT_TWO
              + mPicSpacing * SHIFT_THREE,
              picYStart + picHeight + picButtonSpacing,
              picWidth * SHIFT_THREE + mPicSpacing * SHIFT_THREE,
              picYStart + picHeight + picButtonSpacing + buyButtonHeight);
      mcPaint.setColor(getResources().getColor(R.color.cGray));
      mcPaint.setStyle(Paint.Style.FILL);
      mcPaint.setAlpha(CANT_BUY_OPACITY);
      canvas.drawRect(cUltiCantBuy, mcPaint);
    }
    //Draw back button
    Drawable back = getResources().getDrawable(R.drawable.backtomenu, null);
    back.setBounds(mBackMargin, getHeight() - BACK_BUTTON_Y_POSITION,
            mBackMargin + backButtonWidth,
            getHeight() - BACK_BUTTON_Y_POSITION + backButtonHeight);
    back.draw(canvas);

    if (mbFirstRender)
    {
      invalidate();
      mbFirstRender = false;
    }
  }

  /**
   * Handles the behavior when the user touches the screen.
   * (Buys power ups and goes back to menu)
   *
   * @param event event input details from the touch screen event
   */
  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
    int xPosition = (int) event.getX();
    int yPosition = (int) event.getY();

    if (event.getAction() != MotionEvent.ACTION_DOWN)
    {
      return super.onTouchEvent(event);
    }

    //Check if back button is clicked
    if ((yPosition >= mHeight - BACK_BUTTON_Y_POSITION
            && yPosition <= mHeight - BACK_BUTTON_Y_POSITION
            + backButtonHeight)
            && (xPosition >= mBackMargin && xPosition
            <= mBackMargin + backButtonWidth))
    {
      mcGameReference.onBackClicked();
    }

    //Check if buy PWMulti is clicked
    if ((xPosition >= mPicSpacing && xPosition <= mPicSpacing + picWidth)
            && (yPosition >= picYStart + picHeight + picButtonSpacing
            && yPosition <= picYStart + picHeight + picButtonSpacing
            + buyButtonHeight) && mbCanBuyMulti)
    {
      mcGameReference.onMultiBuy();
      this.invalidate();
    }
    //Check if buy PWSuper is clicked
    if ((xPosition >= mPicSpacing * SHIFT_TWO + picWidth
            && xPosition <= picWidth * SHIFT_TWO + mPicSpacing * SHIFT_TWO)
            && (yPosition >= picYStart + picHeight + picButtonSpacing
            && yPosition <= picYStart + picHeight
            + picButtonSpacing + buyButtonHeight) && mbCanBuySuper)
    {
      mcGameReference.onSuperBuy();
      this.invalidate();
    }
    //Check if buy PWSuper is clicked and user can buy
    if ((xPosition >= picWidth * SHIFT_TWO + mPicSpacing * SHIFT_THREE
            && xPosition <= picWidth * SHIFT_THREE + mPicSpacing * SHIFT_THREE)
            && (yPosition >= picYStart + picHeight + picButtonSpacing
            && yPosition <= picYStart + picHeight
            + picButtonSpacing + buyButtonHeight) && mbCanBuyUlti)
    {
      mcGameReference.onUltiBuy();
      this.invalidate();
    }

    return true;
  }

}

