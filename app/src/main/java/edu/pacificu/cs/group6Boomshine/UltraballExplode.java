package edu.pacificu.cs.group6Boomshine;

import android.content.Context;
import android.view.Display;

import java.util.ArrayList;

public class UltraballExplode {

    private final int EXPANTION_MULTIPLIER = 15;

    public ArrayList<ExplodingBoundedMovingCircle> createUltra(ExplodingType eType, Context context, Display display,
                                                               int drawable, int topCoord,
                                                               int leftCoord, int speed, int topBound,
                                                               int bottomBound, int leftBound,
                                                               int rightBound,
                                                               int radius) {

        ArrayList<ExplodingBoundedMovingCircle> cRetList = new ArrayList<>();


        cRetList.add (new ExplodingBoundedMovingCircle(eType, context, display, drawable, topCoord,
                leftCoord, speed, topBound, bottomBound, leftBound, rightBound,
                radius * EXPANTION_MULTIPLIER));

        return cRetList;
    }
}
