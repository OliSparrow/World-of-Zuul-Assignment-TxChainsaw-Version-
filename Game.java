/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room cellOne, cellTwo, basementHallway, lair, boneRoom, toolStorage, coldRoom, lairDoor, centralHallway, centralStaircase, livingRoom, backPorch, bloodRoom, kitchen, diningRoom;
      
        // create the rooms
        cellOne = new Room("a small dimly lit cell.");
        cellTwo = new Room("a small dimly lit cell, similar to the one you woke up in. There seems to be nothing here.");
        basementHallway = new Room("in a long narrow hallway.");
        lair = new Room("in a large room with ominous red lighting. Bodies are strung from gallows, one looking fresher than the others. To the north of the room, you see a large blue door.");
        boneRoom = new Room("in a medium sized room with bones hanging from the ceiling. You can see piles of bones laying around the edges of the room. You hope they aren't human.");
        toolStorage = new Room("in something that looks like a tool storage. You spot a blue toolbox on a table.");
        coldRoom = new Room("in a cold room, like a freezer. Bodies are strung up from the ceiling, animal and human.");
        lairDoor = new Room("in front of a large blue door with a light shining on it. You try to open it, but it won't budge. You're going to need to find a way to pick the lock.");
        centralStaircase = new Room("in a narrow staircase. The red wall is lined with varying animal skulls.");
        centralHallway = new Room("in a hallway with a locked door at the end of it. On either side, there are doors leading to other rooms of the house.");
        livingRoom = new Room("in what seems like a living room, except there's chicken feathers everywhere and the furniture is adorned with human spines and skulls. At the end of a room is a large window, the sun high in the sky outside.");
        backPorch = new Room("a room resembling a back porch, with large windows letting in a lot of natural light. It would almost be serene, if not for the horrible smell of rot and decay.");
        bloodRoom = new Room("a dark room with buckets of what appears to be blood laying around on the floor. A large butcher table sits in the midst, but luckily nothing is on it. For now.");
        kitchen = new Room("a room that almost looks like a normal kitchen. A large freezer sits across from the fridge and other appliances.");
        diningRoom = new Room("in a long room with a big dining table. At the end of it, what looks like the corpse of a very old man sits with closed eyes, holding what seems to be a hammer. Wait, did it just move?");

        // initialise room exits
        cellOne.setExit("hallway", basementHallway);

        basementHallway.setExit("cell one", cellOne);
        basementHallway.setExit("cell two", cellTwo);
        basementHallway.setExit("lair", lair);

        cellTwo.setExit("hallway", basementHallway);

        lair.setExit("door", lairDoor);
        lair.setExit("bone room", boneRoom);
        lair.setExit("cold room", coldRoom);
        lair.setExit("hallway", basementHallway);

        boneRoom.setExit("storage", toolStorage);
        boneRoom.setExit("lair", lair);

        toolStorage.setExit("bone room", boneRoom);
        toolStorage.setExit("cold room", coldRoom);

        coldRoom.setExit("lair", lair);
        coldRoom.setExit("storage", toolStorage);

        lairDoor.setExit("lair", lair);

        currentRoom = cellOne;  // start game in cell 1
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void startGame()
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("You wake up in a small, poorly lit room. You are alone.");
        System.out.println("In the distance, you hear the sounds of a chainsaw revving");
        System.out.println("coupled with the screams of an unknown person.");
        System.out.println("You don't know where you are. All you know is you need to escape.");
        System.out.println();
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
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
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around the dark and grim basement.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit look help");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    private void printLocationInfo() {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print(currentRoom.getExitString());
        System.out.println();
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
