package edu.pacificu.cs.group6Boomshine;

/**
 * Creates a handleExplodingSuper class that implements the
 * IExpansionStrategy to define explosion behavior for the
 * Super ball power up.
 *
 * @author Thomas Robasciotti
 * @version 1.0
 * @since 1.24.2019
 */

class handleExplodingSuper implements IExpansionStrategy
{
  private final int SPEED_MULTIPLIER = 3;
  private final int RADIUS_MULTIPLIER = 12;

  /**
   * Determines how quickly a circle that implements this strategy
   * will expand/retract when exploding.
   *
   * @return An expansion speed multiplier value for a Super ball
   */
  @Override
  public int handleExploding ()
  {
    return SPEED_MULTIPLIER;
  }

  /**
   * Determines how large a circle that implements this strategy
   * will become when exploding.
   *
   * @return An expansion radius multiplier value for a Super ball
   */
  @Override
  public int getRadiusMultiplier ()
  {
    return RADIUS_MULTIPLIER;
  }
}
