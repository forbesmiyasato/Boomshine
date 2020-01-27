package edu.pacificu.cs.group6Boomshine;

/**
 * Creates a handleExplodingUltra class that implements the
 * IExpansionStrategy to define explosion behavior for the
 * Ultra ball power up.
 *
 * @author Thomas Robasciotti
 * @version 1.0
 * @since 1.24.2019
 */

class handleExplodingUltra implements IExpansionStrategy
{
  private final int SPEED_MULTIPLIER = 10;
  private final int RADIUS_MULTIPLIER = 50;

  /**
   * Determines how quickly a circle that implements this strategy
   * will expand/retract when exploding.
   *
   * @return An expansion speed multiplier value for an Ultra ball
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
   * @return An expansion radius multiplier value for an Ultra ball
   */

  @Override
  public int getRadiusMultiplier ()
  {
    return RADIUS_MULTIPLIER;
  }
}
