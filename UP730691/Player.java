/**
 * This class part of my Star trek adventure game
 * the game is a very simple, text based adventure game with a graphical representation 
 * The game has been extended from "SoC World" to be made more interesting!
 * 
 * A class to represent an adventurer playing the exciting
 * game of "Soc World". A player has a location (of class Room)
 * and is facing a particular direction.
 * 
 * @author  UP730691
 * @version Final
 */

public class Player
{
  private Room here;
  private Direction facing;
  
  /**
   *  Construct a player with "null" location
   *  facing east
   */
  public Player()
  {
    here = null;
    facing = Direction.WEST;
  }
  
  /**
   *  return the direction the player is facing
   * @return facing the direction the player is facing
   */
  public Direction getDirection()
  {
    return facing;
  }
  
  /**
   *  return the room the player is currently in
   * @return the room the players in
   */
  public Room getRoom()
  {
    return here;
  }
  
  /**
   *    make the player turn left
   */
  public void turnLeft()
  {
    if(facing == Direction.NORTH)
    {facing = Direction.WEST;} 
    else if(facing == Direction.WEST)
    {facing = Direction.SOUTH;}
    else if(facing == Direction.SOUTH)
    {facing = Direction.EAST;}
    else
    {facing = Direction.NORTH;}
  }
  
  /**
   *  make the player turn right
   */
  public void turnRight()
  {
    if(facing == Direction.NORTH)
    {facing = Direction.EAST;} 
    else if(facing == Direction.WEST)
    {facing = Direction.NORTH;}
    else if(facing == Direction.SOUTH)
    {facing = Direction.WEST;}
    else
    {facing = Direction.SOUTH;}
  }
  
  /**
   *  make the player turn around (2 rights)
   */
  public void turnAround()
  {
    turnRight();
    turnRight();
  }
  
  /**
   *  set the players current location
   * @param r the room which the player is entering
   */
  
  public void setRoom(Room r)
  {
    here = r;
  }
  
  /**
   *  set the direction the player is facing
   * @param d The new direction for the player
   */
  public void setDirection(Direction d)
  {
    facing = d;
  }
}
