import becker.robots.*;
import java.util.ArrayList;
import java.awt.Color; // used for colour or aiBots

/**
 *  This class is the main class of the Star trek adventure game
 *  the game is a very simple, text based adventure game with a graphical representation 
 *  The game has been extended from "SoC World" to be made more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  UP730691
 * @version Final
 */

public class Game 
{
  private Parser parser;
  private Player thePlayer;
  
  private Room lastRoom;
  private String lastRoomDirection;
  private int weight;
  private int maxWeight;
  private int thingPos;
  private Objectives objectives;
  private Color gold = new Color(218,165,32);
  
  private City starship = new City(11,17);
  private MyBot robotPlayer = new MyBot(starship, 2, 5, becker.robots.Direction.WEST);
  
  private ArrayList<Item> itemList;
  private ArrayList<Thing> thingsArr;
  
  private Room corridor1, corridor2, corridor3, sickBay, engineering, turboLift, transporterRoom, captainsQuarters, 
    bridge, cargoBay, scienceLab;
  
  private NPC_Bot captainBot;
  private NPC_Bot scienceOfficer;
  private NPC_Bot badGuy;
  
  /**
   * Create the game and initialise its internal map.
   */
  public Game() 
  {
    thePlayer = new Player();
    parser = new Parser();
    
    weight = 0;
    thingPos = 0;
    maxWeight = 5;
    objectives = new Objectives();
    
    createRooms();
    createItems();
    graphicsCreateRoom();
  }
  
  /**
   * Create all the rooms and link their exits together.
   * Create all items and add them to the map.
   */
  private void createRooms()
  {
    
    // create the rooms
    corridor1 = new Room("in a corridor with grey paneled walls");
    corridor2 = new Room("in a corridor with grey paneled walls");
    corridor3 = new Room("in a corridor with grey paneled walls");
    sickBay = new Room("in sickbay the main medical center aboard the starship");
    engineering = new Room("in engineering; the location from which the ship's main power systems are controlled");
    turboLift = new Room("in a turboLift, this transports personnel between key sections of a ship");
    transporterRoom = new Room("in the transporter room");
    captainsQuarters = new Room("at the captains quarters");
    bridge  = new Room("on the bridge");
    cargoBay  = new Room("in the cargoBay");
    scienceLab = new Room("in the science lab");
    
    // initialise room exits
    sickBay.setExit("east", corridor1);
    
    corridor1.setExit("west", sickBay);
    corridor1.setExit("south", turboLift);
    corridor1.setExit("east", engineering);
    
    engineering.setExit("west", corridor1);
    
    turboLift.setExit("south", bridge);
    turboLift.setExit("east", corridor3);
    
    corridor3.setExit("west", turboLift);
    corridor3.setExit("east", scienceLab);
    corridor3.setExit("south", captainsQuarters);
    
    scienceLab.setExit("west", corridor3);
    
    captainsQuarters.setExit("north", corridor3);
    
    turboLift.setExit("north", corridor1);
    turboLift.setExit("west", corridor2);
    
    transporterRoom.setExit("east", corridor2);
    
    corridor2.setExit("east", turboLift);
    corridor2.setExit("west", transporterRoom);
    corridor2.setExit("south", cargoBay);
    
    cargoBay.setExit("north", corridor2);
    
    bridge.setExit("north", turboLift);
    
    
    thePlayer.setRoom(sickBay);  // start game in sick bay
    
    lastRoom = sickBay; // used for back command. 
    
  }
  
  /**
   * Create all items and puth them in the rooms on the map
   */
  private void createItems()
  {
    // create the items in rooms
    itemList = new ArrayList<Item>();
    
    storeItem(new Item("hypospray", 1, sickBay, "north", false));
    
    storeItem(new Item("broken engineering console", engineering, "east")); 
    
    storeItem(new Item("life support controls, status: normal", engineering, "south"));
    
    storeItem(new Item("tool", 3, engineering, "north", false));
    
    storeItem(new Item("phaser", 3, cargoBay, "south", false));
    
    String discription = "transporter pad, this will take you to ";
    
    storeItem(new Item(discription + "science lab", transporterRoom, "west"));
    
    storeItem(new Item(discription + "sickBay", transporterRoom, "south"));
    
    storeItem(new Item(discription + "cargo bay", transporterRoom, "north"));
    
    storeItem(new Item("modulator", 2, scienceLab, "south", true)); //  carried by scienceOfficer
    
  }
  
  /**
   * Stores the items in a array list
   * @param a_item The item to add to the list
   */
  public void storeItem(Item a_item)
  {
    itemList.add(a_item);
  }
  
  /**
   * Return a string describing the posision of items, for example
   * "Items: north west".
   * @return returnString 'Items:' with the direction of items in the room
   */
  public String getItemString()
  {
    String returnString = "Items:"; 
    for (Item aItem: itemList) {
      if (aItem.getItemRoom() == thePlayer.getRoom() && aItem.getCarried() == false) {
        returnString += " " + aItem.getItemDirection();
        
      }
    }
    return returnString;
  }
  
  /**
   *  Main play routine.  Loops until end of play.
   */
  public void play() 
  {            
    printWelcome();
    
    // Enter the main command loop.  Here we repeatedly read commands and
    // execute them until the game is over.
    
    boolean finished = false;
    while (! finished) 
    {
      Command command = parser.getCommand();
      finished = processCommand(command);
      
      // check if game is won or lost
      if (objectives.checkWin() == true || objectives.checkLost() == true)
      {
        finished = true; 
      }
      
    }
    System.out.println("Thank you for playing.  Good bye.");
  }
  
  /**
   * Print out the opening message for the player.
   */
  private void printWelcome()
  {
    System.out.println();
    System.out.println("Welcome to Star Trek - The incredibly exciting adventure game!");
    System.out.println("You awake after hearing the sound of red alert, you climb of a bed. No one is around.");
    System.out.println("You need medical attention, look for medical supplies");
    System.out.println("Type 'help' if you need help.");
    System.out.println();
    newRoomMessage(); 
  }
  
  /**
   * Given a command, process (that is: execute) the command.
   * @param command The command to be processed.
   * @return true If the command ends the game, false otherwise.
   */
  private boolean processCommand(Command command) 
  {
    boolean wantToQuit = false;
    
    CommandWord commandWord = command.getCommandWord();
    
    switch (commandWord) 
    {
      case UNKNOWN:
        System.out.println("that is highly illogical. Command not recognized");
        break;
        
      case HELP:
        printHelp();
        break;
        
      case MOVE:
        move();
        break;
        
      case BACK:
        back();
        break;
        
      case TURN:
        turn(command);
        break;
        
      case USE:
        use(command);
        break;   
        
      case SCAN:
        scan();
        break; 
        
      case TAKE:
        take(command);
        break;  
        
      case DROP:
        drop(command);
        break;  
        
      case INVENTORTY:
        inventory();
        break;
        
      case TALK:
        talk();
        break;    
        
      case HEALTH:
        health();
        break;  
        
      case QUIT:
        wantToQuit = quit(command);
        break;
    }
    return wantToQuit;
  }
  
  // implementations of user commands:
  
  /**
   * Print out some help information.
   * Here we print some stupid, cryptic message and a list of the 
   * command words.
   */
  private void printHelp() 
  {
    System.out.println("You are lost. You are alone. You wander around at the ship.");
    System.out.println("To win the game you must get control of the ship");
    System.out.println();
    System.out.println("Your command words are:");
    parser.showCommands();
  }
  
  /** 
   * Try to go in one direction. If there is an exit, enter the new
   * room, otherwise print an error message.
   */
  public void move() 
  {
    
    // Try to leave current room.
    Room nextRoom = thePlayer.getRoom().getExit(thePlayer.getDirection().toString());
    
    
    if (nextRoom == null) 
    {
      System.out.println("You canna change the laws of physics! There is no door");
    }
    else 
    {
      // check if trying to enter turbo ift and if its working 
      if(nextRoom == turboLift && objectives.checkLiftWorking() == false) 
      {
        System.out.println("No power to turbolift");
        return; 
      }
      
      robotPlayer.botMove();
      
      lastRoom = thePlayer.getRoom(); // used for back command 
      thePlayer.setRoom(nextRoom);
      
      graphicsCreateRoom();
      
      newRoomMessage(); 
      
      bridgeCondition(nextRoom); 
    }
  }
  
  /** 
   * Back command takes you back to the last room you�ve been in. 
   * Repeated consecutive use of this command makes you move between two rooms only
   */
  private void back() 
  {
    int i = 0;
    
    
    while (i != 4)
    {
      //check room infront
      if(thePlayer.getRoom().getExit(thePlayer.getDirection().toString()) == lastRoom)
      {
        Room nextRoom = lastRoom; 
        
        robotPlayer.botMove(); //move robot back
        
        lastRoom = thePlayer.getRoom();
        thePlayer.setRoom(nextRoom);
        
        newRoomMessage(); 
        bridgeCondition(nextRoom);
        return;
      }
      
      thePlayer.turnRight();
      robotPlayer.turnRight();
      i = i + 1;
    }
    
    System.out.println("You canna go back");
    // thePlayer.turnAround(); //turn back around to face frount if no exit
  }    
  
  
  /** 
   * Prints the message when a new room is entered 
   */
  public void newRoomMessage()
  {
    System.out.println(thePlayer.getRoom().getLongDescription());
    System.out.println(getItemString());
    System.out.println("You are facing " + thePlayer.getDirection());
    
  }
  
  /**
   * When the player enters bridge
   * @param nextRoom the room the player is about to enter
   */
  public void bridgeCondition(Room nextRoom)
  {
    // if they are entering bridge then get shot
    if (nextRoom == bridge) 
    {
      System.out.println("you have been shot");
      objectives.loseHealth(robotPlayer); 
      robotPlayer.botHit();
      
      health();
    }
    
  }
  
  
  /** 
   * Use items 
   * @param command The commnad entered by the user
   */
  private void use(Command command) 
  {
    String itemText = ""; 
    
    if(!command.hasSecondWord()) 
    {
      // if there is no second word then use whats in front.
      //checks items in front that dont need to be carried
      for (Item aItem: itemList) {
        if (facingItem(aItem) == true && aItem.getWeight()  == 999 ) {
          
          itemText = aItem.getItemDescription();
        }
      }
      
      if (itemText.contains("life support controls"))
      {
        System.out.println("Ihe lights go off, the air is sucked out the room");
        //player loses game  
        objectives.gameLost(); 
        robotPlayer.botUseItem();
        //Spins 3 times
        robotPlayer.botDie(); 
        robotPlayer.botDie();
        robotPlayer.botDie();
        
      } else if (itemText.contains("repaired engineering console"))
      {
        System.out.println("You have restored power to the turbo lift");
        objectives.liftFixed();
        robotPlayer.botUseItem();
      } else if (itemText.contains("broken engineering console"))
      {
        robotPlayer.botUseItem(); //walks to item
        System.out.println("No responce");
      } 
      //Use Transporter
      // the items text contains the new location    
      else if (itemText.contains("science lab"))
      {
        robotPlayer.usePad1();
        thePlayer.setRoom(scienceLab);
        thePlayer.turnAround();
        
        graphicsCreateRoom();
        newRoomMessage(); 
        
        
      } else if (itemText.contains("sickBay"))
      {
        robotPlayer.usePad2();
        thePlayer.setRoom(sickBay);
        thePlayer.turnRight();
        
        graphicsCreateRoom();
        newRoomMessage();  
      }
      
      else if (itemText.contains("cargo bay"))
      {
        robotPlayer.usePad3();
        thePlayer.setRoom(cargoBay);
        
        // stops back command working in this case. keeps consistant. 
        lastRoom = thePlayer.getRoom(); 
        
        thePlayer.turnAround();
        
        graphicsCreateRoom();
        newRoomMessage();  
      }  
      else
      {
        System.out.println("cant be used"); 
      }
      
      return;  
    }
    
    //For carried items. must have a second command word
    
    itemText = command.getSecondWord();
    switch (itemText) 
    {
      
      case "hypospray":
        Item hypospray = itemList.get(0); // uses index of array list
        if (hypospray.getCarried() == true)
        {
          System.out.println("You are feeling better, you can move move at normal speed");
          robotPlayer.useHypospray();
        }else
        {
          itemError(hypospray);
        }
        
        break;
        
      case "tool":
        Item engineeringConsole = itemList.get(1);  
        Item tool = itemList.get(3); 
        
        //only used in the game when facing engineering console and carried
        if(facingItem(engineeringConsole) == true && tool.getCarried() == true)
        {
          
          System.out.println("You have fixed the engineering console");
          engineeringConsole.setItemDescription("repaired engineering console");
          robotPlayer.botUseItem();
          
        }else if (tool.getCarried() == false) // tool not carried
        {
          itemError(tool);
        }
        else{
          System.out.println("Nothing to fix here");
        }
        
        break;
        
      case "phaser":
        Item phaser = itemList.get(4);  
        Item modulator = itemList.get(8);
        
        if (phaser.getCarried() == true)
        {
          
          if (thePlayer.getRoom() == bridge)
          {
            // Wining condidtion of the game
            if (modulator.getCarried() == true &&  objectives.checktalkedToScienceOff() == true && 
                thePlayer.getDirection().toString() == "south"){
              
              
              badGuy.botDie();
              System.out.println("You got him!");
              objectives.gameWon();
              
            }else 
            { 
              System.out.println("You hit him but your weapon is not powerful enough!");
              System.out.println("he quicky gets up and returns fire");
              badGuy.botHit();
              objectives.loseHealth(robotPlayer); 
              robotPlayer.botHit();
              health();
              System.out.println("you will need to find a phaser upgrade");
            }
            
          } else // phaser carried and used but not on bridge 
          {
            System.out.println("No target");
          }
        }else  // phaser not carried
        {
          System.out.println("you cant find " + itemText + " on your person");
        }
        break;
        
        
      default:
        System.out.println(itemText + " is not on your person");
        break;
        
    }
  }
  
  /** 
   * checks if item is infront of player
   */
  private boolean facingItem(Item aItem)
  {
    if (thePlayer.getRoom() == aItem.getItemRoom() && thePlayer.getDirection().toString() == aItem.getItemDirection())
    {
      return true;
    }
    return false;
  }
  
  /** 
   * error message for item not being carried by player
   */
  private void itemError (Item aItem)
  {
    System.out.println("you do not have a " + aItem.getItemDescription() +  " on you person");
  }
  
  /** 
   * Gives the names of item in front. 
   * Example: "Scans dectect a tool"
   */
  private void scan() 
  {
    Boolean found = false; 
    
    String returnString = "Scans detect a "; 
    for (Item aItem: itemList) {
      if (facingItem(aItem) == true &&  aItem.getCarried() == false) {
        returnString += aItem.getItemDescription() + " ";
        found = true;
      }   
    } 
    if (found == false) {
      returnString = "Scans are picking up nothing";
    }
    System.out.println(returnString);
  }
  
  /** 
   * "take" was entered. Second word need with item name. 
   * If item is in front then player picks up
   */
  private void take(Command command)
  { 
    if(!command.hasSecondWord())
    {
      System.out.println("Take what? scan to find items.");
      return;
    }
    
    
    Boolean found = false;
    String itemString =  command.getSecondWord();
    for (Item aItem: itemList) {
      if (facingItem(aItem) == true && aItem.getItemDescription().equals(itemString) &&  
          aItem.getCarried() == false) {
        
        found = true; 
        
        // items with weight cant be picked up
        if (aItem.getWeight() == 999)
        {
          System.out.println(itemString + " cant be carried");
        }else if ((weight + aItem.getWeight()) > maxWeight) // checks weight limit is to high
        {
          System.out.println(itemString + " exceeds your weight limit, the weight of " + itemString + 
                             " is " + aItem.getWeight());
          System.out.println(" The max weight you can carry is " + maxWeight + ". check your inventory");
        }else // pick up item
        {
          weight = weight + aItem.getWeight();
          robotPlayer.botAddItem();
          aItem.setCarried(true);
          System.out.println("you are now carrying " + itemString);
        }
        
      }   
    } 
    if (found == false) {
      
      System.out.println("Item " + itemString + " not found");
    }
  }
  
  /** 
   * "drop" was entered. Checks if the item is on the  
   *  person and if it can be dropped
   *  @param Item name to drop 
   */
  private void drop(Command command)
  { 
    Boolean found = false;
    String itemString =  command.getSecondWord();
    
    if(!command.hasSecondWord()) 
    {
      // if there is no second word, we don't know which way to turn...
      System.out.println("Drop what?");
      return;
    }
    
    
    for (Item aItem: itemList) {
      
      if (aItem.getItemDescription().equals(itemString) &&  aItem.getCarried() == true ) //checks if can be dropped
      {
        //weight update  
        weight = weight - aItem.getWeight();
        found = true;
        robotPlayer.botDropItem();
        aItem.setCarried(false);
        aItem.putDownItem(thePlayer.getRoom(), thePlayer.getDirection().toString()); 
        System.out.println("you have put down " + itemString); 
      } 
      
    }
    
    if (found == false) {
      System.out.println("you cant find " + itemString + " on your person");
    }  
  }
  /** 
   * "inventory" was entered. lists all items the player is 
   *  carrying and the weights 
   */
  private void inventory() 
  {
    Boolean found = false;
    String returnString = ""; 
    System.out.println("Items being carried: ");
    
    for (Item aItem: itemList) {
      if (aItem.getCarried() == true ){
        
        //excludes the item carried by the science officer
        if (aItem.getItemDescription().equals("modulator")  && objectives.checktalkedToScienceOff() == false)  
        {
          
        }else
        {
          
          System.out.println(aItem.getItemDescription() + ", weight = " + aItem.getWeight()); 
          found = true;
          
        }
      }
    }
    if (found == false){
      System.out.println("NONE");
    }
    System.out.println("Your total weight is " + weight);
    System.out.println(" The max weight you can carry is " + maxWeight);
  }
  
  
  /** 
   * "Turn" was entered. Check the rest of the command to see
   * whether we turn left, right or around.
   * @param The direction the player wishes to turn  
   */
  private void turn(Command command)
  {
    if(!command.hasSecondWord()) 
    {
      // if there is no second word, we don't know which way to turn...
      System.out.println("turn which way?");
      return;
    }
    
    String direction = command.getSecondWord();
    
    if(direction.equals("left")) 
    { thePlayer.turnLeft();
      robotPlayer.turnLeft();
    }
    else if(direction.equals("right")) 
    { thePlayer.turnRight();
      robotPlayer.turnRight();
    }
    else if(direction.equals("around")) 
    { thePlayer.turnAround();
      robotPlayer.turnAround();
    }
    else 
    { System.out.println("You can not turn that way");}
    System.out.println("You are currently facing " + thePlayer.getDirection());
  }
  
  /** 
   * "talk" was entered. Check the the rooms the NPC's 
   * are in and give the responce 
   */
  private void talk()
  {
    Room currentRoom = thePlayer.getRoom();
    
    if (currentRoom == captainsQuarters && 
        objectives.checkCaptainTalked() == false)
    {
      System.out.println("The bridge has been taken, he was.. there was... arrgh..");
      captainBot.botDie();
      objectives.captainTalked();
      System.out.println("The captain is now dead.");
    }
    
    else if (currentRoom == scienceLab)
    {
      if (objectives.checktalkedToScienceOff() == false) //ensures only drops item once. 
      {
        System.out.println("I have something for you");
        scienceOfficer.botGiveItem();
        
        Item modulator = itemList.get(8); 
        modulator.setCarried(false);
        System.out.println("This should set phaser to maximum power");
        objectives.scienceOffTalked(); 
        System.out.println(getItemString());
      }
      else { // still talks when drops
        System.out.println("Good luck");
      }
      
    } else if (currentRoom == bridge)
    {
      System.out.println("I will never surrender."); 
    }
    else {
      System.out.println("Hello? no one is around. ");
    } 
  }
  
  /** 
   * "health" was entered. print the health of the player
   */
  private void health()
  {
    
    System.out.println("Your health is : " + objectives.getHealth() + "/5" );  
    
  }
  
  
  
  
  /** 
   * "Quit" was entered. Check the rest of the command to see
   * whether we really quit the game.
   * @return true, if this command quits the game, false otherwise.
   */
  private boolean quit(Command command) 
  {
    
    if(command.hasSecondWord())
    {
      System.out.println("Quit what?");
      return false;
    }
    else 
    {
      return true;  // signal that we want to quit
    }
  }
  
  /** 
   * Create the room and the items and npcs in the room.
   */ 
  public void graphicsCreateRoom()
  {  
    
    int botAv = robotPlayer.getAvenue();
    int botSt = robotPlayer.getStreet();
    int thingStreet;
    int thingAvenue;
    
    if (thePlayer.getRoom().getDrawn()  == false)
    {
      
      //draw things here
      for (Item aItem: itemList) {
        
        //item carried by bot at start, only show items not carried
        if (aItem.getItemRoom() == thePlayer.getRoom() && aItem.getCarried() == false) {  
          
          thingStreet = botSt;
          thingAvenue = botAv;
          
          if(aItem.getItemDirection() == "north")
          {
            thingStreet = thingStreet -1;
            
          }else if(aItem.getItemDirection() == "south")
          {
            thingStreet = thingStreet +1;
          }else if(aItem.getItemDirection() == "east")
          {
            
            thingAvenue = thingAvenue +1;
          }else if(aItem.getItemDirection() == "west")
          {
            
            thingAvenue  = thingAvenue -1;
          }
          
          if (aItem.getWeight() == 999) {
            new Thing(starship, thingStreet, thingAvenue).setColor(Color.darkGray);
          }else{
            
            new Thing(starship, thingStreet, thingAvenue);
          }
          
        }
      }
      
      // Set up Room
      
      //top
      Wall wall1 = new Wall(starship, (botSt-1), (botAv-1), becker.robots.Direction.NORTH);
      Wall wall2 = new Wall(starship, (botSt-1), (botAv+1), becker.robots.Direction.NORTH);
      
      //sides
      Wall wall5 = new Wall(starship, (botSt+1), (botAv+1), becker.robots.Direction.EAST);
      Wall wall6 = new Wall(starship, (botSt-1), (botAv+1), becker.robots.Direction.EAST);
      //bottom
      Wall wall3 = new Wall(starship, (botSt+1), (botAv+1), becker.robots.Direction.SOUTH);
      Wall wall4 = new Wall(starship, (botSt+1), (botAv-1), becker.robots.Direction.SOUTH);
      //west
      Wall wall7 = new Wall(starship, (botSt+1), (botAv-1), becker.robots.Direction.WEST);
      Wall wall8 = new Wall(starship, (botSt-1), (botAv-1), becker.robots.Direction.WEST);
      
      
      
      //Checks if there is not an exit. if there isnt then place wall 
      if(!thePlayer.getRoom().getExitString().contains("north")) 
      {
        Wall exitNorth = new Wall(starship, (botSt-1), (botAv), becker.robots.Direction.NORTH);
      }
      
      if(!thePlayer.getRoom().getExitString().contains("east")) 
      {
        Wall exitEast = new Wall(starship,(botSt), (botAv+1), becker.robots.Direction.EAST);
      }  
      
      if(!thePlayer.getRoom().getExitString().contains("south")) 
      {
        Wall exitSouth = new Wall(starship, (botSt+1), (botAv), becker.robots.Direction.SOUTH);
      }  
      
      if(!thePlayer.getRoom().getExitString().contains("west")) 
      {
        Wall exitWest = new Wall(starship, (botSt), (botAv-1), becker.robots.Direction.WEST);
      }  
      
      //draws npc's
      if (thePlayer.getRoom() == captainsQuarters)
      {
        captainBot = new NPC_Bot(starship, 9, 11, becker.robots.Direction.NORTH, gold);
      }else if (thePlayer.getRoom() == scienceLab)
      {
        scienceOfficer = new NPC_Bot(starship, 5, 15, becker.robots.Direction.WEST, 1, Color.blue);
      }else if (thePlayer.getRoom() == bridge)
      {
        badGuy = new NPC_Bot(starship, 9, 8, becker.robots.Direction.NORTH, Color.green );
      }
      
      //ensures the rooms only drawn once.
      thePlayer.getRoom().setDrawn();
    }
  }
  
  /**
   * Testing for game class
   */
  public static void main(String[] args)
  {
    Game test = new Game();
    
    test.printHelp();
    test.talk();
    test.health();
    
    
    Command testCommand = new Command(CommandWord.TURN, "right");
    test.turn(testCommand);
    
    test.scan();
    
    testCommand = new Command(CommandWord.TAKE, "hypospray");
    test.take(testCommand);
    
    testCommand =  new Command(CommandWord.USE, "hypospray");
    test.use(testCommand);
    
    test.inventory();
    
    testCommand =  new Command(CommandWord.TURN, "right");
    test.turn(testCommand);
    
    test.move();
    
    test.back();
    
    test.back();
    
    testCommand = new Command(CommandWord.TURN, "left");
    
    test.turn(testCommand);
    
    testCommand = new Command(CommandWord.DROP, "hypospray");
    
    test.drop(testCommand);
    
  }
  
}


