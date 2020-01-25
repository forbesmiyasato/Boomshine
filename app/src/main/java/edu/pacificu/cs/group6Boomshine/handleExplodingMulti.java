package edu.pacificu.cs.group6Boomshine;

class handleExplodingMulti implements IExpansionStrategy {
    @Override
    public int handleExploding() {
        return 2;
    }

    @Override
    public int getRadiusMultiplier() {
        return 6;
    }
}
