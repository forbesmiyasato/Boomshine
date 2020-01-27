package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Paint;

/**
 * Defines the IconRectangle class that contains information regarding
 * any selectable icons that appear on the screen during game play.
 * Handles icon bounding, passive/selected icon bitmap values,
 * and icon selection logic.
 *
 * @author Thomas Robasciotti
 * @version 1.0
 * @since 1.25.2019
 */

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

    /**
     * Initializes member variables with default values.
     * Sets bitmap values based on resources found in the
     * drawable directory. All icons must have both a passive
     * and active image saved in the drawable folder.
     * Bounding boxes for individual icons are defined by
     * current device screen size.
     *
     * @param eType The ExplodingType associated with the power up icon
     * @param context The calling application context
     */

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

    /**
     * Sets the icon's bounding rectangle values base don passed-in
     * coordinate values.
     *
     * @param left The left-bound for the rectangle
     * @param top The top-bound for the rectangle
     * @param right right left-bound for the rectangle
     * @param bottom bottom left-bound for the rectangle
     *
     */

    public void setRect (int left, int top, int right, int bottom)
    {
        this.mcBoundingRect.set(left, top, right, bottom);
        this.mbSet = true;
    }

    /**
     * Draws the icon via the passed-in Canvas object.
     * Determines which icon (passive/active) icon to
     * draw based on if the icon has been selected.
     *
     * @param canvas The Canvas object reference to draw the
     *               icon to.
     *
     */

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

    /**
     * Retrieves the width of the icon bitmap
     *
     * @return The bitmap width
     *
     */

    public int getWidth ()
    {
        return this.mPassiveBitmap.getWidth();
    }

    /**
     * Retrieves the height of the icon bitmap
     *
     * @return The bitmap height
     *
     */

    public int getHeight ()
    {
        return this.mPassiveBitmap.getHeight();
    }

    /**
     * Retrieves the ExplodingType of the icon
     *
     * @return The ExplodingType value of the icon
     *
     */

    public ExplodingType getExplodingType ()
    {
        return this.meType;
    }

    /**
     * Retrieves the isSet value of the icon.
     * isSet is true when the icon's bounding box has been
     * initialized.
     *
     * @return True if the icon's bounding rectangle has been set,
     * otherwise returns false
     *
     */

    public boolean isSet() {
        return this.mbSet;
    }

    /**
     * Checks if a touch event coordinates fall within this
     * icon's bounding rectangle and toggles the isSelected
     * value.
     *
     * @param xPressPos The x-coordinate of a touch event
     * @param yPressPos The y-coordinates of a touch event
     *
     * @return True if the passed in coordinates fall within the icon's
     * bounding rectangle and the icon was not already selected, otherwise
     * returns false.
     *
     */

    public boolean checkPress (int xPressPos, int yPressPos)
    {
        if (xPressPos > this.mcBoundingRect.left && xPressPos < this.mcBoundingRect.right &&
                yPressPos < this.mcBoundingRect.bottom && yPressPos > this.mcBoundingRect.top &&
                this.mNum > 0)
        {
            this.mbIsSelected = !mbIsSelected;
        }

        return mbIsSelected;
    }

    /**
     * Forces the icon to become un-selected
     *
     */

    public void reset()
    {
        this.mbIsSelected = false;
    }

    /**
     * Sets the current number of this power up type that is available
     * to the user
     *
     * @param numPowerup The number of this type of power up
     *                   available to the user
     */

    public void setNum(int numPowerup)
    {
        this.mNum = numPowerup;
    }

    /**
     * Retrives this icon's number value
     *
     * @return The number value of this power up type
     */

    public int getCount()
    {
        return this.mNum;
    }
}
