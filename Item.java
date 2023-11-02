
public class Item {
    private String itemDescription;
    private String itemName;
    private boolean isInInventory;

    public Item(String itemName, String itemDescription) {
        this.setDescription(itemDescription);
        this.itemName = itemName;
        this.isInInventory = false;
    }

    public void setDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
            return itemDescription;
    }

    public void pickedUp() {
        this.isInInventory = true;
    }

}
