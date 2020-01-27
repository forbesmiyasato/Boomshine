package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.ArrayList;

/**
 * Defines the IconHandler class that contains all selectable icons
 * that will be displayed during game play. Exposes IconRectangle behavior
 * and scales the icon region of the screen based on device screen
 * size.
 *
 * @author Thomas Robasciotti
 * @version 1.0
 * @since 1.25.2019
 */

public class IconHandler {

    private int mWidth;
    private int mHeight;
    private ArrayList<IconRectangle> mcIcons;
    private Rect mcIconPanel;

    /**
     * Initializes member variables to default values.
     * Creates an array of IconRectangles based on values
     * contained in the ExplodingType enumeration.
     *
     * @param context The calling context object
     */

    IconHandler(Context context)
    {
        mcIcons = new ArrayList<>();
        mcIconPanel = new Rect (Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0);
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

    public void drawIcons(Canvas canvas, int userMultiPowerups, int userUltraPowerups,
                          int userSuperPowerups)
    {
        mWidth = canvas.getWidth();
        mHeight = canvas.getHeight();
        int numCurrent;
        int inc = 0;

        for (IconRectangle cIconRect : mcIcons)
        {
            if (!cIconRect.isSet())
            {
                int left = mWidth - (mWidth / 15) * (inc + 2);
                int top = mHeight - 150;
                int right = (mWidth - (mWidth / 15) * (inc + 2)) + cIconRect.getWidth();
                int bottom = (mHeight - 150) + cIconRect.getHeight();

                cIconRect.setRect(left, top, right, bottom);
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

            switch(cIconRect.getExplodingType())
            {
                case MULTI:
                    cIconRect.setNum(userMultiPowerups);
                    break;
                case SUPER:
                    cIconRect.setNum(userSuperPowerups);
                    break;
                case ULTIMATE:
                    cIconRect.setNum(userUltraPowerups);
                    break;
                default:
                    break;
            }
            cIconRect.draw(canvas);
        }
    }

    public void resetIcons()
    {
        for (IconRectangle cIconRect : mcIcons)
        {
            cIconRect.reset();
        }
    }

    public boolean checkIconBounds (int xTouchPos, int yTouchPos)
    {
        return xTouchPos > this.mcIconPanel.left && xTouchPos < this.mcIconPanel.right &&
                yTouchPos < this.mcIconPanel.bottom && yTouchPos > this.mcIconPanel.top;
    }

    public ExplodingType checkPress(int xTouchPos, int yTouchPos)
    {
        IconRectangle temp = null;

        for (IconRectangle cIconRect : mcIcons)
        {
            if (cIconRect.checkPress(xTouchPos, yTouchPos) && (cIconRect.getCount() > 0))
            {
                temp = cIconRect;
                return cIconRect.getExplodingType();
            }
        }

        for (IconRectangle cIconRect : mcIcons)
        {
            if (cIconRect != temp) {
                cIconRect.reset();
            }
        }

        return ExplodingType.NORMAL;
    }
}
