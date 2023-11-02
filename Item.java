
public class Item {
    private String itemDescription;
    private String itemName;
    private boolean isInInventory;

    public Item(String itemDescription, String itemName) {
        this.setDescription(itemDescription);
        this.itemName = itemName;
        this.isInInventory = false;
    }

    public void setDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemDescription() {
        if (isInInventory) {
            return itemName;
        } else {
            return itemDescription;
        }

    }

    public void pickedUp() {
        this.isInInventory = true;
    }

}
