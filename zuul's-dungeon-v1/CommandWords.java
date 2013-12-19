import java.util.TreeMap;

/**
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes, edited by Joel Hagrot.
 * @version 2008.03.30, edited 2013.12.15.
 */

public class CommandWords
{
    // a TreeMap holding all valid command words. The values holds brief descriptions of the
    // possible arguments and they're printed when the player uses the help command.
    private static final TreeMap<String, String> validCommands = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    static
    {
        validCommands.put("go", "[direction]; back");
        validCommands.put("look", "(None); [item name]; inventory");
        validCommands.put("use", "[item name] - to fight, enter: 'use [weapon name]");
        validCommands.put("take", "[item name]");
        validCommands.put("drop", "[item name]");
        validCommands.put("say", "[phrase] - e.g.: 'hi'; 'how's it going'. (Only salutations have been implemented.)");
        validCommands.put("give", "[character name] [item name]");
        validCommands.put("help", "(None)");
        validCommands.put("quit", "(None)");
    }

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords() {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString) {
        if (validCommands.containsKey(aString)) {
            return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
    
    /**
     * Print all valid commands.
     */
    public String getCommandList() {
        String commandList = "";
        for (String key : validCommands.keySet()) {
            commandList += key;
            commandList += " +: " + validCommands.get(key) + "\n";
        }
        return commandList;
    }
}