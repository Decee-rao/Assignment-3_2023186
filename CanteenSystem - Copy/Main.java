import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        while (true)
        {
            String responses[] = {"Admin Login","Customer Login","Exit"};
            Data.Users.add(new User("admin", "admin"));
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
