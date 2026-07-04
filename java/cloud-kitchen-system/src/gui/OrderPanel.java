package gui;

import model.MenuItem;
import model.Order;
import model.Restaurant;
import service.OrderService;
import service.RestaurantService;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class OrderPanel extends JPanel {
    private final UserService userService = new UserService();
    private final RestaurantService restaurantService = new RestaurantService();
    private final OrderService orderService = new OrderService();

    public OrderPanel(String userId) {
        setLayout(new BorderLayout());

        JPanel orderPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Place Order");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        orderPanel.add(titleLabel, constraints);

        constraints.gridwidth = 1;

        JLabel restaurantIdLabel = new JLabel("Restaurant ID:");
        constraints.gridy = 1;
        constraints.gridx = 0;
        orderPanel.add(restaurantIdLabel, constraints);

        JTextField restaurantIdField = new JTextField(20);
        constraints.gridx = 1;
        orderPanel.add(restaurantIdField, constraints);

        JLabel itemIdLabel = new JLabel("Item ID:");
        constraints.gridy = 2;
        constraints.gridx = 0;
        orderPanel.add(itemIdLabel, constraints);

        JTextField itemIdField = new JTextField(20);
        constraints.gridx = 1;
        orderPanel.add(itemIdField, constraints);

        JButton orderButton = new JButton("Place Order");
        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        orderPanel.add(orderButton, constraints);

        add(orderPanel, BorderLayout.NORTH);

        JTextArea orderTextArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        add(scrollPane, BorderLayout.CENTER);

        orderButton.addActionListener(e -> {
            String restaurantId = restaurantIdField.getText();
            String itemId = itemIdField.getText();

            Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
            if (restaurant != null && restaurant.getMenu().containsKey(itemId)) {
                MenuItem menuItem = restaurant.getMenu().get(itemId);
                Order order = new Order(UUID.randomUUID().toString(), userId, restaurantId, itemId, menuItem.getPrice());
                orderService.createOrder(order);

                JOptionPane.showMessageDialog(this, "Order placed successfully!");

                StringBuilder orderText = new StringBuilder();
                for (Order userOrder : orderService.getOrdersByUserId(userId)) {
                    orderText.append(userOrder.getOrderId()).append(" - ")
                            .append(userOrder.getRestaurantId()).append(": ")
                            .append(userOrder.getItemId()).append(" - $")
                            .append(userOrder.getPrice()).append("\n");
                }
                orderTextArea.setText(orderText.toString());

                restaurantIdField.setText("");
                itemIdField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid restaurant ID or item ID.");
            }
        });
    }
}
