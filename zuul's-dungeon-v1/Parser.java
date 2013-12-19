import java.util.Scanner;
import java.util.StringTokenizer;

/** 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Michael Kolling and David J. Barnes, edited by Joel Hagrot
 * @version 2008.03.30, edited 2013.12.15
 */
public class Parser
{
    // holds all valid command words
    private CommandWords commands;
    // source of command input
    private Scanner reader;

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }
    
    public String getInput() {
        return reader.nextLine();
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() 
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next().toLowerCase();
            if(tokenizer.hasNext()) {
                word2 = inputLine.substring(word1.length() + 1).toLowerCase();
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, or if the player entered "" (null), create a
        // "null" command (for unknown command).
        if (word1 == null || !commands.isCommand(word1)) {
            return new Command(null, word2);
        }
        return new Command(word1, word2);
    }
    
    public String showCommands() {
        return commands.getCommandList();
    }
}
