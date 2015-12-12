import becker.robots.*; //thing
import java.awt.Color;

/**
 * This class part of my Star trek adventure game
 * the game is a very simple, text based adventure game with a graphical representation 
 * The game has been extended from "SoC World" to be made more interesting!
 * 
 * A "Item" represents one thing game. A thing apear on the map and can be 
 * picked up or used
 * 
 * @author  UP730691
 * @version Final
 */


public class Item
{
  
  private String description;  // description of thing
  private int weight; // weight of thing
  private Room room; // room thing is in
  private String direction; // direction of thing
  private Boolean carried; // if the thing is carried
  
  /**
   * creates an item 
   * @param aDescription The room's description.
   * @param aWeight The weight of the item
   * @param aRoom the room the item is in
   * @param aDirection the direction the item is is
   * @param carriedStatus if the item is carried or not
   */
  public Item(String aDescription, int aWeight, Room aRoom, String aDirection, Boolean carriedStatus)
  {
    description = aDescription;
    weight = aWeight;
    room = aRoom;
    direction = aDirection;
    carried = carriedStatus;
  }
  
  // for items that cant be carried
  public Item(String aDescription, Room aRoom, String aDirection)
  {
    description = aDescription;
    weight = 999; // rogue value for items that cant be picked up
    room = aRoom;
    direction = aDirection;
    carried = false;  
  }
  
  /**
   *  set Items description
   * @param aDescription the description of the item
   */
  public void setItemDescription(String aDescription)
  {
    description = aDescription;
  }
  
  /**
   *  set Items carried status
   * changes when player picks up the item
   * @param x, true if carried
   */ 
  public void setCarried(Boolean x)
  {
    carried = x;
  }
  
  /**
   * get Items description
   * @return description the description of the item
   */
  public String getItemDescription()
  {
    return description;
  }
  
  /**
   * get Room
   * @return room the room items in
   */
  public Room getItemRoom()
  {
    return room;
  }
  
  /**
   * get weight
   * @return weight the weight of the item 
   */
  public int getWeight()
  {
    return weight;
  }
  
  /**
   * get item direction
   * @return direction the direction 
   */
  public String getItemDirection()
  {
    return direction;
  }
  
  /**
   * get item carried status
   * @return carried is the item is being carried
   */
  public Boolean getCarried()
  {
    return carried;
  }
  
  /**
   * puts the item down and sets its new room and direction 
   * @param aRoom the room the item is being dropped 
   * @param aDirection the direction the item is being dropped
   */
  public void putDownItem(Room aRoom, String aDirection)
  {
    room = aRoom;
    direction = aDirection;
    carried = false;
  }
  
  
  //testing
  public static void main(String[] args)
  {
    
    Room aRoom = new Room("Test room"); 
    
    //static item
    Item testItem = (new Item("test item text", aRoom, "north")); 
    
    
    System.out.println("Test: .getItemDescription()");
    System.out.println("Expected: test item text");
    System.out.println("Result: " + testItem.getItemDescription());
    
    System.out.println();
    
    System.out.println("Test: setItemDescription(String aDescription)");
    testItem.setItemDescription("Test Test Test");
    System.out.println("Expected: Test Test Test");
    System.out.println("Result: " + testItem.getItemDescription());
    
    System.out.println();
    
    System.out.println("Test: getWeight()");
    System.out.println("Expected: 999");
    System.out.println("Result: " + testItem.getWeight());
    
    System.out.println();
    
    //item can be carried
    Item testItem2 = new Item("test", 4, aRoom, "north", false);
    
    System.out.println("Test: getCarried()");
    System.out.println("Expected: false");
    System.out.println("Result: " + testItem2.getCarried());
    
    System.out.println();
    
    System.out.println("Test: setCarried()");
    System.out.println("Expected: true");
    testItem2.setCarried(true);
    System.out.println("Result: " + testItem2.getCarried());
    
    System.out.println();
    
    System.out.println("Test: putDownItem(Room aRoom, String aDirection)");
    testItem2.putDownItem(aRoom, "south");
    System.out.println("Expected: south");
    System.out.println("Result: " + testItem2.getItemDirection());
    System.out.println("Expected: Test room");
    System.out.println("Result: " + testItem2.getItemRoom().getShortDescription());
    
  }
}




