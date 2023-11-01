import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;
    private Door door;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description)
    {
        this.setDescription(description);
        exits = new HashMap<>();
        items = new ArrayList<>();
        this.door = new Door();
    }

    public void addDoor(){
        this.door = new Door();
    }

    public Door getDoor() {
        return this.door;
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


    public void addItem(String itemDescription, String inventoryDescription) {
        items.add(new Item(itemDescription, inventoryDescription));
    }

    public Item removeItem(Item item) {
        if (!items.isEmpty()) {
            return items.remove(items.size() - 1);
        }
        else {
            return null;
        }
    }

    public Room getRoom(String direction) {
        return exits.get(direction);
    }

    public Item getItem(String itemName) {
        for (Item item : items) {
            if (item.getItemDescription().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

}
