import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Random;
import java.util.ArrayList;

/**
 *  This class is the main class of the "Zuul's Dungeon" application. 
 *  "Zuul's Dungeon" is a very simple, text based adventure game. As of
 *  now, there is no way to complete it or any goals, and only one quest
 *  which is very simple and short.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Joel Hagrot
 * @version 2013.12.15
 */

public class Game 
{
    // a parser for processing user input.
    private Parser parser;
    // a dictionary for keeping track of valid "say" arguments
    // and mapping them to the correct wordGroup and thus response.
    private Dictionary dictionary;
    // a map for keeping track of all characters except the player.
    private TreeMap<String, Character> characters;
    // a map for keeping track of all rooms.
    private TreeMap<String, Room> rooms;
    // a map for keeping track of all items.
    private TreeMap<String, Item> items;
    // a map for keeping track of all quests.
    private TreeMap<String, Quest> quests;
    // the player's Character instance.
    private Character player;
    // the random instance.
    private Random random;
    
    /**
     * Main method for running the game as a jar file.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        parser = new Parser();
        dictionary = new Dictionary();
        characters = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        rooms = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        items = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        quests = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        random = new Random();
        play();
    }

    /**
     * Initialize the game.
     */
    private void initializeGame() {
        // initialize quests
        initializeQuest("quest1", 2);
        
        // create the rooms
        // intro
        Room bedroom = new Room("bedroom", "in your bedroom on the second floor of the house. The night is gloomy and the faint brightness from the street lanterns outside cast eerie shadows on the walls, while the wind produces startling sounds. The pitter-patter of the rain puts you in a mood of desolation.");
        Room hallwayHouse = new Room("hallwayHouse", "in the hallway at the top of the stairs. The railing, littered with ornaments, leads down into complete darkness, contrasting the light from the candle on the wall.");
        Room study = new Room("study", "in the study upstairs. The bookcases are crammed with carefully sorted literature.");
        Room stairway = new Room("stairway", "in the stairs. You try to sneak but the age of the stairs is evident.");
        Room antechamber = new Room("antechamber", "in the antechamber. Your eyes start to adjust to the darkness slowly. There is a small bit of light from the keyhole in the door and from the windows in the kitchen and living room.");
        Room kitchen = new Room("kitchen", "in the kitchen. The rain lies heavy on the windows.");
        // dungeon
        Room cell1 = new Room("cell1", "in the prison cell your kidnappers dumped you in. The warm light from the torches in the hallway shines through the cold iron bars and everything's silent except for the dripping sound of the occasional drop of water from the moist ceiling.");
        Room hallwayA1 = new Room("hallwayA1", "in the hallway outside the prison cell you woke up in. There's another cell to the left, open and devoid of any traces of life.");
        Room hallwayA2 = new Room("hallwayA2", "in the hallway south of the prison cell you woke up in. There's another cell on your left side along the hallway to the east.");
        Room cell2 = new Room("cell2", "in the cell north of the prison cell you woke up in. Whoever might have resided in here must have left ages ago.");
        Room hallwayA3 = new Room("hallwayA3", "in the hallway around the corner from your old cell. There's another cell to the north.");
        Room cell3 = new Room("cell3", "in the cell the old man resided in. You feel pretty confounded at the fact the scrawny man didn't seem to have anything, not even food or water...");
        Room hallwayA4 = new Room("hallwayA4", "at the bottom of a narrow spiral staircase made from stone, leading clockwise upwards. Torches are put at regular intervals, illuminating the moist rock.");
        Room stairsAB = new Room("stairsAB", "in the middle of the spiral staircase, leading clockwise upwards.");
        Room hallwayB1 = new Room("hallwayB1", "at the top of a narrow spiral staircase made from stone, leading clockwise upwards. Torches are put at regular intervals, illuminating the moist rock. There's a door with a small window with bars to the north.");
        // rooms that the program needs to be able to access outside of this block are also put in the "rooms" TreeMap.
        rooms.put(study.getName(), study);
        rooms.put(cell1.getName(), cell1);
        rooms.put(hallwayA1.getName(), hallwayA1);
        rooms.put(cell2.getName(), cell2);
        rooms.put(hallwayA2.getName(), hallwayA2);
        rooms.put(hallwayA3.getName(), hallwayA3);
        rooms.put(cell3.getName(), cell3);
        rooms.put(hallwayA4.getName(), hallwayA4);
        rooms.put(stairsAB.getName(), stairsAB);
        rooms.put(hallwayB1.getName(), hallwayB1);
        
        // create characters.
        initializeCharacter("Hrangst Jaltibrond", 50, 30, 200, false, cell3);
        
        // create dialogue for the characters.
        characters.get("Hrangst Jaltibrond").addDialogue("pardon", "Pardon?");
        characters.get("Hrangst Jaltibrond").addDialogue("greetingsNear", "A scrawny, bearded old figure inhabits the cell to the north.\nHrangst Jaltibrond: Lone wanderer... I beg of you, help me, please.");
        characters.get("Hrangst Jaltibrond").addDialogue("greetingsSame", "Hrangst Jaltibrond: Please...");
        characters.get("Hrangst Jaltibrond").addDialogue("salutations", "Thank the Gods you arrived. I am a mere mortal in this horrible place. Could you please help me get out of here? I heard you using the key over there...");
        
        // initialize items
        initializeItem("Bread loaf", "A bread loaf in surprisingly good condition.", 0.2, "none", 0, true, "miscellaneous", "You pull out the bread loaf and it does look tasty, but you don't feel that hungry yet.", true, "cell1");
        initializeItem("Sheath knife", "A sharp sheath knife made of steel.", 0.2, "Damage", 20, true, "weapon", "You swing the knife.", false, "study");
        initializeItem("Note from kidnappers", "A rather neatly put together note, apparently written by whoever kidnapped you.", 0.1, "none", 0, true, "miscellaneous", "It reads:\n\"" + player.getName() + ",\nYour plans are over. I know of your schemes, your plot to overthrow the order using Dagon's heart.\nYou will not succeed. I am a mage of great power, indeed I once trapped the emperor himself and have now returned\nmore powerful than ever. You are trapped in my dimension just like my father, Jagar Tharn, trapped the emperor all those centuries ago.\nBut I am a person of sports, and will allow you a chance to redeem yourself. Of course your plot is forever lost, but\nyou may yet survive and leave this place. Prove your worthiness in my dungeon!\nZuul Tharn\"", false, "cell1");
        initializeItem("Crude key", "A very crude looking key made from dark and rusty iron.", 0.05, "none", 0, true, "key", "You pull the key out of your bag, it feels rather fragile and light in your palm.", true, "cell2");
        initializeItem("Sturdy key", "A sturdy key made from cast iron.", 0.1, "none", 0, true, "key", "You pull the key out of your bag, it feels rather cold and heavy.", true, "Hrangst Jaltibrond");
        initializeItem("Rusty sword", "A rusty old sword, dull to the point any death caused by it must be extremely painful.", 5, "Damage", 30, true, "weapon", "You swing the sword.", false, "hallwayB1");
        
        // initialize room exits.
        // intro
        bedroom.setExit("south", hallwayHouse, true, null);
        hallwayHouse.setExit("north", bedroom, true, null);
        hallwayHouse.setExit("east", study, true, null);
        hallwayHouse.setExit("down", stairway, true, null);
        study.setExit("west", hallwayHouse, true, null);
        stairway.setExit("up", hallwayHouse, true, null);
        stairway.setExit("down", antechamber, true, null);
        antechamber.setExit("east", kitchen, true, null);
        antechamber.setExit("up", stairway, true, null);
        antechamber.setExit("west", null, true, null);
        kitchen.setExit("west", antechamber, true, null);
        // maze
        cell1.setExit("east", hallwayA1, true, null);
        hallwayA1.setExit("north", cell2, true, null);
        hallwayA1.setExit("west", cell1, true, null);
        hallwayA1.setExit("south", hallwayA2, false, items.get("Crude key"));
        cell2.setExit("south", hallwayA1, true, null);
        hallwayA2.setExit("north", hallwayA1, true, null);
        hallwayA2.setExit("east", hallwayA3, true, null);
        hallwayA3.setExit("west", hallwayA2, true, null);
        hallwayA3.setExit("north", cell3, false, items.get("Crude key"));
        cell3.setExit("south", hallwayA3, true, null);
        hallwayA3.setExit("east", hallwayA4, false, items.get("Sturdy key"));
        hallwayA4.setExit("west", hallwayA3, true, null);
        hallwayA4.setExit("up", stairsAB, true, null);
        stairsAB.setExit("down", hallwayA4, true, null);
        stairsAB.setExit("up", hallwayB1, true, null);
        hallwayB1.setExit("down", stairsAB, true, null);
        
        player.addLocationHistory(bedroom);  // start game in bedroom
    }
    
    /**
     * Initialize item.
     * @param description The item's description.
     * @param weight The item's weight.
     * @param parameterType The item's parameter's type.
     * @param parameter The item's parameter.
     * @param takeable The item's "takeable" status.
     * @param type The item's type.
     * @param use Description of what happens when item is used.
     * @param questItem Determines whether item is flagged as "quest item".
     * or not.
     * @param place Where to place the item, null if not to be placed. The
     * name of either a character or room.
     */
    public void initializeItem(String name, String description, double weight, String parameterType, int parameter, boolean takeable, String type, String use, boolean questItem, String place) {
        Item item = new Item(name, description, weight, parameterType, parameter, takeable, type, use, questItem);
        items.put(item.getName(), item);
        if (rooms.containsKey(place)) {
            rooms.get(place).addItem(item);
        } else if (characters.containsKey(place)) {
            characters.get(place).addItem(item);
        }
    }
    
    /**
     * Initialize quest with a defined number of parts.
     * @param name Name of the quest.
     * @param number Number of parts.
     */
    public void initializeQuest(String name, int number) {
        Quest quest = new Quest(name);
        quests.put(name, quest);
        for (int i = 0; i <= number; i++) {
            quest.addPart();
        }
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() {
        // initialize player and game.
        System.out.println("What is your name?");
        String name = parser.getInput();
        player = new Character(name, 50, 10, 100, true);
        initializeGame();
        
        printWelcome();
        
        System.out.println("You wake up from a nightmare in the noble rented house in the diplomat " +
                           "district of Skingrad, Cyrodiil. Something chased you, but it was " +
                           "but a shadow of unknown shape. Leaving you with an unusual feeling " +
                           "of unease, you can't close your eyes and go to sleep again.\n");
                           
        printLocationInfo();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to Zuul's Dungeon!");
        System.out.println("Zuul's Dungeon is an adventure game set");
        System.out.println("in the world of The Elder Scrolls.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
    }
    
    /**
     * Create character, put it into the TreeMap and put
     * it into its initial room.
     * @param name The character's name.
     * @param capacity The character's carrying capacity.
     * @param damage The character's initial damage.
     * @param health The character's initial health.
     * @param moveable The character's moveable status.
     * @param room The character's initial room.
     */
    private void initializeCharacter(String name, double capacity, int damage, int health, boolean moveable, Room room) {
        Character character = new Character(name, capacity, damage, health, moveable);
        characters.put(name, character);
        character.addLocationHistory(room);
    }
    
    /**
     * Print the info relevant to the player's current location.
     */
    private void printLocationInfo() {
        String presentCharacters = "Present characters: ";
        boolean found = false;
        for (Character character : characters.values()) {
            if (character.getLocation() == player.getLocation()) {
                presentCharacters += character.getName() + "; ";
                found = true;
            }
        }
        if (!found) {
            presentCharacters = "There doesn't seem to be anybody else here.";
        } else {
            presentCharacters = presentCharacters.substring(0, presentCharacters.length() - 2) + ".";
        }
        System.out.println("You are " + player.getLocation().getDescription() + "\n" + player.getLocation().getItemsString() + "\n" + presentCharacters + "\n" + player.getLocation().getExitString());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("This command is not valid. Type 'help' for a list of valid commands.");
            return false;
        }
        
        moveCharacters();
        
        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("look")) {
            look(command);
        } else if (commandWord.equals("use")) {
            attemptUse(command);
        } else if (commandWord.equals("take")) {
            take(command);
        } else if (commandWord.equals("give")) {
            give(command);
        } else if (commandWord.equals("say")) {
            say(command);
        } else if (commandWord.equals("drop")) {
            drop(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }
    
    /**
     * Loops through all characters and, based upon randomness, 
     * moves the character into an adjoining room if the character
     * doesn't have to remain in its current room due to quests.
     */
    public void moveCharacters() {
        for (Character character : characters.values()) {
            if (character.getMoveable() && random.nextInt(2) == 1) {
                ArrayList<Room> neighboursList = character.getLocation().getNeighbours();
                Room randomNeighbour = neighboursList.get(random.nextInt(neighboursList.size()));
                character.addLocationHistory(randomNeighbour);
            }
        }
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     */
    private void printHelp() {
        System.out.println("Zuul's Dungeon features a few possible actions: You can go between rooms; Look around the room to get the description again, look at items to get their properties (they must be either in the room or in your inventory) or look at your inventory to see what you're carrying around; Use items to e.g. unlock doors or read their contents; Take items to pick them up and put them in your inventory; Drop items to drop them on the floor; Say e.g. 'hi' to talk to characters; Give items to other characters.\nThere is one short quest at the moment and a few items and rooms, but no actual end as of now. The one character in the game will move around randomly from room to room once the quest is over. Characters recognize when you are nearby and will greet you.");
        System.out.println("Your command words, with their arguments, are:");
        System.out.println(parser.showCommands());
    }

    /** 
     * Try to go in a direction. If there is an exit and it's unlocked,
     * enter the new room, otherwise print a message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        
        String direction = command.getSecondWord();
        if (direction.equals("back")) {
            player.subtractLocationHistory();
            printLocationInfo();
            lookForNPCAction();
            return;
        }
        
        // Special cases.
        
        if (player.getLocation().getName().equals("antechamber") && direction.equals("west")) {
            System.out.println("Carefully sneaking into the living room, you hear something running violently towards you from behind. You don't have enough time to turn your head around before you feel a heavy blow to the back of your head and everything goes black!");
            player.clearLocationHistory();
            player.clearInventory();
            System.out.println("You wake up freezing on the wet stone floor of an ancient prison cell, your head resting in a small pool of blood. For some reason whoever guards this place left the door open. Perhaps the note lying next to you might reveal something.\n");
            player.addLocationHistory(rooms.get("cell1"));
            printLocationInfo();
            return;
        }
        
        // Regular operation.
        
        // Try to leave current room through chosen exit.
        Exit chosenExit = player.getLocation().getExit(direction);
        if (chosenExit == null) {
            System.out.println("There's no room in that direction.\n");
            return;
        }
        Room nextRoom = chosenExit.getNeighbour();
        if (!chosenExit.getUnlocked()) {
            System.out.println("The door is locked.\n");
            return;
        }
        player.addLocationHistory(nextRoom);
        printLocationInfo();
        lookForNPCAction();
    }
    
    /**
     * If an NPC is nearby, let it interact.
     */
    private void lookForNPCAction() {
        Character nearbyCharacter = checkWhoIsNear();
        if (nearbyCharacter == null) {
            System.out.println();
            return;
        }
        if (nearbyCharacter.getLocation() == player.getLocation()) {
            System.out.println(nearbyCharacter.getResponse("greetingsSame") + "\n");
        } else {
            System.out.println(nearbyCharacter.getResponse("greetingsNear") + "\n");
        }
    }
    
    /** 
     * Try to take an object. If the object exists in the
     * current location and the player has enough capacity,
     * take the object, otherwise print an error message.
     * @param command Command to process.
     */
    private void take(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?\n");
            return;
        }
        String name = command.getSecondWord();
        Item item = player.getLocation().getItem(name);
        double freeCapacity = player.getCapacity() - player.getWeight();
        if (item == null) {
            System.out.println("There is no such item in this room.");
        } else if (freeCapacity >= item.getWeight()) {
            System.out.println("You picked up the " + name + ".");
            player.addItem(item);
            player.getLocation().removeItem(name);
        } else {
            double neededCapacity = item.getWeight() - freeCapacity;
            System.out.println("The " + name + " is too heavy! You would need to get rid of " + neededCapacity + " kilograms.");
        }
        System.out.println();
    }
    
    /** 
     * Try to give an object to a character if the object 
     * exists in the player's inventory and the receiving
     * character is in the same room. Otherwise print
     * an error message.
     * @param command Command to process.
     */
    private void give(Command command) {
        // check whether command has argument.
        if (!command.hasSecondWord()) {
            System.out.println("Give whom?\n");
            return;
        }
        // identify the name part in the rest and find receiver.
        String rest = command.getSecondWord();
        if (checkWhoIsNear() == null) {
            System.out.println("There doesn't seem to be any such person in here.\n");
            return;
        }
        if (!rest.toLowerCase().contains(checkWhoIsNear().getName().toLowerCase())) {
            System.out.println("There doesn't seem to be any such person in here.\n");
            return;
        }
        Character receiver = checkWhoIsNear();
        String receiverName = receiver.getName();
        // identify the item part of the command and find the item.
        String itemName = rest.substring(receiverName.length() + 1);
        if (items.get(itemName) == null) {
            System.out.println("Give what?\n");
            return;
        }
        Item item = player.getItem(itemName);
        // check that item is in inventory and then that it's not flagged as quest item.
        if (item == null) {
            System.out.println("There is no such item in your inventory.\n");
            return;
        }

        // checks whether any quest has progressed (and that it hasn't already done so previously).
        if (receiverName.equals("Hrangst Jaltibrond") && item.getName() == "Bread loaf" && !quests.get("quest1").getPart(1)) {
            quests.get("quest1").completedPart(1);
            if (!quests.get("quest1").getPart(2)) {
                System.out.println(receiverName + ": Thank you, I am absolutely starving. Could you please get me out of here?\n");
                receiver.addDialogue("salutations", "Hello, friend. Could you please get me out of here?");
            } else {
                System.out.println(receiverName + ": Thank you so much for all your help, kind soul. I won't bother you anymore. Here, take this key so you may continue your journey. I wish you the best of fortune. Now, I must go.\nThe old man didn't smile, but as he walked away, you could see the gratefulness in his eyes. You wonder how such a scrawny figure might fare in such a place, but before you had a time to reply he was gone.\n");
                receiver.removeItem(items.get("sturdy key"));
                player.addItem(items.get("sturdy key"));
                receiver.removeDialogue("salutations");
                receiver.addDialogue("salutations", "Hello, friend. I appreciate all your help.");
                receiver.removeDialogue("greetingsNear");
                receiver.addDialogue("greetingsNear", "You hear the old man moving around in the adjoining room.");
                receiver.removeDialogue("greetingsSame");
                receiver.addDialogue("greetingsSame", "The scrawny old figure you rescued earlier seems to wander around aimlessly. His ambitions seem a great mystery. Why is he not trying to escape this place?");
                receiver.addLocationHistory(rooms.get("hallwayB1"));
                receiver.setMoveable(true);
            }
        } else if (item.getQuestItem()) {
            System.out.println("You figure you might want to hold on to this.\n");
            return;
        }
        // gives the item to the character and removes it from the player.
        receiver.addItem(item);
        player.removeItem(item);
        return;
    }
    
    /** 
     * Engage in conversation, if a character is in the same or
     * nearby room. Otherwise print an error message.
     * @param command Command to process.
     */
    private void say(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Say what?\n");
            return;
        }

        String phrase = command.getSecondWord();
        
        // check who's in a nearby room.
        Character nearbyCharacter = checkWhoIsNear();
        if (nearbyCharacter == null) {
            System.out.println("This place seems devoid of the slightest spirit. Though it is of a mind haunting nature, one must remain sane and not attempt conversation with the void.\n");
            return;
        }
        
        // check if the phrase is understood by the character.
        TreeSet<String> all = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        all = dictionary.getAll();
        if (!all.contains(phrase)) {
            System.out.println(nearbyCharacter.getName() + ": " + nearbyCharacter.getResponse("pardon") + "\n");
            return;
        }
        
        // let the character respond.
        TreeSet<String> salutations = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        salutations = dictionary.getSalutations();
        if (salutations.contains(phrase)) {
            System.out.println(nearbyCharacter.getName() + ": " + nearbyCharacter.getResponse("salutations") + "\n");
        }
    }
    
    /**
     * Check every nearby room for characters.
     */
    public Character checkWhoIsNear() {
        for (Character character : characters.values()) {
            if (character.getLocation() == player.getLocation()) {
                return character;
            }
            for (Room neighbour : player.getLocation().getNeighbours()) {
                if (character.getLocation() == neighbour) {
                    return character;
                }
            }
        }
        return null;
    }
    
    /** 
     * Try to drop an item. If the item exists in the
     * player's inventory, drop the item. Otherwise print
     * an error message.
     * @param command Command to process.
     */
    private void drop(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?\n");
            return;
        }

        String name = command.getSecondWord();
        Item item = player.getItem(name);
        if (item == null) {
            System.out.println("There is no such item in your inventory.\n");
            return;
        }
        player.removeItem(item);
        player.getLocation().addItem(item);
        System.out.println("You have dropped the " + item.getName() + ".\n");
    }
    
    /**
     * Attempt to use the object. If the object isn't
     * takeable, it must be in the same room (using 
     * non-takeable objects hasn't been implemented
     * yet). If the object is takeable, it must be
     * in the player's inventory. Otherwise print 
     * an error message.
     * @param command
     */
    private void attemptUse(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Use what?\n");
            return;
        }

        String name = command.getSecondWord();
        // look for item in player's inventory and use it if found (only takeable items)
        Item item = player.getItem(name);
        if (items.get(name) == null) {
            System.out.println("Use what?\n");
            return;
        }
        if (items.get(name).getTakeable()){
            if (item == null) {
                System.out.println("There is no such item in your inventory.\n");
                return;
            }
            use(item);
            return;
        }
        // look for item in the room and use it if found (only non-takeable items)
        item = player.getLocation().getItem(name);
        if (item == null) {
            System.out.println("There is no such item here.\n");
            return;
        }
        use(item);
        return;
    }
    
    /**
     * Use the item. If the item is of type "advantage"
     * (and thus expendable) and flagged as quest item,
     * print an error message. Different item types
     * are used differently.
     * @param item The item to use.
     */
    private void use(Item item) {
        System.out.println(item.printUse());
        if (item.getType().equals("miscellaneous")) {
            System.out.println();
            return;
        }
        if (item.getType().equals("weapon")) {
            // future feature.
            return;
        }
        if (item.getType().equals("advantage")) {
            // future feature.
            if (item.getType().equals("quest")) {
                System.out.println("You figure you might want to hold on to this.\n");
            }
            return;
        }
        if (item.getType().equals("key")) {
            // Check if key is connected to any exit in current room.
            Exit exit = player.getLocation().lookForExit(item);
            if (exit == null) {
                System.out.println("After checking every door in this room, you realize, with disappointment, that " + item.getName() + " won't fit in any of them.\n");
                return;
            }
            System.out.println(item.getName() + " seems to work in the door leading " + exit.getDirection() + " from here! You unlocked the door.\n");
            exit.unlock();
            // Check whether the action is relevant to any quest.
            if (player.getLocation().getName().equals("hallwayA3") && exit.getDirection().equals("north") && !quests.get("quest1").getPart(2)) {
                quests.get("quest1").completedPart(2);
                if (!quests.get("quest1").getPart(1)) {
                    System.out.println("Hrangst Jaltibrond: Thank you so much. I am absolutely starving, could you please pass me some of that bread?\n");
                    characters.get("Hrangst Jaltibrond").addDialogue("salutations", "Hello, friend. I am starving, is there any chance you could pass me some of that bread?");
                } else {
                    System.out.println("Hrangst Jaltibrond: Thank you so much for all your help, kind soul. I won't bother you anymore. Here, take this key so you may continue your journey. I wish you the best of fortune. Now, I must go.\nThe old man didn't smile, but as he walked away, you could see the gratefulness in his eyes. You wonder how such a scrawny figure might fare in such a place, but before you had a time to reply he was gone.\n");
                    characters.get("Hrangst Jaltibrond").removeItem(items.get("sturdy key"));
                    player.addItem(items.get("sturdy key"));
                    characters.get("Hrangst Jaltibrond").removeDialogue("salutations");
                    characters.get("Hrangst Jaltibrond").addDialogue("salutations", "Hello, friend. I appreciate all your help.");
                    characters.get("Hrangst Jaltibrond").removeDialogue("greetingsNear");
                    characters.get("Hrangst Jaltibrond").addDialogue("greetingsNear", "You hear the old man moving around in the adjoining room.");
                    characters.get("Hrangst Jaltibrond").removeDialogue("greetingsSame");
                    characters.get("Hrangst Jaltibrond").addDialogue("greetingsSame", "The scrawny old figure you rescued earlier seems to wander around aimlessly. His ambitions seem a great mystery. Why is he not trying to escape this place?");
                    characters.get("Hrangst Jaltibrond").addLocationHistory(rooms.get("hallwayB1"));
                    characters.get("Hrangst Jaltibrond").setMoveable(true);
                }
            }
            return;
        }
    }
    
    /**
     * Print the description of the room and directions. If there
     * is a second word, analyze the object specified.
     * @param command The object to look at.
     */
    private void look(Command command) {
        if (!command.hasSecondWord()) {
            printLocationInfo();
            return;
        }

        String secondWord = command.getSecondWord();
        if (secondWord.equals("inventory")) {
            player.printInventory();
            return;
        }
        
        Item item = player.getLocation().getItem(secondWord);
        // if the item wasn't found in the room, look in the player's inventory instead. If still not found, print an error message.
        if (item == null) {
            item = player.getItem(secondWord);
        }
        if (item == null) {
            System.out.println("There is no such item here.");
        }
        lookAtItem(item);
    }
    
    /**
     * Print the item's details.
     */
    private void lookAtItem(Item item) {
        System.out.println("Taking a look at " + item.getName() + ":");
        System.out.println(item.getDescription());
        System.out.println("Weight: " + item.getWeight() + " kg.");
        if (item.getType().equals("miscellaneous") || item.getType().equals("quest") || item.getType().equals("key")) {
            System.out.println();
            return;
        }
        System.out.println(item.getParameterType() + ": " + item.getParameter() + ".\n");
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}