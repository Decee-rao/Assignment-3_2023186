import java.util.ArrayList;

public class Item {
    private String name;
    private int price;
    private int stock;
    private int Qty;
    private ArrayList<String> Tags = new ArrayList<String>();
    public Item(String name, int price, int stock,ArrayList<String> Tags) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.Tags = Tags;
    }
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQty() {
        return Qty;
    }
    public void setQty(int Qty) {
        this.Qty = Qty;
    }
    public void addTag(String tag) {
        Tags.add(tag);
    }
    public ArrayList<String> getTags() {
        return Tags;
    }
    public void removeTag(String tag) {
        Tags.remove(tag);
    }
}
