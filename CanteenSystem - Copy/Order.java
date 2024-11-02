import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    private HashMap<Item,Integer> Items = new HashMap<Item,Integer>();
    private ArrayList<Item> ItemsList = new ArrayList<Item>();
    private int OrderID;
    private String CustomerName;
    private String pass;
    private Customer customer;
    private int TotalPrice;
    private static int IDs = 1;
    private String isDelivered = "Pending";
    public Order(int OrderID, String CustomerName) {
        this.OrderID = OrderID;
        this.CustomerName = CustomerName;
    }
    public Order(ArrayList<Item> items, Customer customer) {
        this.OrderID = IDs;
        IDs++;
        this.CustomerName = customer.getUsername();
        this.pass = customer.getPassword();
        for(Item item: items) {
            Items.put(item, item.getQty());
            ItemsList.add(item);
            this.TotalPrice += item.getPrice() * item.getQty();
        }
    }
    public void setOrderStatus(String status) {
        isDelivered = status;
    }
    public Customer getCustomer() {
        return customer;
    }

    public void addItem(Item item, int quantity) {
        Items.put(item, quantity);
        ItemsList.add(item);
    }

    public ArrayList<Item> getItemsList() {
        return ItemsList;
    }
    public String getPass() {
        return pass;
    }
    public HashMap<Item, Integer> getItems() {
        return Items;
    }

    public int getOrderID() {
        return OrderID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public int getTotalPrice() {
        for(Item item: Items.keySet()) {
            TotalPrice += item.getPrice() * Items.get(item);
        }
        return TotalPrice;
    }
    public String getOrderStatus() {
        return isDelivered;
    }

}
