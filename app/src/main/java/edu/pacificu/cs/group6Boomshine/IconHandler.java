package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Icon;

import java.util.ArrayList;

import edu.pacificu.cs.group6Boomshine.R;

public class IconHandler {

    private int mWidth;
    private int mHeight;
    private ArrayList<IconRectangle> mcIcons;
    private Rect mcIconPanel;

    IconHandler(Context context)
    {
        mcIcons = new ArrayList<>();
        mcIconPanel = new Rect (0, 0, 0, 0);
        IconRectangle cIconRect;

        for (ExplodingType eType : ExplodingType.values())
        {
            if (eType != ExplodingType.NORMAL)
            {
                cIconRect = new IconRectangle(eType, context);
                mcIcons.add(cIconRect);
            }
        }
    }

    public void drawIcons (Canvas canvas)
    {
        mWidth = canvas.getWidth();
        mHeight = canvas.getHeight();
        int inc = 0;

        for (IconRectangle cIconRect : mcIcons)
        {
            if (!cIconRect.isSet())
            {
                int left = mWidth - (mWidth / 15) * (inc + 2);
                int right = mHeight - 150;
                int top = (mWidth - (mWidth / 15) * (inc + 2)) + cIconRect.getWidth();
                int bottom = (mHeight - 150) + cIconRect.getHeight();

                cIconRect.setRect(left, right, top, bottom);
                inc += 2;

                if (left < mcIconPanel.left)
                {
                    mcIconPanel.set(left, mcIconPanel.top, mcIconPanel.right, mcIconPanel.bottom);
                }
                if (right > mcIconPanel.right)
                {
                    mcIconPanel.set(mcIconPanel.left, mcIconPanel.top, right, mcIconPanel.bottom);
                }
                if (top < mcIconPanel.top)
                {
                    mcIconPanel.set(mcIconPanel.left, top, mcIconPanel.right, mcIconPanel.bottom);
                }
                if (bottom > mcIconPanel.bottom)
                {
                    mcIconPanel.set(mcIconPanel.left, mcIconPanel.top, mcIconPanel.right, bottom);
                }
            }

            cIconRect.draw(canvas);
        }


    }

    public boolean checkIconBounds (int xTouchPos, int yTouchPos)
    {
        return xTouchPos > this.mcIconPanel.left && xTouchPos < this.mcIconPanel.right &&
                yTouchPos > this.mcIconPanel.bottom && yTouchPos < this.mcIconPanel.top;
    }

    public ExplodingType checkPress(int xTouchPos, int yTouchPos)
    {
        for (IconRectangle cIconRect : mcIcons)
        {
            if (cIconRect.checkPress(xTouchPos, yTouchPos))
            {
                return cIconRect.getExplodingType();
            }
        }

        return ExplodingType.NORMAL;
    }
}
