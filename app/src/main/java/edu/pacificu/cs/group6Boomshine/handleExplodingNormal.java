package edu.pacificu.cs.group6Boomshine;

public class handleExplodingNormal implements IExpansionStrategy {
    @Override
    public int handleExploding() {
        return 2;
    }

    @Override
    public int getRadiusMultiplier() {
        return 6;
    }
}
