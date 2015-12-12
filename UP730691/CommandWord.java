/**
 * This class part of my Star trek adventure game
 * the game is a very simple, text based adventure game with a graphical representation 
 * The game has been extended from "SoC World" to be made more interesting!
 * 
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author  UP730691
 * @version Final
 */

public enum CommandWord
{
  // A value for each command word along with its
  // corresponding user interface string.
  MOVE("move"),TURN("turn"), QUIT("quit"), HELP("help"), BACK("back"), USE("use"), SCAN("scan"), 
    TAKE("take"), DROP("drop"), INVENTORTY("inventory"), TALK("talk"), HEALTH("health"), UNKNOWN("?");
  
  // The command string.
  private String commandString;
  
  /**
   * Initialise with the corresponding command word.
   * @param commandWord The command string.
   */
  CommandWord(String commandString)
  {
    this.commandString = commandString;
  }
  
  /**
   * @return The command word as a string.
   */
  public String toString()
  {
    return commandString;
  }
}
