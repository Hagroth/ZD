/**
 * Class Item - represents an item.
 */
public class Item
{
    // the name of the item.
    private String name;
    // the description (displayed when looking at it).
    private String description;
    // the weight.
    private double weight;
    // the type of parameter. Displayed as a "prefix"
    // when displaying the parameter when looking at
    // the item, e.g. "Damage: 10".
    private String parameterType;
    // the parameter, depending on type of item. For
    // example damage for weapons.
    private int parameter;
    // determines whether the item is takeable or not.
    private boolean takeable;
    // the type of item, e.g. "weapon", "miscellaneous"
    // etc. Determines what happens when using it, looking
    // at it etc.
    private String type;
    // determines what happens when the item is used. Only
    // for display purposes for printing e.g. "You swing
    // the sword".
    private String use;
    // determines whether the item is flagged as quest item
    // or not. Flagged items can't be dropped or given to
    // others.
    private boolean questItem;

    /**
     * Create an item described "description". The purpose of "parameter"
     * varies depending on item type, e.g. if the item can be used as a 
     * weapon its damage will be handled by its "parameter" field. The type
     * also determines how it is used. "parameterType" determines what type
     * of parameter, e.g. "Damage", the item uses (only for the purpose of
     * looking at the item). Types used: "weapon", "advantage", "key",
     * "miscellaneous", "quest".
     * "Takeable" determines whether the item can be picked up.
     * @param name The item's name.
     * @param description The item's description.
     * @param weight The item's weight.
     * @param parameterType The item's parameter's type.
     * @param parameter The item's parameter.
     * @param takeable The item's "takeable" status.
     * @param type The item's type.
     * @param use Description of what happens when item is used.
     * @param questItem Determines whether item is flagged as "quest item".
     * or not.
     */
    public Item(String name, String description, double weight, String parameterType, int parameter, boolean takeable, String type, String use, boolean questItem) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.parameterType = parameterType;
        this.parameter = parameter;
        this.takeable = takeable;
        this.type = type;
        this.use = use;
        this.questItem = questItem;
    }
    
    /**
     * Return the name of the item.
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Return the description of the item.
     * @return The description of the item.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Return the weight of the item, in kg.
     * @return The weight of the item, in kg.
     */
    public double getWeight() {
        return weight;
    }
    
    /**
     * Return the parameter of the item.
     * @return The parameter of the item.
     */
    public int getParameter() {
        return parameter;
    }
    
    /**
     * Return the item's parameter's type.
     * @return The item's parameter's type.
     */
    public String getParameterType() {
        return parameterType;
    }
    
    /**
     * Return the type of the item.
     * @return The type of the item.
     */
    public String getType() {
        return type;
    }
    
    /**
     * Prints a description of what happens
     * when the item is used.
     */
    public String printUse() {
        return use;
    }
    
    /**
     * Return the takeable status of the item.
     * @return The takeable status of the item.
     */
    public boolean getTakeable() {
        return takeable;
    }
    
    /**
     * Return the quest item status of the item.
     * @return The quest item status of the item.
     */
    public boolean getQuestItem() {
        return questItem;
    }
}
