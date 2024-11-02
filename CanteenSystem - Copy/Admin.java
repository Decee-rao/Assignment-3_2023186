import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Admin extends User {
    HashMap<Integer,Item> optionSelector = new HashMap<Integer,Item>();
    public Admin(String username, String password) {
        super(username, password);
    }
    public static void Login() {
        while (true) {
            {
                JPanel panel = new JPanel();
                JLabel label1 = new JLabel("Enter username: ");
                JTextField username = new JTextField(50);
                JLabel label2 = new JLabel("Enter password: ");
                JPasswordField password = new JPasswordField(15);
                panel.add(label1);
                panel.add(username);
                panel.add(label2);
                panel.add(password);
                String[] options = new String[]{"OK", "Cancel"};
                int option = JOptionPane.showOptionDialog(null, panel, "Login", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
                if (option == 0) {
                    String UsrName = new String(username.getText());
                    String PassWord = new String(password.getPassword());
                    if (Data.checkUser(UsrName, PassWord)) {
                        JOptionPane.showOptionDialog(null, "Login successful", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"OK"}, "OK");
                        Menu();
                        break;
                    } else {
                        System.out.println("Invalid username or password");
                    }
                } else if (option == 1) {
                    JOptionPane.showMessageDialog(null, "Cancelled");
                }
            }
        }
    }
    private static void Menu(){
        boolean inMenu = true;
        while(inMenu)
        {
            JPanel panel = new JPanel(new GridLayout(3, 1));
            String responses[] = {"Menu Management", "Order management", "Report Generation","Return to Main Menu"};
            int Option = JOptionPane.showOptionDialog(null, "Choose your option", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses, responses[0]);
            if(Option == 0) {
                MenuManagement();
            }
            else if(Option == 1)
            {
                OrderManagement();
            }
            else if (Option == 2)
            {
                ReportGeneration();
            }
            else {
                inMenu = false;
            }
        }
    }
    private static void MenuManagement(){
        boolean inMenu = true;
        while(inMenu)
        {
            JPanel panel = new JPanel(new GridLayout(7, 1));
            String responses[] = {"Add New Items", "Update Existing Items", "Remove Items", "Modify Prices","Update Availaility","View Items ","Back To Main Menu"};
            int Option = JOptionPane.showOptionDialog(null, "Choose your option", "Menu Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses, responses[0]);
            if(Option == 0) {
                AddItems();
            }
            else if(Option == 1)
            {
                UpdateItems();
            }
            else if(Option == 2)
            {
                RemoveItems();
            }
            else if(Option == 3)
            {
                ModifyPrices();
            }
            else if (Option == 4)
            {
                UpdateAvailability();
            }
            else if (Option == 5)
            {
                ViewItems();
            }
            else
            {
                inMenu = false;
            }
        }
    }

    private static void ViewItems() {

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

    private static void UpdateAvailability() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        JComboBox<String> itemComboBox = new JComboBox<>();
        for (Item item : Data.getItems()) {
            itemComboBox.addItem(item.getName());
        }

        JLabel label1 = new JLabel("Select Item: ");
        panel.add(label1);
        panel.add(itemComboBox);

        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Update Availability", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

        if (option == 0) {
            String selectedItemName = (String) itemComboBox.getSelectedItem();

            for (Item item : Data.getItems()) {
                if (item.getName().equals(selectedItemName)) {
                    int newStock = Integer.parseInt(JOptionPane.showInputDialog("Enter new stock: "));
                    Data.ItemStock.put(item, newStock);
                    JOptionPane.showOptionDialog(null, "Stock updated successfully", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"OK"}, "OK");
                    break;
                }
            }
        } else if (option == 1) {
            JOptionPane.showMessageDialog(null, "Cancelled");
        }
    }

    private static void ModifyPrices() {
        JPanel panel = new JPanel(new GridLayout(2, 1));


        JComboBox<String> itemComboBox = new JComboBox<>();
        for (Item item : Data.getItems()) {
            itemComboBox.addItem(item.getName());
        }

        JLabel label1 = new JLabel("Select Item: ");
        panel.add(label1);
        panel.add(itemComboBox);

        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Modify Prices", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

        if (option == 0) {
            String selectedItemName = (String) itemComboBox.getSelectedItem();

            for (Item item : Data.getItems()) {
                if (item.getName().equals(selectedItemName)) {
                    int newPrice = Integer.parseInt(JOptionPane.showInputDialog("Enter new price: "));
                    Data.ItemPrices.put(item, newPrice);
                    JOptionPane.showOptionDialog(null, "Price modified successfully", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"OK"}, "OK");
                    break;
                }
            }
        } else if (option == 1) {
            JOptionPane.showMessageDialog(null, "Cancelled");
        }
    }


    private static void RemoveItems() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        // Create a JComboBox to list all items
        JComboBox<String> itemComboBox = new JComboBox<>();
        for (Item item : Data.getItems()) {
            itemComboBox.addItem(item.getName());
        }

        JLabel label1 = new JLabel("Select Item: ");
        panel.add(label1);
        panel.add(itemComboBox);

        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Remove Items", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

        if (option == 0) {
            String selectedItemName = (String) itemComboBox.getSelectedItem();

            for (Item item : Data.getItems()) {
                if (item.getName().equals(selectedItemName)) {
                    Data.getItems().remove(item);
                    Data.ItemPrices.remove(item);
                    Data.ItemStock.remove(item);
                    for(Order order: Data.getOrders()) {
                        if(order.getItems().containsKey(item)) {
                            order.setOrderStatus("Denied");
                        }
                    }
                    JOptionPane.showOptionDialog(null, "Item removed successfully", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"OK"}, "OK");
                    break;
                }
            }
        } else if (option == 1) {
            JOptionPane.showMessageDialog(null, "Cancelled");
        }
    }


    private static void UpdateItems() {
        // First dialog to select the item
        JPanel selectPanel = new JPanel(new GridLayout(2, 1));
        JComboBox<String> itemComboBox = new JComboBox<>();
        for (Item item : Data.getItems()) {
            itemComboBox.addItem(item.getName());
        }
        JLabel selectLabel = new JLabel("Select Item: ");
        selectPanel.add(selectLabel);
        selectPanel.add(itemComboBox);

        String[] selectOptions = new String[]{"OK", "Cancel"};
        int selectOption = JOptionPane.showOptionDialog(null, selectPanel, "Select Item", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, selectOptions, selectOptions[0]);

        if (selectOption == 0) {
            String selectedItemName = (String) itemComboBox.getSelectedItem();
            Item selectedItem = null;
            for (Item item : Data.getItems()) {
                if (item.getName().equals(selectedItemName)) {
                    selectedItem = item;
                    break;
                }
            }

            if (selectedItem != null) {
                // Second dialog to modify the selected item's values
                JPanel modifyPanel = new JPanel(new GridLayout(4, 2));
                JLabel label1 = new JLabel("Item Name: ");
                JTextField itemName = new JTextField(selectedItem.getName(), 15);
                JLabel label2 = new JLabel("Enter New Price: ");
                JTextField itemPrice = new JTextField(String.valueOf(selectedItem.getPrice()), 15);
                JLabel label3 = new JLabel("Enter New Stock: ");
                JTextField itemStock = new JTextField(String.valueOf(selectedItem.getStock()), 15);

                modifyPanel.add(label1);
                modifyPanel.add(itemName);
                modifyPanel.add(label2);
                modifyPanel.add(itemPrice);
                modifyPanel.add(label3);
                modifyPanel.add(itemStock);

                String[] modifyOptions = new String[]{"OK", "Cancel"};
                int modifyOption = JOptionPane.showOptionDialog(null, modifyPanel, "Modify Item", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, modifyOptions, modifyOptions[0]);

                if (modifyOption == 0) {
                    String newName = itemName.getText();
                    int newPrice = Integer.parseInt(itemPrice.getText());
                    int newStock = Integer.parseInt(itemStock.getText());

                    selectedItem.setName(newName);
                    selectedItem.setPrice(newPrice);
                    selectedItem.setStock(newStock);

                    Data.ItemPrices.put(selectedItem, newPrice);
                    Data.ItemStock.put(selectedItem, newStock);

                    JOptionPane.showOptionDialog(null, "Item updated successfully", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"OK"}, "OK");
                } else {
                    JOptionPane.showMessageDialog(null, "Modification cancelled");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selection cancelled");
        }
    }

    private static void AddItems() {
        JPanel panel = new JPanel();
        JLabel label1 = new JLabel("Enter Item Name: (String)");
        JTextField ItemName = new JTextField(50);
        JLabel label2 = new JLabel("Enter Item Price:(integer) ");
        JTextField ItemPrice = new JTextField(15);
        JLabel label3 = new JLabel("Enter Item Stock: (integer)");
        JTextField ItemStock = new JTextField(15);
        JLabel label4 = new JLabel("Enter Item Tags: (String,String)");
        JTextField ItemTags = new JTextField(200);

        panel.add(label1);
        panel.add(ItemName);
        panel.add(label2);
        panel.add(ItemPrice);
        panel.add(label3);
        panel.add(ItemStock);
        panel.add(label4);
        panel.add(ItemTags);

        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Add Items", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
        if (option == 0) {
            String Name = new String(ItemName.getText());
            ArrayList<String> Tags = new ArrayList<String>();
            String[] tags = ItemTags.getText().split(",");
            for (String tag : tags) {
                Tags.add(tag);
            }
            int Price = Integer.parseInt(ItemPrice.getText());
            int Stock = Integer.parseInt(ItemStock.getText());
            Item item = new Item(Name,Price,Stock,Tags);
            Data.Items.add(item);
            Data.ItemPrices.put(item,Price);
            Data.ItemStock.put(item,Stock);
            JOptionPane.showOptionDialog(null, "Item added successfully", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"OK"}, "OK");
        } else if (option == 1) {
            JOptionPane.showMessageDialog(null, "Cancelled");
        }
    }

    private static void OrderManagement(){
        boolean inMenu = true;
        while(inMenu)
        {
            JPanel panel = new JPanel(new GridLayout(1, 4));
            String responses[] = {"View Pending Orders", "Update Order Status ", "Process Refunds", "Handle Special Requests"};
            int Option = JOptionPane.showOptionDialog(null, "Choose your option", "Order Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses, responses[0]);
            if(Option == 0) {
                ViewOrders();
            }
            else if(Option == 1)
            {
                UpdateOrders();
            }
            else if(Option == 2)
            {
//                ProcessRefunds();
            }
            else
            {
//                HandleSpecialRequests();
            }
        }
    }

    private static void UpdateOrders() {
        // Column names for the table
        String[] columnNames = {"Order Id", "Customer Name", "Item Name", "Quantity", "Total Price", "Order Status"};

        // Data for the table
        Object[][] data = new Object[Data.getOrders().size()][6];
        int i = 0;
        for (Order order : Data.getOrders()) {
            data[i][0] = order.getOrderID();
            data[i][1] = order.getCustomerName();
            for (int j = 0; j < order.getItems().size(); j++) {
                data[i+j][2] = order.getItemsList().get(j).getName();
                data[i+j][3] = order.getItemsList().get(j).getStock();
            }
            i = i + order.getItemsList().size();
            data[i][4] = order.getTotalPrice();
            data[i][5] = order.getOrderStatus();
        }

        // Create the table with the data
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a JComboBox to select an order
        JComboBox<String> orderComboBox = new JComboBox<>();
        for (Order order : Data.getOrders()) {
            orderComboBox.addItem("Order ID: " + order.getOrderID());
        }

        // Create a panel to hold the table and the combo box
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(orderComboBox, BorderLayout.SOUTH);

        // Show the panel in a JOptionPane
        int option = JOptionPane.showConfirmDialog(null, panel, "Update Orders", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            // Handle the order update logic here
            String selectedOrder = (String) orderComboBox.getSelectedItem();
            int orderId = Integer.parseInt(selectedOrder.split(": ")[1]);
            // Find the order by ID and update it
            for (Order order : Data.getOrders()) {
                if (order.getOrderID() == orderId) {
                    // Update order logic here
                    JOptionPane.showMessageDialog(null, "Order " + orderId + " updated successfully.");
                    break;
                }
            }
        }
    }

    private static void ViewOrders() {
        String[] columnNames = {"Order Id", "Customer Name", "Item Name", "Quantity", "Total Price", "Order Status"};
        Object[][] data = new Object[Data.getOrders().size()][6];
        int i = 0;
        for (Order order : Data.getOrders()) {
            data[i][0] = order.getOrderID();
            data[i][1] = order.getCustomerName();
            for (int j = 0; j < order.getItems().size(); j++) {
                data[i+j][2] = order.getItemsList().get(j).getName();
                data[i+j][3] = order.getItemsList().get(j).getStock();
            }
            i = i + order.getItemsList().size();
            data[i][4] = order.getTotalPrice();
            data[i][5] = order.getOrderStatus();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Orders", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void ReportGeneration(){
        boolean inMenu = true;
        while(inMenu)
        {
            JPanel panel = new JPanel(new GridLayout(3, 1));
            String responses[] = {"Generate Sales Report", "Generate Inventory Report", "Generate Customer Report"};
            int Option = JOptionPane.showOptionDialog(null, "Choose your option", "Report Generation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses, responses[0]);
            if(Option == 0) {
//                GenerateSalesReport();
            }
            else if(Option == 1)
            {
//                GenerateInventoryReport();
            }
            else
            {
//                GenerateCustomerReport();
            }
        }
    }
}
