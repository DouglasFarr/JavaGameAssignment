import becker.robots.*;
import java.awt.Color;

/**
 * This class part of my Star trek adventure game
 * the game is a very simple, text based adventure game with a graphical representation 
 * The game has been extended from "SoC World" to be made more interesting!
 * 
 * The represents the robot controled by the player in the game
 * 
 * @author  UP730691
 * @version Final
 */


public class MyBot extends RobotSE
{
  
  private int botSpeed; // speed of robot
  
  /**
   * Creates the robot
   * @param theCity - The city in which the robot will exist.
   * @param avenue - The robot's initial avenue within the city.
   * @param street - The robot's initial street within the city.
   * @param aDirection - The robot's initial direction. One of {Direction.NORTH, EAST, SOUTH, WEST}.
   */
  public MyBot(City theCity, int avenue, int street, becker.robots.Direction aDirection)
  { super(theCity, avenue, street, aDirection);
    
    botSpeed = 1;
    setSpeed(botSpeed); 
    
  }
  
  // used for NPC bots that inherits MyBot
  public MyBot(City theCity, int avenue, int street, becker.robots.Direction aDirection, int numThings)
  { super(theCity, avenue, street, aDirection, numThings);
    
  }
  
  /**
   *  Moves the robot into the next room
   */
  public void botMove()
  {
    move(3);    
  }
  
  
  /**
   *  Robots pick up thing
   */
  public void botAddItem()
  {
    move();
    pickThing(); 
    turnAround();
    move();
    turnAround();
  }
  
  /**
   *  Robot drops thing
   */
  public void botDropItem()
  {
    move();
    putThing(); 
    turnAround();
    move();
    turnAround();
  } 
  
  /**
   *  robots walks up to thing
   */
  public void botUseItem()
  {
    move();
    turnAround();
    move();
    turnAround();
  } 
  
  
  /**
   *  robot turns around representing getting shot.
   */
  public void botHit()
  {
    setSpeed(4);
    turnLeft(4);
    setSpeed(botSpeed); 
  } 
  
  
  
  /**
   * Simulates the death of a bot
   */
  public void botDie()
  {
    setSpeed(1);
    turnAround();
    setSpeed(0.6);
    turnAround();
    setColor(Color.darkGray);
  }
  
  
  //use items
  
  
  /**
   * bots speeds increacreses to defult (2) when player
   * instructs to use hypospray
   */
  public void useHypospray()
  {
    botSpeed = 2;
    setSpeed(botSpeed); 
    
  }
  
  /**
   * pad moves the bot to new room
   * speed set to 999 making it look instant
   */
  public void usePad1()
  {
    startTransport();
    setSpeed(999);
    move(13);
    turnAround();
    endTransport();
    setSpeed(botSpeed); 
  }
  
  /**
   * pad moves the bot to new room
   * speed set to 999 making it look instant
   */
  public void usePad2()
  {
    startTransport();
    setSpeed(999);
    move();
    turnRight();
    move(6);
    turnLeft();
    move(3);
    turnLeft();
    move(3);
    turnAround();
    endTransport();
    setSpeed(botSpeed); 
  }
  
  /**
   * pad moves the bot to new room
   * speed set to 999 making it look instant
   */
  public void usePad3()
  {
    startTransport();
    setSpeed(999);
    move();
    turnLeft();
    move(3);
    turnRight();
    move(3);
    endTransport();
    turnAround();
    setSpeed(botSpeed); 
  }
  
  /**
   * simulates the player being transported 
   * speed increases and flashes colour
   */
  public void startTransport()
  {
    move(); 
    turnAround();
    
    System.out.println("Energize");
    
    double i = 2;
    boolean red = true;
    
    while (i<=8)
    {
      
      turnLeft();
      setSpeed(i); 
      i++;
      
      if(red == true)
      {
        setColor(Color.blue);
        red = false;
      }else 
      {
        setColor(Color.red);
        red = true;
        
      }
      
    } 
    
    turnLeft(); 
  }
  
  
  /**
   * simulates the player being transported 
   * speed decreases and flashes colour
   */
  public void endTransport()
  {
    
    double i = 8;
    boolean red = false;
    
    while (i>=2)
    {
      
      turnLeft();
      setSpeed(i); 
      i = i - 1;
      
      if(red == true)
      {
        setColor(Color.blue);
        red = false;
      }else 
      {
        setColor(Color.red);
        red = true;
        
      }
      
    } 
    
    turnLeft();
    turnAround();
  }
  
  //testing
  public static void main(String[] args)
  {
    City testCity = new City(11,16);
    Thing testThing = new Thing(testCity,2,4);
    
    MyBot robotPlayerTest = new MyBot(testCity, 2, 11, becker.robots.Direction.WEST);
    
    System.out.println("Expected: robots moves slow");
    robotPlayerTest.botMove();
    
    robotPlayerTest.useHypospray();
    
    System.out.println("Expected: robots moves normal speed");
    robotPlayerTest.botMove();
    
    System.out.println("Expected: robots picks up thing and goes back to starting position");
    robotPlayerTest.botAddItem();
    
    System.out.println("Expected: robots drops thing and goes back to starting position");
    robotPlayerTest.botDropItem();
    
    System.out.println("Expected: robots moves on top of thing and goes back to starting position");
    robotPlayerTest.botUseItem();
    
    System.out.println("Expected: robots spins around fast");
    robotPlayerTest.botHit();
    
    
    System.out.println("Expected: robots spins around acellerating flashing");
    robotPlayerTest.startTransport();
    
    System.out.println("Expected: robots spins around decellerating flashing");
    robotPlayerTest.startTransport();
    
    
    System.out.println("Expected: robots spins slowly and changes to grey");
    robotPlayerTest.botDie();
    
    
    
    
  }
}


