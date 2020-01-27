package edu.pacificu.cs.group6Boomshine;

/**
 * Defines the IExpansionStrategy Interface must be implemented by
 * all power up types to define attributes specific to that power up's
 * explosion behavior.
 *
 * @author Thomas Robasciotti
 * @version 1.0
 * @since 1.24.2019
 */


public interface IExpansionStrategy
{
  /**
   * Defines a handleExploding method that must be defined by classes that
   * implement this interface.
   *
   * @return an int value representing the current explosion modifier
   */

  int handleExploding ();

  /**
   * Defines a getRadiusMultiplier method that must be defined by classes that
   * implement this interface.
   *
   * @return an int value representing the radius multiplier for the strategy
   */

  int getRadiusMultiplier ();
}
