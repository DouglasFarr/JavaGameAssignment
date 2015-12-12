/**
 * This class part of my Star trek adventure game
 * the game is a very simple, text based adventure game with a graphical representation 
 * The game has been extended from "SoC World" to be made more interesting!
 * 
 * Representations all other robots in the game.
 * These are not controled by the player
 * The player can interact with the robots
 * 
 * @author  UP730691
 * @version Final
 */


import becker.robots.*;
import java.awt.Color;

public class NPC_Bot extends MyBot
{
  /**
   * Create NPC_Bot.
   * @param theCity - The city in which the robot will exist.
   * @param avenue - The robot's initial avenue within the city.
   * @param street - The robot's initial street within the city.
   * @param aDirection - The robot's initial direction. One of {Direction.NORTH, EAST, SOUTH, WEST}.
   * @param aColor The colour of the robot
   */
  public NPC_Bot(City theCity, int avenue, int street, becker.robots.Direction aDirection, Color aColor)
  { super(theCity, avenue, street, aDirection);
    
    setColor(aColor);
  }
  
  /**
   * Construct a new Robot at the given location in the given 
   * city with the given number of things in its backpack.
   * @param theCity - The city in which the robot will exist.
   * @param avenue - The robot's initial avenue within the city.
   * @param street - The robot's initial street within the city.
   * @param aDirection - The robot's initial direction. One of {Direction.NORTH, EAST, SOUTH, WEST}.
   * @param aColor The colour of the robot
   * @param numThings - the number of things that start in backpack
   */
  public NPC_Bot(City theCity, int avenue, int street, becker.robots.Direction aDirection, int numThings, Color aColor)
  { super(theCity, avenue, street, aDirection, numThings);
    
    setColor(aColor);
  } 
  
  
  /**
   * Robot moves and then drops thing
   */
  public void botGiveItem()
  {
    turnLeft();
    move();
    turnRight();
    move();
    putThing();
    turnAround();
    move();
    turnLeft();
    move();
    turnLeft();
  }
  
  //testing
  public static void main(String[] args)
  {
    City testCity = new City(11,16);
    Room aRoom;
    NPC_Bot testAIBot = new NPC_Bot(testCity, 3, 3, becker.robots.Direction.WEST, 1, Color.blue);
    
    //TEST botGiveItem
    System.out.println("Expected: robot moves from 3,3 to 4,2 and drops item then returns 3,3");
    testAIBot.botGiveItem();
    
    
  }
  
}