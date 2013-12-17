import java.util.TreeSet;

/**
 * Class Dictionary - keeps track of which
 * words belong to which word group. 
 * Example: This allows the player to say
 * "hi" or "hello" and get the same response,
 * since both words belong to "salutations".
 * Each character has its own map linking a 
 * word group to its own responses.
 */

public class Dictionary
{
    // word group "salutations", with all words
    // belonging to it.
    private static final TreeSet<String> salutations = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
    static
    {
        salutations.add("hi");
        salutations.add("hello");
        salutations.add("hey");
        salutations.add("good day");
        salutations.add("yo");
        salutations.add("hows it going");
        salutations.add("how's it going");
        salutations.add("what's up");
        salutations.add("whats up");
    }
    
    /**
     * Return all words in the salutations 
     * word group.
     * @return The words in the salutations
     * word group.
     */
    public TreeSet<String> getSalutations() {
        return salutations;
    }
    
    /**
     * Return all valid words.
     * @return All valid words.
     */
    public TreeSet<String> getAll() {
        TreeSet<String> all = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        all.addAll(salutations);
        return all;
    }
}