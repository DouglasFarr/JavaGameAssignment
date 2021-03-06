/**
 * This class part of my Star trek adventure game
 * the game is a very simple, text based adventure game with a graphical representation 
 * The game has been extended from "SoC World" to be made more interesting!
 * 
 *  An enumeration class to represent compass directions
 * 
 * @author  UP730691
 * @version Final
 */


public enum Direction
{
  // A value for each direction along with its
  // corresponding user interface string.
  NORTH("north"), SOUTH("south"), EAST("east"), WEST("west");
  
  // The direction string.
  private String directionString;
  
  /**
   * Initialise with the corresponding direction.
   * @param directionString The direction as a string.
   */
  Direction(String directionString)
  {
    this.directionString = directionString;
  }
  
  /**
   * @return The command word as a string.
   */
  public String toString()
  {
    return directionString;
  }
  
}