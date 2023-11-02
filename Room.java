import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;



    private Door door;


    public Room(String description)
    {
        this.setDescription(description);
        exits = new HashMap<>();
        items = new ArrayList<>();
        this.door = new Door();
    }

    public Door getDoor() {
        return this.door;
    }

    public void addDoor() {
        this.door = new Door();
        this.door.lock();
    }


    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     */
    public void setExit(String direction, Room neighbour)
    {
        exits.put(direction, neighbour);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }


    public String getDescription()
    {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExitString() {
        StringBuilder returnString = new StringBuilder("Exits:");
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString.append(" ").append(exit);
        }
        return returnString.toString();
    }

    public String getLongDescription(){
        return "You are " + description + ".\n" + getExitString();
    }


    public void addItem(String itemName, String itemDescription) {
        items.add(new Item(itemName, itemDescription));
    }

    public Item removeItem(String itemName) {
        Item foundItem = null;
        for (Item item : items) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                foundItem = item;
                break;
            }
        }

        if (foundItem != null) {
            items.remove(foundItem);
        }

        return foundItem;
    }



    public Item getItem(String itemName) {
        for (Item item : items) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }


    public ArrayList<Item> lookItems() {
        return items;
    }




}
