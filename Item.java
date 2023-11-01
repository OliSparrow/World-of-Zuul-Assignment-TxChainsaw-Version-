
public class Item {
    private String itemDescription;
    private String inventoryDescription;
    private boolean isInInventory;

    public Item(String itemDescription, String inventoryDescription) {
        this.setDescription(itemDescription);
        this.inventoryDescription = inventoryDescription;
        this.isInInventory = false;
    }

    public void setDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemDescription() {
        return (isInInventory) ? inventoryDescription : itemDescription;

    }

    public void pickedUp() {
        this.isInInventory = true;
    }

}
