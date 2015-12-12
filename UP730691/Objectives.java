import becker.robots.*; //used in testing
/**
 * This class part of my Star trek adventure game
 * the game is a very simple, text based adventure game with a graphical representation 
 * The game has been extended from "SoC World" to be made more interesting!
 * 
 * This is a function class which stores the winning and losing condtions of the game. 
 * 
 * @author  UP730691
 * @version Final
 */

public class Objectives  
{
  private Boolean liftWorking;
  private Boolean win;
  private Boolean lost;
  private Boolean talkedToCaptain;
  private Boolean talkedToScienceOff;
  private int health; // player health 
  
  /**
   * set start varibles 
   */
  public Objectives() 
  {
    win = false;
    lost = false;
    liftWorking = false;  
    talkedToCaptain = false;
    talkedToScienceOff = false;
    health = 5;
  }
  
  /**
   * When game is won. prints winning message
   */
  public void gameWon() 
  {
    System.out.println("The ship is now under your control!"); 
    System.out.println("Tou have saved the crew and neutralized any threats"); 
    System.out.println("Congratulations you have won the game!"); 
    win = true; 
  }
  
  /**
   * When game is won. prints losing message
   */
  public void gameLost() 
  {
    System.out.println("You are dead...."); 
    System.out.println("Your cold corpse lays there as the enemy has control of the ship"); 
    System.out.println("Game over.");
    lost = true; 
  }
  
  
  public void liftFixed() 
  {
    liftWorking = true; 
  }
  
  public void captainTalked() 
  {
    talkedToCaptain = true; 
  }
  
  public void scienceOffTalked() 
  {
    talkedToScienceOff = true; 
  }
  
  /**
   * playler loses health, when health is zero player
   * dies and game is over
   * @param robotPlayer the robot controled by the player
   */
  public void loseHealth(MyBot robotPlayer) 
  {
    health = health - 1;
    if (health == 0)
    {
      robotPlayer.botDie();
      gameLost(); 
    } 
  }
  
  // GET
  public boolean checkWin() 
  {
    return win;
  }
  
  public boolean checkLost() 
  {
    return lost;
  }
  
  public boolean checkLiftWorking() 
  {
    return liftWorking;
  }
  
  public boolean checkCaptainTalked() 
  {
    return talkedToCaptain; 
  }
  
  public boolean checktalkedToScienceOff() 
  {
    return talkedToScienceOff; 
  }
  
  public int getHealth() 
  {
    return health; 
  }
  
  public static void main(String[] args)
  {
    Objectives objectives = new Objectives();
    
    System.out.println("gameWon()"); 
    objectives.gameWon();
    System.out.println();
    
    System.out.println("gameLost()"); 
    objectives.gameLost();
    System.out.println(); 
    
    System.out.println("Test losing health"); 
    
    City testCity = new City(11,16);
    
    MyBot robotPlayerTest = new MyBot(testCity, 2, 11, becker.robots.Direction.WEST);
    
    
    System.out.println("Expected 5, restult:" + objectives.getHealth());
    objectives.loseHealth(robotPlayerTest);
    System.out.println("Expected 4, restult:" + objectives.getHealth());
    objectives.loseHealth(robotPlayerTest);
    System.out.println("Expected 3, restult:" + objectives.getHealth());
    objectives.loseHealth(robotPlayerTest);
    System.out.println("Expected 2, restult:" + objectives.getHealth());
    objectives.loseHealth(robotPlayerTest);
    System.out.println("Expected 1, restult:" + objectives.getHealth());
    
    System.out.println("Expected robot dies and objectives.gameLost();");
    objectives.loseHealth(robotPlayerTest);
    
    
    
    
  }
  
  
}