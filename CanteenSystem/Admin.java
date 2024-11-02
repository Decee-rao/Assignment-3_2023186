import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Admin extends User {
    private static HashMap<String,Order> strToOrder = new HashMap<String,Order>();
    private static HashMap<Item,Integer> SoldItems = new HashMap<Item,Integer>();
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
            Data.getItems().add(item);
            Data.ItemPrices.put(item, Price);
            Data.ItemStock.put(item, Stock);
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
            String responses[] = {"View Pending Orders", "Update Order Status ", "Process Refunds", "Handle Special Requests","Back To Main Menu"};
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
                ProcessRefunds();
            }
            else if(Option == 3)
            {
                HandleSpecialRequests();
            }
            else {
                inMenu = false;
            }
        }
    }

//    private static void UpdateOrders() {
//
//        String[] columnNames = {"Order Id", "Customer Name", "Item Name", "Quantity", "Total Price", "Order Status"};
//
//        Object[][] data = new Object[Data.getOrders().size()][6];
//        int i = 0;
//        for (Order order : Data.getOrders()) {
//            data[i][0] = order.getOrderID();
//            data[i][1] = order.getCustomerName();
//            for (int j = 0; j < order.getItems().size(); j++) {
//                data[i+j][2] = order.getItemsList().get(j).getName();
//                data[i+j][3] = order.getItemsList().get(j).getStock();
//            }
//            i = i + order.getItemsList().size();
//            data[i][4] = order.getTotalPrice();
//            data[i][5] = order.getOrderStatus();
//        }
//
//        JTable table = new JTable(data, columnNames);
//        JScrollPane scrollPane = new JScrollPane(table);
//
//        JComboBox<String> orderComboBox = new JComboBox<>();
//        for (Order order : Data.getOrders()) {
//            orderComboBox.addItem("Order ID: " + order.getOrderID());
//        }
//
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.add(scrollPane, BorderLayout.CENTER);
//        panel.add(orderComboBox, BorderLayout.SOUTH);
//        int option = JOptionPane.showConfirmDialog(null, panel, "Update Orders", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//
//        if (option == JOptionPane.OK_OPTION) {
//            String selectedOrder = (String) orderComboBox.getSelectedItem();
//            int orderId = Integer.parseInt(selectedOrder.split(": ")[1]);
//            for (Order order : Data.getOrders()) {
//                if (order.getOrderID() == orderId) {
//                    JOptionPane.showMessageDialog(null, "Order " + orderId + " updated successfully.");
//                    break;
//                }
//            }
//        }
//    }
    public static void UpdateOrders(){
        JComboBox<String> orderComboBox = new JComboBox<>();
        for (Order order : Data.getOrders()) {
            orderComboBox.addItem(order.toString());
            strToOrder.put(order.toString(), order);
        }
        JPanel panel = new JPanel();
        panel.add(orderComboBox);
        int option = JOptionPane.showConfirmDialog(null, panel, "Update Orders", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String selectedOrder = (String) orderComboBox.getSelectedItem();
            Order order = strToOrder.get(selectedOrder);
            String[] responses = {"Processing","Delivered","Denied","Refunded"};
            int Option = JOptionPane.showOptionDialog(null, "Choose your option", "Order Status", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses, responses[0]);
            if(Option == 0) {
                order.setOrderStatus("Processing");
            }
            else if(Option == 1)
            {
                order.setOrderStatus("Delivered");
                order.getCustomer().completeOrder(order);
                Data.completedOrders.add(order);
            }
            else if(Option == 2) {
                order.setOrderStatus("Denied");
            }
            else {
                order.setOrderStatus("Refunded");
            }
        }
    }
    private static void ProcessRefunds() {
        JComboBox<String> orderComboBox = new JComboBox<>();
        for (Order order : Data.getOrders()) {
            orderComboBox.addItem(order.toString());
            strToOrder.put(order.toString(), order);
        }
        JPanel panel = new JPanel();
        panel.add(orderComboBox);
        int option = JOptionPane.showConfirmDialog(null, panel, "Process Refunds", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String selectedOrder = (String) orderComboBox.getSelectedItem();
            Order order = strToOrder.get(selectedOrder);
            order.setOrderStatus("Refunded");
        }
    }
    private static void HandleSpecialRequests(){

    }
    private static void ViewOrders() {
        String[] columnNames = {"Order Id", "Customer Name", "Item Name", "Quantity", "Total Price", "Order Status"};
        int totalItems = 0;
        for (Order order : Data.getOrders()) {
            if (order.getOrderStatus().equals("Pending"))
            {
                totalItems += order.getItems().size();
            }
        }
        Object[][] data = new Object[totalItems][6];
        int i = 0;
        for (Order order : Data.getOrders()) {
            if(order.getOrderStatus().equals("Pending"))
            {
                data[i][0] = order.getOrderID();
                data[i][1] = order.getCustomerName();
                for (int j = 0; j < order.getItems().size(); j++) {
                    data[i + j][2] = order.getItemsList().get(j).getName();
                    data[i + j][3] = order.getItemsList().get(j).getStock();
                }
                i = i + order.getItemsList().size() - 1;
                data[i][4] = order.getTotalPrice();
                data[i][5] = order.getOrderStatus();
            }
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
            String responses[] = {"Generate Sales Report","Back To Main Menu"};
            int Option = JOptionPane.showOptionDialog(null, "Choose your option", "Report Generation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses, responses[0]);
            if(Option == 0) {
                GenerateSalesReport();
            }
            else {
                inMenu = false;
            }
        }
    }

    private static void GenerateSalesReport() {
        String[] columnNames = {"Total Orders","Delivered Orders","Pending Orders","Total Sales"};
        int totalOrders = Data.getOrders().size();
        int delieveredOrders = 0;
        int pendingOrders = 0;
        int totalSales = 0;
        for(Order order: Data.getOrders()) {
            if(order.getOrderStatus().equals("Delivered")) {
                delieveredOrders++;
                totalSales += order.getTotalPrice();
            }
            else {
                pendingOrders++;
            }

        }
        Object data[][] = new Object[3][4];
        data[0][0] = totalOrders;
        data[0][1] = delieveredOrders;
        data[0][2] = pendingOrders;
        data[0][3] = totalSales;
        data[1][0] = "Most Sold Item : ";
        for (Order order: Data.getCompletedOrders()){
            for(Item item: order.getItems().keySet()){
                if(SoldItems.containsKey(item)){
                    SoldItems.put(item,SoldItems.get(item)+order.getItems().get(item));
                }
                else{
                    SoldItems.put(item,order.getItems().get(item));
                }
            }
        }
        int max = 0;
        Item mostSold = null;
        for(Item item: SoldItems.keySet()){
            if(SoldItems.get(item)>max){
                max = SoldItems.get(item);
                mostSold = item;
            }
        }
        data[1][1] = mostSold.getName();
        data[2][0] = "Least Sold Item : ";
        int min = Integer.MAX_VALUE;
        Item leastSold = null;
        for(Item item: SoldItems.keySet()){
            if(SoldItems.get(item)<min){
                min = SoldItems.get(item);
                leastSold = item;
            }
        }
        data[2][1] = leastSold.getName();
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Sales Report", JOptionPane.INFORMATION_MESSAGE);

    }
}
