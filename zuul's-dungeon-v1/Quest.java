import java.util.ArrayList;

/**
 * Class Quest - represents a quest.
 */
public class Quest
{
    // the name of the quest (only for internal purposes).
    private String name;
    // a list with the boolean value describing whether the
    // part is completed. Index 1 is for part 1 and so on.
    private ArrayList<Boolean> parts;
    
    /**
     * Create a quest with name "name".
     * @param name Name of the quest (only for internal purposes).
     */
    public Quest(String name) {
        this.name = name;
        parts = new ArrayList<Boolean>();
        // the first index is for the total quest.
        parts.add(false);
    }
    
    /**
     * Adds a part to the quest.
     */
    public void addPart() {
        parts.add(false);
    }
    
    /**
     * Return the status of the part.
     * @param number The number of the part to return (index 0 for total quest).
     * @return The status of the part (finished or not).
     */
    public boolean getPart(int number) {
        return parts.get(number);
    }
    
    /**
     * Set the part with number "number" to completed.
     * @param number The number of the part.
     */
    public void completedPart(int number) {
        parts.set(number, true);
    }
}
