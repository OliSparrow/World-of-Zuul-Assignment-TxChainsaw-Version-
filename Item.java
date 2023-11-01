public class Item {
    private String itemDescription;

    public Item(String itemDescription) {
        this.setDescription(itemDescription);
    }

    public void setDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemDescription() {
        return itemDescription;
    }


}
