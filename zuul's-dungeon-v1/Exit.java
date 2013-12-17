import java.util.Set;
import java.util.TreeMap;

/**
 * Class Exit - represents an exit and its status.
 * Keeps track of what room the exit leads to,
 * whether its unlocked or not and what key
 * that can be used to unlock it.
 */
public class Exit
{
    // only for display purposes and thus works as a name.
    private String direction;
    // stores the room the exit leads to.
    private Room neighbour;
    // determines whether the exit is locked or not.
    private boolean unlocked;
    // stores what item is needed to unlock the exit.
    private Item key;

    /**
     * Create an exit with direction "direction" and
     * locked status "unlocked". "direction" is only for
     * display purposes and thus works as a name.
     * @param direction The direction (name).
     * @param neighbour The room the exit leads to.
     * @param unlocked The exit's status.
     * @param key The key that fits the lock (null if there's
     * no lock).
     */
    public Exit(String direction, Room neighbour, boolean unlocked, Item key) {
        this.direction = direction;
        this.neighbour = neighbour;
        this.unlocked = unlocked;
        this.key = key;
    }
    
    /**
     * Return the direction (used as a name) of the exit.
     * @return The direction/name of the exit.
     */
    public String getDirection() {
        return direction;
    }
    
    /**
     * Return the neighbour - the room the exit leads to.
     * @return The neighbour - the room the exit leads to.
     */
    public Room getNeighbour() {
        return neighbour;
    }
    
    /**
     * Lock the door.
     */
    public void lock() {
        unlocked = false;
    }
    
    /**
     * Unlock the door.
     */
    public void unlock() {
        unlocked = true;
    }
    
    /**
     * Return key associated with exit.
     * @return Key associated with exit.
     */
    public Item getKey() {
        return key;
    }
    
    /**
     * Return the status of the exit (unlocked
     * or locked).
     * @return True if exit is unlocked.
     */
    public boolean getUnlocked() {
        return unlocked;
    }
}
