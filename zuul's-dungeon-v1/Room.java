import java.util.Set;
import java.util.TreeMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * Class Room - represents a room and keeps
 * track of the items and exits in it.
 */
public class Room 
{
    // the name of the room (only for internal purposes).
    private String name;
    // the description of the room (only for display purposes).
    private String description;
    // a map keeping track of which exits belong to the room.
    private TreeMap<String, Exit> exits;
    // a map keeping track of what items are in the room.
    private TreeMap<String, Item> items;

    /**
     * Create a room named "name" and described "description". 
     * The name is only used internally by the program. Initially,
     * it has no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param name The room's name.
     * @param description The room's description.
     */
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        exits = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        items = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }
    
    /**
     * Define an exit from this room. Also creates the exit
     * object and sets it to defined lock status.
     * @param direction The direction of the exit.
     * @param neighbour The room in the given direction.
     * @param unlocked The lock status of the exit.
     */
    public void setExit(String direction, Room neighbour, boolean unlocked, Item key) {
        Exit exit = new Exit(direction, neighbour, unlocked, key);
        exits.put(direction, exit);
    }
    
    /**
     * Place an item in this room.
     * @param item The item to be placed.
     */
    public void addItem(Item item) {
        items.put(item.getName(), item);
    }
    
    /**
     * Remove an item from this room.
     * @param name The name of the item to be removed.
     */
    public void removeItem(String name) {
        items.remove(name);
    }
    
    /**
     * Return item whose name is "name". If not found,
     * return null.
     * @param name The name to look for.
     * @return The item whose name is "name".
     */
    public Item getItem(String name) {
        return items.get(name);
    }
    
    /**
     * Return the exit in specified direction.
     * Return null if there is no exit.
     * @return The exit in specified direction.
     */
    public Exit getExit(String direction) {
        return exits.get(direction);
    }
    
    /**
     * Return all neighbours.
     * @return The neighbours as an ArrayList.
     */
    public ArrayList<Room> getNeighbours() {
        ArrayList<Room> neighbours = new ArrayList<Room>();
        for (Exit exit : exits.values()) {
            neighbours.add(exit.getNeighbour());
        }
        return neighbours;
    }
    
    /**
     * Return a string listing all exits.
     * @return A string listing all exits.
     */
    public String getExitString() {
        String exitString = "Exits: ";
        for (String key : exits.keySet()) {
            exitString += key + " ";
        }
        return exitString;
    }
    
    /**
     * Return a string listing all items.
     * @return A string listing all items.
     */
    public String getItemsString() {
        if (items.isEmpty()) {
            return "The room doesn't seem to contain anything of interest.";
        }
        String itemsString = "Items of interest: ";
        for (String key : items.keySet()) {
            itemsString += key + "; ";
        }
        itemsString = itemsString.substring(0, itemsString.length()-2) + ".";
        return itemsString;
    }
    
    /**
     * Check whether key works in current room.
     * @param key Key to compare with exits.
     * @return The room the exit leads to. Otherwise,
     * return null.
     */
    public Exit lookForExit(Item key) {
        for (Map.Entry<String, Exit> exit : exits.entrySet()) {
            if (key == exit.getValue().getKey()) {
                return exit.getValue();
            }
        }
        return null;
    }
    
    /**
     * Return the name of the room.
     * @return The name of the room.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Return the description of the room.
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Return a long description of this room, of the form:
     *      You are in the kitchen.
     *      Exits: north west
     * @return A description of the room, including exits.
     */
    public String getLongDescription() {
        return "You are " + description + "\n" + getItemsString() + "\n" + getExitString();
    }
}
