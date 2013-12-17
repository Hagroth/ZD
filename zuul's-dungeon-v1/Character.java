import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Class Character - represents a character.
 * Keeps track of all parameters as well as
 * dialogue, inventory and location history
 * (used to keep track of where the character
 * is and to backtrack - the topmost Room in
 * this stack is the character's current 
 * location).
 */
public class Character
{
    // the name of the character, used for both internal
    // and display purposes.
    private String name;
    // a stack keeping track of the character's history,
    // enables backtracking when using the "go back" command.
    // Top room in the stack is the current location.
    private Stack<Room> locationHistory;
    // a map keeping track of what items belong to the
    // character's inventory.
    private TreeMap<String, Item> inventory;
    // the character's inventory's capacity.
    private double capacity;
    // the initial damage the character is capable of
    // inflicting.
    private int damage;
    // the initial health points of the character.
    private int health;
    // contains one response to every wordgroup.
    // wordgroups: "greetings", "salutations", "pardon".
    private TreeMap<String, String> dialogue;
    // determines whether the character may move around.
    private boolean moveable;

    /**
     * Create a character with defined name, capacity,
     * damage and health and default (empty) locationHistory,
     * inventory and dialogue (a TreeMap mapping a word to a
     * response).
     * @param name The character's name.
     * @param capacity The character's carrying capacity.
     * @param damage The character's initial damage.
     * @param health The character's initial health.
     * @param moveable The character's moveable status.
     */
    public Character(String name, double capacity, int damage, int health, boolean moveable) {
        this.name = name;
        this.capacity = capacity;
        this.damage = damage;
        this.health = health;
        this.moveable = moveable;
        locationHistory = new Stack<Room>();
        inventory = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        dialogue = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }
    
    /**
     * Add new dialogue options for the character. "dialogue" is
     * a TreeMap mapping a wordgroup to a response. Case insensitive.
     * @param wordGroup The type of action to reply to.
     * @param response The response.
     */
    public void addDialogue(String wordGroup, String response) {
        dialogue.put(wordGroup, response);
    }
    
    /**
     * Remove dialogue options for the character. "dialogue" is
     * a TreeMap mapping a wordgroup to a response. Case insensitive.
     * @param wordGroup The type of action whose mapping is to be removed.
     */
    public void removeDialogue(String wordGroup) {
        dialogue.remove(wordGroup);
    }
    
    /**
     * Return the response for the wordgroup.
     * @param wordGroup The wordgroup to respond to.
     * @return The response.
     */
    public String getResponse(String wordGroup) {
        return dialogue.get(wordGroup);
    }
    
    /**
     * Add a location to the stack.
     */
    public void addLocationHistory(Room room) {
        locationHistory.push(room);
    }
    
    /**
     * Remove the current location from the
     * stack and return the previous.
     * @return The previous location.
     */
    public Room subtractLocationHistory() {
        return locationHistory.pop();
    }
    
    /**
     * Clear character's location history.
     */
    public void clearLocationHistory() {
        locationHistory.clear();
    }
    
    /**
     * Clear character's inventory.
     */
    public void clearInventory() {
        inventory.clear();
    }
    
    /**
     * Adds item "item" to the character's
     * inventory.
     * @param item Item to be added.
     */
    public void addItem(Item item) {
        inventory.put(item.getName(), item);
    }
    
    /**
     * Remove item "item" from the character's
     * inventory.
     * @param item Item to be removed.
     */
    public void removeItem(Item item) {
        inventory.remove(item.getName());
    }
    
    /**
     * Return item "item" if found in character's
     * inventory. Return null if not found.
     * @param name Name of item to look for.
     * @return The item, if found.
     */
    public Item getItem(String name) {
        return inventory.get(name);
    }
    
    /**
     * Return the current room.
     * @return The current room.
     */
    public Room getLocation() {
        return locationHistory.peek();
    }
    
    /**
     * Return the name of the character.
     * @return The name of the character.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Return the damage of the character.
     * @return The damage of the character.
     */
    public int getDamage(){
        return damage;
    }
    
    /**
     * Return the health of the character.
     * @return The health of the character.
     */
    public double getHealth(){
        return health;
    }
    
    /**
     * Return the capacity of the character.
     * @return The capacity of the character.
     */
    public double getCapacity() {
        return capacity;
    }
    
    /**
     * Return the moveable status.
     * @return The moveable status.
     */
    public boolean getMoveable() {
        return moveable;
    }
    
    /**
     * Set the moveable status.
     * @param The moveable status.
     */
    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }
    
    /**
     * Return the total weight of the inventory.
     * @return The total weight of the inventory.
     */
    public double getWeight() {
        double weight = 0;
        for (Item item : inventory.values()) {
            weight += item.getWeight();
        }
        return weight;
    }
    
    /**
     * Print the character's inventory.
     */
    public void printInventory() {
        System.out.println("Your inventory consists of: ");
        for (String name : inventory.keySet()) {
            System.out.println("- " + name);
        }
        double freeCapacity = getCapacity() - getWeight();
        System.out.println("It currently weighs " + getWeight() + " kg. You can carry " + freeCapacity + " more kilograms.\n");
    }
}