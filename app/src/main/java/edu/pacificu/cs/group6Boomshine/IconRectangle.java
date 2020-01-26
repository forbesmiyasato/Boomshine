package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Paint;

public class IconRectangle {
    private final float TEXT_SIZE = 45f;
    private Rect mcBoundingRect;
    private Bitmap mPassiveBitmap;
    private Bitmap mSelectedBitmap;
    private int mNum;
    private Paint mTextPaint;
    private ExplodingType meType;
    private boolean mbSet;
    private boolean mbIsSelected;

    IconRectangle (ExplodingType eType, Context context)
    {
        this.mcBoundingRect = new Rect();
        this.meType = eType;
        this.mNum = 0;
        this.mbSet = false;
        this.mbIsSelected = false;
        this.mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(TEXT_SIZE);

        switch (meType)
        {
            case MULTI:
                mPassiveBitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.multi_ball);
                mSelectedBitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.multi_ball_selected);
                break;
            case SUPER:
                mPassiveBitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.super_ball);
                mSelectedBitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.super_ball_selected);
                break;
            case ULTIMATE:
                mPassiveBitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.ultra_ball);
                mSelectedBitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.ultra_ball_selected);
                break;
            default:
                break;

        }

    }

    public void setRect (int left, int top, int right, int bottom)
    {
        this.mcBoundingRect.set(left, top, right, bottom);
        this.mbSet = true;
    }

    public void draw (Canvas canvas)
    {
        if (this.mbIsSelected)
        {
            canvas.drawBitmap(this.mSelectedBitmap, null, this.mcBoundingRect, null);
            canvas.drawText(Integer.toString(this.mNum), mcBoundingRect.left, mcBoundingRect.top,
                    mTextPaint);
        }
        else
        {
            canvas.drawBitmap(this.mPassiveBitmap, null, this.mcBoundingRect, null);
            canvas.drawText(Integer.toString(this.mNum), mcBoundingRect.left, mcBoundingRect.top,
                    mTextPaint);
        }
    }

    public int getWidth ()
    {
        return this.mPassiveBitmap.getWidth();
    }

    public int getHeight ()
    {
        return this.mPassiveBitmap.getHeight();
    }

    public ExplodingType getExplodingType ()
    {
        return this.meType;
    }

    public boolean isSet() {
        return this.mbSet;
    }

    public boolean checkPress (int xPressPos, int yPressPos)
    {
        if (xPressPos > this.mcBoundingRect.left && xPressPos < this.mcBoundingRect.right &&
                yPressPos < this.mcBoundingRect.bottom && yPressPos > this.mcBoundingRect.top &&
                !this.mbIsSelected)
        {
            this.mbIsSelected = true;
        }
        else
        {
            this.mbIsSelected = false;
        }

        return mbIsSelected;
    }

    public void reset()
    {
        this.mbIsSelected = false;
    }

    public boolean isSelected ()
    {
        return this.mbIsSelected;
    }

    public void setNum(int numPowerup)
    {
        this.mNum = numPowerup;
    }
}
