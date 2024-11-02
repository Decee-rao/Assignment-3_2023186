import javax.annotation.processing.Filer;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer extends User {
    private ArrayList<Item> cart = new ArrayList<>();
    private HashMap<Item, Integer> ItemtoQty = new HashMap<>();
    private HashMap<String,Item> ItemtoName = new HashMap<>();
    private ArrayList<Order> placedOrders = new ArrayList<Order>();
    private int isVIP = 1;
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
        String[] responses = {"Browse Menu", "Cart", "Track Your Order","Buy Premium","Review Items","View Reviews","Logout"};
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
                isVIP = 0;
            }
            else if (option == 4) {
                reviewItems();
            }
            else if (option == 5) {
                viewReview();
            }
            else {
                break;
            }
        }
    }
    private static HashMap<String,Item> OrderIDtoOrder = new HashMap<>();
    private void reviewItems() {
        JComboBox<String> reviewBox = new JComboBox<>();

        for (Order order : Data.getCompletedOrders()) {
            if (order.getOrderStatus().equals("Delivered")) {
                for(Item item: order.getItemsList())
                {
                    reviewBox.addItem(item.getName());
                    OrderIDtoOrder.put(item.getName(),item);
                }
            }
        }

        if (reviewBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "No orders to review.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Select Order:"));
        panel.add(reviewBox);
        panel.add(new JLabel("Enter your review:"));
        JTextArea reviewArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(reviewArea);
        panel.add(scrollPane);

        int result = JOptionPane.showConfirmDialog(null, panel, "Review Order", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String selectedOrderId = (String) reviewBox.getSelectedItem();
            if (selectedOrderId != null) {
                Item selecteditem = OrderIDtoOrder.get(selectedOrderId);
                String review = reviewArea.getText();
                if (review != null && !review.trim().isEmpty()) {
                    selecteditem.addReview(review);
                    JOptionPane.showMessageDialog(null, "Thank you for your review!", "Review Submitted", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No review entered.", "Notice", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    private void viewReview() {
        JComboBox<String> orderBox = new JComboBox<>();
        Map<String, Item> deliveredOrdersMap = new HashMap<>();

        for (Item item : Data.getItems()) {
            orderBox.addItem(item.getName());
            deliveredOrdersMap.put(item.getName(), item);
        }

        if (orderBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "No Items Available to reviews.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selection = JOptionPane.showConfirmDialog(null, orderBox, "Select a Delivered Order", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (selection == JOptionPane.OK_OPTION) {
            String selectedOrder = (String) orderBox.getSelectedItem();
            Item item = deliveredOrdersMap.get(selectedOrder);
            if (item.getReviews().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No reviews for this item.", "Notice", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder reviews = new StringBuilder();
                for (String review : item.getReviews()) {
                    reviews.append(review).append("\n");
                }
                JOptionPane.showMessageDialog(null, reviews.toString(), "Reviews", JOptionPane.INFORMATION_MESSAGE);
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
        JTextField specialRequest = new JTextField(50);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Special Request:"));
        panel.add(specialRequest);
        int result = JOptionPane.showConfirmDialog(null, panel, "Checkout", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        Order order = new Order(cart, this);
        order.setSpecialRequest(specialRequest.getText());
        Data.OrderQueue.add(order);
        Data.Orders.add(order);
        cart.clear();
        JOptionPane.showMessageDialog(null, "Order placed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    public void completeOrder(Order order) {
        placedOrders.add(order);
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


    public void trackOrder() {
        String[] columns = {"Order ID", "Item Name", "Per Unit Price", "Qty", "Total Price", "Status"};

        int numOrders = 0;
        for (Order order : Data.Orders) {
            numOrders += order.getItemsList().size();
        }

        if (numOrders == 0) {
            JOptionPane.showMessageDialog(null, "No orders placed yet.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object[][] data = new Object[numOrders][6];
        int i = 0;

        for (Order order : Data.Orders) {
            int totalPrice = 0;
            data[i][0] = order.getOrderID();
            for (int j = 0; j < order.getItemsList().size(); j++) {
                data[i + j][1] = order.getItemsList().get(j).getName();
                data[i + j][2] = order.getItemsList().get(j).getPrice();
                data[i + j][3] = order.getItemsQty().get(order.getItemsList().get(j));
                totalPrice += order.getItemsList().get(j).getPrice() * order.getItemsQty().get(order.getItemsList().get(j));
            }
            data[i][4] = totalPrice;
            data[i][5] = order.getOrderStatus();
            i += order.getItemsList().size();
        }

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(scrollPane);

        JButton cancelButton = new JButton("Cancel Order");
        panel.add(cancelButton);

        cancelButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select an order to cancel.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int orderId = (int) table.getValueAt(row, 0);
            Order orderToRemove = null;
            for (Order order : Data.Orders) {
                if (order.getOrderID() == orderId) {
                    orderToRemove = order;
                    break;
                }
            }

            if (orderToRemove != null) {
                Data.Orders.remove(orderToRemove);
                JOptionPane.showMessageDialog(null, "Order canceled successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                trackOrder();
            }
        });

        JOptionPane.showConfirmDialog(null, panel, "Track Orders", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }


}