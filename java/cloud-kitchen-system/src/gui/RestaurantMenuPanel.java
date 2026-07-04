package gui;

import model.MenuItem;
import model.Restaurant;
import service.RestaurantService;

import javax.swing.*;
import java.awt.*;

public class RestaurantMenuPanel extends JPanel {
    private final RestaurantService restaurantService = new RestaurantService();

    public RestaurantMenuPanel() {
        setLayout(new BorderLayout());

        JPanel addMenuItemPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Add Menu Item");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        addMenuItemPanel.add(titleLabel, constraints);

        constraints.gridwidth = 1;

        JLabel restaurantIdLabel = new JLabel("Restaurant ID:");
        constraints.gridy = 1;
        constraints.gridx = 0;
        addMenuItemPanel.add(restaurantIdLabel, constraints);

        JTextField restaurantIdField = new JTextField(20);
        constraints.gridx = 1;
        addMenuItemPanel.add(restaurantIdField, constraints);

        JLabel itemIdLabel = new JLabel("Item ID:");
        constraints.gridy = 2;
        constraints.gridx = 0;
        addMenuItemPanel.add(itemIdLabel, constraints);

        JTextField itemIdField = new JTextField(20);
        constraints.gridx = 1;
        addMenuItemPanel.add(itemIdField, constraints);

        JLabel itemNameLabel = new JLabel("Item Name:");
        constraints.gridy = 3;
        constraints.gridx = 0;
        addMenuItemPanel.add(itemNameLabel, constraints);

        JTextField itemNameField = new JTextField(20);
        constraints.gridx = 1;
        addMenuItemPanel.add(itemNameField, constraints);

        JLabel itemPriceLabel = new JLabel("Item Price:");
        constraints.gridy = 4;
        constraints.gridx = 0;
        addMenuItemPanel.add(itemPriceLabel, constraints);

        JTextField itemPriceField = new JTextField(20);
        constraints.gridx = 1;
        addMenuItemPanel.add(itemPriceField, constraints);

        JButton addButton = new JButton("Add Item");
        constraints.gridy = 5;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        addMenuItemPanel.add(addButton, constraints);

        add(addMenuItemPanel, BorderLayout.NORTH);

        JTextArea menuTextArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(menuTextArea);
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            String restaurantId = restaurantIdField.getText();
            String itemId = itemIdField.getText();
            String itemName = itemNameField.getText();
            double itemPrice = Double.parseDouble(itemPriceField.getText());

            restaurantService.addMenuItemToRestaurant(restaurantId, new MenuItem(itemId, itemName, itemPrice));

            JOptionPane.showMessageDialog(this, "Menu item added successfully!");

            Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
            if (restaurant != null) {
                StringBuilder menuText = new StringBuilder();
                for (MenuItem menuItem : restaurant.getMenu().values()) {
                    menuText.append(menuItem.getItemId()).append(" - ")
                            .append(menuItem.getName()).append(": $")
                            .append(menuItem.getPrice()).append("\n");
                }
                menuTextArea.setText(menuText.toString());
            }

            restaurantIdField.setText("");
            itemIdField.setText("");
            itemNameField.setText("");
            itemPriceField.setText("");
        });
    }
}
