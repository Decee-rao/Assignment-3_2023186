import javax.annotation.processing.Filer;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer extends User {
    private ArrayList<Item> cart = new ArrayList<>();
    private HashMap<Item, Integer> ItemtoQty = new HashMap<>();
    private HashMap<String,Item> ItemtoName = new HashMap<>();
    private ArrayList<Order> placedOrders = new ArrayList<Order>();
    private int isVIP = 0;
    JDialog dialog;
    public Customer(String username, String password) {
        super(username, password);
    }

    public void addToCart(Item item) {
        cart.add(item);
    }

    public void removeFromCart(Item item) {
        cart.remove(item);
    }

    public ArrayList<Item> getCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }
    public int getVIPStatus() {
        return isVIP;
    }

    public static void Login() {
        while (true) {
            JPanel panel = new JPanel();
            JLabel label1 = new JLabel("Enter username: ");
            JTextField username = new JTextField(50);
            JLabel label2 = new JLabel("Enter password: ");
            JPasswordField password = new JPasswordField(15);
            panel.add(label1);
            panel.add(username);
            panel.add(label2);
            panel.add(password);
            String[] options = new String[]{"OK", "Cancel", "Register"};
            int option = JOptionPane.showOptionDialog(null, panel, "Login", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
            if (option == 0) {
                String UsrName = username.getText();
                String PassWord = new String(password.getPassword());
                if (Data.checkCustomer(UsrName, PassWord)) {
                    JOptionPane.showOptionDialog(null, "Login successful", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"OK"}, "OK");
                    Customer customer = (Customer) Data.getCustomer(UsrName,PassWord); // Assuming Data.getUser returns a Customer object
                    customer.Menu();
                    break;
                } else {
                    System.out.println("Invalid username or password");
                }
            } else if (option == 1) {
                JOptionPane.showMessageDialog(null, "Cancelled");
            } else {
                String UsrName = username.getText();
                String PassWord = new String(password.getPassword());
                Data.Customers.add(new Customer(UsrName, PassWord));
                JOptionPane.showOptionDialog(null, "User added successfully", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"OK"}, "OK");
            }
        }
    }

    public void Menu() {
        String[] responses = {"Browse Menu", "Cart", "Track Your Order","Buy Premium","Logout"};
        while (true) {
            int option = JOptionPane.showOptionDialog(null, "Choose your option", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses, responses[0]);
            if (option == 0) {
                buyItems();
            }
            else if (option == 1) {
                ViewCart();
            }
            else if (option == 2) {
                 trackOrder();
            }
            else if (option == 3) {
                isVIP = 1;
            }
            else {
                break;
            }
        }
    }

    private void ViewCart() {
        String[] columnNames = {"Item Name", "Quantity", "Price", "Total Price"};

        Object[][] data = new Object[cart.size()][4];
        int i = 0;
        int countItems= 0;
        for(Item item: cart){
            if(item.getQty() > 0)
            {
                countItems++;
            }
        }
        if (countItems == 0) {
            JOptionPane.showMessageDialog(null, "Cart is empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Item item : cart) {
            data[i][0] = item.getName();
            data[i][1] = item.getQty();
            data[i][2] = item.getPrice();
            data[i][3] = item.getPrice() * item.getQty();
            i++;

        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(scrollPane);

        JButton proceedButton = new JButton("Proceed to Checkout");
        JButton clearButton = new JButton("Clear Cart");
        JButton backButton = new JButton("Back");
        JButton updateButton = new JButton("Refresh");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(proceedButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel);
        panel.add(updateButton);

        proceedButton.addActionListener(e -> checkout());
        clearButton.addActionListener(e -> clearCart());
        backButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(panel);
            if (window instanceof JDialog) {
                ((JDialog) window).dispose();
            }
        });

        updateButton.addActionListener(e -> {
            for (int row = 0; row < table.getRowCount(); row++) {
                updateTotalPriceView(table, row);
            }
        });
        JDialog dialog = new JDialog();
        dialog.setTitle("Cart");
        dialog.setModal(true);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void updateTotalPriceView(JTable table, int row) {
        String quantityStr = table.getValueAt(row, 1).toString();
        int newQty;
        try {
            newQty = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid quantity entered. Please enter a valid number.");
            return;
        }
        String priceStr = table.getValueAt(row, 2).toString();
        double pricePerItem;
        try {
            pricePerItem = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price format. Please check the data.");
            return;
        }
        String itemName = (String) table.getValueAt(row, 0);
        Item i = ItemtoName.get(itemName);
        i.setQty(newQty);
        double totalPrice = pricePerItem * newQty;
        table.setValueAt(totalPrice, row, 3);
    }

    public void checkout() {
        if(cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Cart is empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Order order = new Order(cart, this);
        Data.Orders.add(order);
        cart.clear();
        JOptionPane.showMessageDialog(null, "Order placed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    public void buyItems() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] columns = {"Item Name", "Price", "Stock", "Qty","Tags"};

        Object[][] data = new Object[Data.getItems().size()][5];
        int i = 0;
        for (Item item : Data.getItems()) {
            data[i][0] = item.getName();
            data[i][1] = item.getPrice();
            data[i][2] = item.getStock();
            data[i][3] = 0;
            data[i][4] = item.getTags();
            ItemtoName.put(item.getName(), item);
            i++;
        }

        JTable table = new JTable(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        JButton addButton = new JButton("Add to Cart");
        panel.add(addButton);

        JPopupMenu columnMenu = new JPopupMenu();
        JMenuItem sortAsc = new JMenuItem("Sort Ascending");
        JMenuItem sortDesc = new JMenuItem("Sort Descending");
        JMenuItem Filter = new JMenuItem("Filter Column");
        columnMenu.add(sortAsc);
        columnMenu.add(sortDesc);
        columnMenu.addSeparator();
        columnMenu.add(Filter);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int col = table.columnAtPoint(e.getPoint());
                    columnMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        sortAsc.addActionListener(e -> sortTable(table, true, sorter));
        sortDesc.addActionListener(e -> sortTable(table, false, sorter));
        Filter.addActionListener(e -> Filter(table, table.getSelectedColumn(), sorter));

        addButton.addActionListener(e -> {
            boolean itemAdded = false;

            for (int row = 0; row < table.getRowCount(); row++) {
                Item selectedItem = ItemtoName.get(table.getValueAt(row, 0));
                int quantity;
                try {
                    quantity = Integer.parseInt(table.getValueAt(row, 3).toString());
                    if (quantity <= 0) {
                        continue;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid quantity in row " + (row + 1) + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (quantity > selectedItem.getStock()) {
                    JOptionPane.showMessageDialog(null, "Not enough stock for " + selectedItem.getName() + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                selectedItem.setQty(quantity);
                this.addToCart(selectedItem);
                itemAdded = true;
            }
            if (itemAdded) {
                JOptionPane.showMessageDialog(null, "Items added to cart.");
            } else {
                JOptionPane.showMessageDialog(null, "No items were added. Please enter quantities.");
            }
        });

        JOptionPane.showMessageDialog(null, panel, "Buy Items", JOptionPane.INFORMATION_MESSAGE);
    }
    public void Filter(JTable table, int column, TableRowSorter<TableModel> sorter) {
        String filterTag = JOptionPane.showInputDialog(null, "Enter tag to filter by:", "Filter by Tag", JOptionPane.QUESTION_MESSAGE);

        if (filterTag == null || filterTag.trim().isEmpty()) {
            ((TableRowSorter<TableModel>) table.getRowSorter()).setRowFilter(null);
            return;
        }
        sorter = (TableRowSorter<TableModel>) table.getRowSorter();
        sorter.setRowFilter(new RowFilter<TableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                String itemName = entry.getStringValue(0);
                Item item = ItemtoName.get(itemName);
                if (item != null) {
                    List<String> itemTags = item.getTags();
                    return itemTags.contains(filterTag.trim());
                }
                return false;
            }
        });
    }
    public void sortTable(JTable table, boolean ascending, TableRowSorter<TableModel> sorter) {
        // at site https://www.codejava.net/java-se/swing/6-techniques-for-sorting-jtable-you-should-know
        sorter.setComparator(1, (o1, o2) -> {
            Double price1 = Double.parseDouble(o1.toString());
            Double price2 = Double.parseDouble(o2.toString());
            return price1.compareTo(price2);
        });

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int columnIndex = table.getSelectedColumn();
        if (columnIndex == -1) {
            columnIndex = 1;
        }

        sortKeys.add(new RowSorter.SortKey(columnIndex, ascending ? SortOrder.ASCENDING : SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
        System.out.println("Sorted by column " + table.getColumnName(columnIndex) + " in " + (ascending ? "ascending" : "descending") + " order.");
    }

    private void updateTotalPriceBuy(JTable table, int row) {
        double pricePerItem = (double) table.getValueAt(row, 1);
        int newQty = (int) table.getValueAt(row, 3);
        table.setValueAt(pricePerItem * newQty, row, 5);
    }


    public void trackOrder(){

        String columns[] = {"Order ID", "Customer Name", "Per Unit Price", "Qty","Total Price","Status"};
        Object [][] data = new Object[Data.Orders.size()][6];
        int i = 0;
        int numOrders = 0;
        for (Order order : Data.Orders) {
            data[i][0] = order.getOrderID();
            if(order.getCustomerName().equals(this.getUsername()) && order.getPass().equals(this.getPassword())){
                numOrders++;
                for(Item item: order.getItemsList()){
                    data[i][1] = item.getName();
                    data[i][2] = item.getPrice();
                    data[i][3] = item.getQty();
                    data[i][4] = order.getTotalPrice();
                }
                data[i][5] = order.getOrderStatus();
                i++;
            }
        }
        if(numOrders == 0){
            JOptionPane.showMessageDialog(null, "No orders placed yet.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(scrollPane);
        JButton CancelButton = new JButton("Cancel Order");
        panel.add(CancelButton);
        CancelButton.addActionListener(e->{
            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Please select an order to cancel.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int OrderID = (int) table.getValueAt(row, 0);
            for(Order order: Data.Orders){
                if(order.getOrderID() == OrderID){
                    Data.Orders.remove(order);
                    JOptionPane.showMessageDialog(null, "Order cancelled successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        });

        JOptionPane.showMessageDialog(null, scrollPane, "Orders", JOptionPane.INFORMATION_MESSAGE);

    }
    
}