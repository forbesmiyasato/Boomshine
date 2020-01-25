package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

import edu.pacificu.cs.group6Boomshine.R;

public class IconHandler {

    private int mWidth;
    private int mHeight;
    private ArrayList<Bitmap> mcPowerupIcons;
    private ArrayList<Rect> mcBoundingRectangles;

    IconHandler(Context context)
    {
        mcPowerupIcons = new ArrayList<>();
        mcBoundingRectangles = new ArrayList<>();

        Bitmap multiBallIcon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.multi_ball);
        Bitmap superBallIcon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.super_ball);
        Bitmap ultimateBallIcon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ultimate_ball);
        mcPowerupIcons.add(multiBallIcon);
        mcPowerupIcons.add(superBallIcon);
        mcPowerupIcons.add(ultimateBallIcon);
    }

    public void drawIcons (Canvas canvas)
    {
        mWidth = canvas.getWidth();
        mHeight = canvas.getHeight();
        int count = 0;
        for (Bitmap cBitmap : mcPowerupIcons)
        {
            Rect spot = new Rect (mWidth - (mWidth / 15) * (count + 2),
                    mHeight - 150, (mWidth - (mWidth / 15) * (count + 2)) +
                    cBitmap.getWidth(), (mHeight - 150) + cBitmap.getHeight());

            canvas.drawBitmap(cBitmap,null,  spot, null);
            count+= 2;
        }
    }
}
