import java.util.ArrayList;

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private boolean lockpickUsed = false;
    private Room centralStaircase;
    private Room lairExit;

        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        inventory = new ArrayList<>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room cellOne, cellTwo, basementHallway, lair, boneRoom, toolStorage, coldRoom, lairExit, blueBox, centralHallway, centralStaircase, livingRoom, backPorch, bloodRoom, kitchen, diningRoom;

        // create the rooms
        cellOne = new Room("a small dimly lit cell");
        cellTwo = new Room("a small dimly lit cell, similar to the one you woke up in. There seems to be nothing here");
        basementHallway = new Room("in a long narrow hallway");
        lair = new Room("in a large room with ominous red lighting. Bodies are strung from gallows, one looking fresher than the others. To the north of the room, you see a large blue door");
        boneRoom = new Room("in a medium sized room with bones hanging from the ceiling. You can see piles of bones laying around the edges of the room. You hope they aren't human");
        toolStorage = new Room("in something that looks like a tool storage. You spot a blue toolbox on a table");
        blueBox = new Room ("in front of the blue toolbox. There seems to be a single lockpick inside of it");
        coldRoom = new Room("in a cold room, like a freezer. Bodies are strung up from the ceiling, animal and human");
        lairExit = new Room("in front of a large blue door with a light shining on it. You try to open it, but it won't budge. You're going to need to find a way to pick the lock");
        centralStaircase = new Room("in a narrow staircase. The red wall is lined with varying animal skulls");
        centralHallway = new Room("in a hallway with a locked door at the end of it. On either side, there are doors leading to other rooms of the house");
        livingRoom = new Room("in what seems like a living room, except there's chicken feathers everywhere and the furniture is adorned with human spines and skulls. At the end of a room is a large window, the sun high in the sky outside");
        backPorch = new Room("a room resembling a back porch, with large windows letting in a lot of natural light. It would almost be serene, if not for the horrible smell of rot and decay");
        bloodRoom = new Room("a dark room with buckets of what appears to be blood laying around on the floor. A large butcher table sits in the midst, but luckily nothing is on it. For now");
        kitchen = new Room("a room that almost looks like a normal kitchen. A large freezer sits across from the fridge and other appliances");
        diningRoom = new Room("in a long room with a big dining table. At the end of it, what looks like the corpse of a very old man sits with closed eyes, holding what seems to be a hammer");

        // initialise room exits
        cellOne.setExit("hallway", basementHallway);

        basementHallway.setExit("cell1", cellOne);
        basementHallway.setExit("cell2", cellTwo);
        basementHallway.setExit("lair", lair);

        cellTwo.setExit("hallway", basementHallway);

        lair.setExit("door", lairExit);
        lair.setExit("bone", boneRoom);
        lair.setExit("cold", coldRoom);
        lair.setExit("hallway", basementHallway);

        boneRoom.setExit("storage", toolStorage);
        boneRoom.setExit("lair", lair);

        toolStorage.setExit("bone", boneRoom);
        toolStorage.setExit("cold", coldRoom);
        toolStorage.setExit("toolbox", blueBox);

        blueBox.setExit("storage", toolStorage);

        coldRoom.setExit("lair", lair);
        coldRoom.setExit("storage", toolStorage);

        lairExit.setExit("lair", lair);

        centralStaircase.setExit("hallway", centralHallway);

        centralHallway.setExit("staircase", centralStaircase);
        centralHallway.setExit("livingroom", livingRoom);
        centralStaircase.setExit("diningroom", diningRoom);

        livingRoom.setExit("backporch", backPorch);
        livingRoom.setExit("hallway", centralHallway);

        diningRoom.setExit("kitchen", kitchen);
        diningRoom.setExit("hallway", centralHallway);

        kitchen.setExit("bloodroom", bloodRoom);
        kitchen.setExit("diningroom", diningRoom);

        bloodRoom.setExit("diningroom", diningRoom);
        bloodRoom.setExit("backporch", backPorch);


        currentRoom = cellOne;  // start game in cell 1

        //Add items below - Currently itemDescription doesn't fully work, but is kept for future implementation.
        cellOne.addItem("lockpick", "A small but sturdy lockpick. Can be used on doors.");
        cellOne.addItem("bonescrap", "A sharp piece of chicken bone.");

        blueBox.addItem("lockpick", "A small but sturdy lockpick. Can be used on doors.");

        //Add doors below
        lairExit.addDoor();
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
        System.out.println(currentRoom.getLongDescription());
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
        else if(commandWord.equals("look")) {
            look();
        } else if (commandWord.equals("get")) {
            takeItem(command);
        }
        else if (commandWord.equals("use")) {
            useItem(command);
        } else if (commandWord.equals("inventory")) {
            printInventory();
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
        parser.showCommands();

    }

    private void printInventory() {
        System.out.println("You check your pockets.");

        if (inventory.isEmpty()) {
            System.out.println("There's nothing in your pockets!");
        }
        else {
            for (Item item : inventory) {
                System.out.println("- " + item.getItemDescription());
            }
        }
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
            if (direction.equals("staircase") && !lockpickUsed) {
                System.out.println("The door is locked. You need to find something to unlock it with.");
            }
            else {
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());
            }

        }
    }

    //Re-prints the room description, but also checks for items.
    private void look(){
        System.out.println(currentRoom.getLongDescription());
        System.out.println();

        if (currentRoom.lookItems().isEmpty()) {
            System.out.println("There are no items in this room.");
        } else {
            System.out.println("Items in room:");
            for (Item item : currentRoom.lookItems()) {
                System.out.println("- " + item.getItemName());
            }
        }
    }

    private void takeItem(Command command){
        if (!command.hasSecondWord()) {
            System.out.println("Get what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = currentRoom.getItem(itemName);

        if (item != null) {
            inventory.add(item);
            item.pickedUp();
            currentRoom.removeItem(itemName);
            System.out.println("You picked up: " + itemName + "!");
        }
        else {
            System.out.println("There is no " + itemName + " to get!");
        }
    }

    private void useItem(Command command) {
        if (command.hasSecondWord()) {
            String itemToUse = command.getSecondWord();
            boolean foundItem = false;

            for (Item item : inventory) {
                //Check if desc of item contains text entered by player
                if (item.getItemName().toLowerCase().contains(itemToUse.toLowerCase())) {
                    if (item.getItemName().toLowerCase().contains("lockpick")) {
                        useLockpick(item);
                        return;
                    } else {
                        System.out.println("You can't use that here.");
                        return;
                    }
                } else {
                    foundItem = true;
                }
            }

            if (foundItem) {
                System.out.println("You have a " + itemToUse + ", but it cannot be used here");
            } else {
                System.out.println("You don't have that item in your inventory.");
            }
        } else {
            System.out.println("Use what?");
        }
    }


    //Find a way to make this work in tandem with the createRooms method. Currently unable to unlock anything with it.us

    private void useLockpick(Item item) {
        Door door = currentRoom.getDoor();

        if (currentRoom.equals(lairExit)) {
            if (door != null) {
                if (door.isLocked()) {
                    door.unlock();
                    inventory.remove(item); // Remove the lockpick from the inventory after use
                    System.out.println("You used the lockpick to unlock the lair door.");
                    currentRoom = currentRoom.getExit("door"); // Move to the room behind the door
                    currentRoom.setExit("staircase", centralStaircase); // Add new exit to the central staircase
                    System.out.println(currentRoom.getLongDescription());
                    return;
                } else {
                    System.out.println("The lair door is already unlocked.");
                    return;
                }
            } else {
                System.out.println("There is no door in this room.");
            }
        } else {
            System.out.println("You can't use the lockpick here.");
        }
    }




    /** he
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
