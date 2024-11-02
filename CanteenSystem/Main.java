import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Data.Users.add(new User("admin", "admin"));
        Data.Items.add(new Item("Apple", 10, 100, new ArrayList<String>()));
        Data.Items.add(new Item("Banana", 5, 100, new ArrayList<String>()));
        Data.Items.add(new Item("Orange", 7, 100, new ArrayList<String>()));
        Data.Customers.add(new Customer("a", "a"));
        Data.Customers.add(new Customer("b", "b"));
        Data.Customers.add(new Customer("c", "c"));
        while (true)
        {
            String responses[] = {"Admin Login","Customer Login","Exit"};


            int Option = JOptionPane.showOptionDialog(null, "Choose your login type", "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses, responses[0]);
            if (Option == 0) {
                System.out.println("Welcome Admin!!! ");
                Admin.Login();
            }
            else if (Option == 1) {
                System.out.println("Welcome Customer!!! ");
                Customer.Login();
            }
            else {
                break;
            }
        }
    }
}
