import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Order implements Comparable<Order> {
    private HashMap<Item,Integer> Items = new HashMap<Item,Integer>();
    private ArrayList<Item> ItemsList = new ArrayList<Item>();
    private String SpecialRequest;
    private int OrderID;
    private String CustomerName;
    private String pass;
    private Customer customer;
    private int TotalPrice;
    private String Review;
    private static int IDs = 1;
    private String isDelivered = "Pending";
    public Order(int OrderID, String CustomerName) {
        this.OrderID = OrderID;
        this.CustomerName = CustomerName;
    }
    public int compareTo(Order o) {
        return this.customer.getVIPStatus() - o.customer.getVIPStatus();
    }
    @Override
    public String toString() {
        return "Order + " + OrderID + " by " + CustomerName + " with total price " + TotalPrice;
    }
    public Order(ArrayList<Item> cart, Customer customer) {
        this.OrderID = IDs;
        IDs++;
        this.CustomerName = customer.getUsername();
        this.pass = customer.getPassword();
        this.customer = customer;
        for(Item item: cart) {
            Items.put(item, item.getQty());
            ItemsList.add(item);
            this.TotalPrice += item.getPrice() * item.getQty();
        }
    }
    public void setSpecialRequest(String request) {
        SpecialRequest = request;
    }
    public String getSpecialRequest() {
        return SpecialRequest;
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
        int TotalPrice = 0;
        for(Item item: Items.keySet()) {
            TotalPrice += item.getPrice() * Items.get(item);
        }
        return TotalPrice;
    }
    public String getOrderStatus() {
        return isDelivered;
    }
    public HashMap<Item, Integer> getItemsQty() {
        return Items;
    }
    public void setReview(String review) {
        Review = review;
    }
    public String getReview() {
        return Review;
    }



}
