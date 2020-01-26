package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class IconRectangle {
    private Rect mcBoundingRect;
    private Bitmap mBitmap;
    private ExplodingType meType;
    private boolean set;

    IconRectangle (ExplodingType eType, Context context)
    {
        this.mcBoundingRect = new Rect();
        this.meType = eType;
        set = false;

        switch (meType)
        {
            case MULTI:
                mBitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.multi_ball);
                break;
            case SUPER:
                mBitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.super_ball);
                break;
            case ULTIMATE:
                mBitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.ultimate_ball);
                break;
            default:
                break;

        }

    }

    public void setRect (int left, int right, int top, int bottom)
    {
        this.mcBoundingRect.set(left, right, top, bottom);
        this.set = true;
    }

    public void draw (Canvas canvas)
    {
        canvas.drawBitmap(this.mBitmap, null, this.mcBoundingRect, null);
    }

    public int getWidth ()
    {
        return this.mBitmap.getWidth();
    }

    public int getHeight ()
    {
        return this.mBitmap.getHeight();
    }

    public ExplodingType getExplodingType ()
    {
        return this.meType;
    }

    public boolean isSet() {
        return this.set;
    }

    public boolean checkPress (int xPressPos, int yPressPos)
    {
        return xPressPos > this.mcBoundingRect.left && xPressPos < this.mcBoundingRect.right &&
                yPressPos > this.mcBoundingRect.bottom && yPressPos < this.mcBoundingRect.top;
    }
}
