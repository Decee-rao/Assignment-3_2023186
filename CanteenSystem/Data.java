import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Data {
    public static ArrayList<User> Users= new ArrayList<User>();
    public static ArrayList<Customer> Customers= new ArrayList<Customer>();
    public static ArrayList<Admin> Admins= new ArrayList<Admin>();
    public static ArrayList<Item> Items= new ArrayList<Item>();
    public static ArrayList<Order> Orders= new ArrayList<Order>();
    public static PriorityQueue<Order> OrderQueue = new PriorityQueue<Order>();
    public static HashMap<Item,Integer> ItemPrices = new HashMap<Item,Integer>();
    public static HashMap<Item,Integer> ItemStock = new HashMap<Item,Integer>();
    public static ArrayList<Order> completedOrders = new ArrayList<Order>();

    public Data() {
        Users.add(new User("admin","admin"));

        Admins.add(new Admin("admin","admin"));
        Customers.add(new Customer("customer","customer"));
        Users.add(new User("customer","customer"));
    }
    //Implements the Comparator interface to compare the priority of orders
    public static boolean checkUser(String username, String password) {
        for(User user: Users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public static boolean checkCustomer(String username, String password) {
        for(Customer customer : Customers) {
            if(customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public static ArrayList<Order> getCompletedOrders() {
        return completedOrders;
    }

    public static void addUser(String username, String password) {
        Users.add(new User(username, password));
    }

    public static ArrayList<Item> getItems() {
        return Items;
    }

    public static PriorityQueue<Order> getOrders() {
        return OrderQueue;
    }


    public static HashMap<Item, Integer> getItemPrices() {
        return ItemPrices;
    }

    public static HashMap<Item, Integer> getItemStock() {
        return ItemStock;
    }

    public static ArrayList<Admin> getAdmins() {
        return Admins;
    }

    public static ArrayList<Customer> getCustomers() {
        return Customers;
    }

    public static ArrayList<User> getUsers() {
        return Users;
    }

    public static void viewItems() {

        String[] columnNames = {"Item Name", "Price", "Stock"};
        Object[][] data = new Object[Data.getItems().size()][3];
        int i = 0;
        for (Item item : Data.getItems()) {
            data[i][0] = item.getName();
            data[i][1] = Data.ItemPrices.get(item);
            data[i][2] = Data.ItemStock.get(item);
            i++;
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        JOptionPane.showMessageDialog(null, scrollPane, "Items", JOptionPane.INFORMATION_MESSAGE);
    }

    public void addOrder(Order order,ArrayList<Item> items) {
        Orders.add(order);
        for(Item item: items) {
            ItemStock.put(item, ItemStock.get(item) - 1);
        }
    }
    public static Customer getCustomer(String username, String password) {
        for(Customer customer: Customers) {
            if(customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

}
