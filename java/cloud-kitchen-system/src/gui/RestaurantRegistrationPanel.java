package gui;

import service.RestaurantService;

import javax.swing.*;
import java.awt.*;

public class RestaurantRegistrationPanel extends JPanel {
    private final RestaurantService restaurantService = new RestaurantService();

    public RestaurantRegistrationPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Restaurant Registration");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(titleLabel, constraints);

        constraints.gridwidth = 1;

        JLabel restaurantIdLabel = new JLabel("Restaurant ID:");
        constraints.gridy = 1;
        constraints.gridx = 0;
        add(restaurantIdLabel, constraints);

        JTextField restaurantIdField = new JTextField(20);
        constraints.gridx = 1;
        add(restaurantIdField, constraints);

        JLabel nameLabel = new JLabel("Name:");
        constraints.gridy = 2;
        constraints.gridx = 0;
        add(nameLabel, constraints);

        JTextField nameField = new JTextField(20);
        constraints.gridx = 1;
        add(nameField, constraints);

        JLabel addressLabel = new JLabel("Address:");
        constraints.gridy = 3;
        constraints.gridx = 0;
        add(addressLabel, constraints);

        JTextField addressField = new JTextField(20);
        constraints.gridx = 1;
        add(addressField, constraints);

        JButton registerButton = new JButton("Register");
        constraints.gridy = 4;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        add(registerButton, constraints);

        registerButton.addActionListener(e -> {
            String restaurantId = restaurantIdField.getText();
            String name = nameField.getText();
            String address = addressField.getText();

            restaurantService.addRestaurant(restaurantId, name, address);

            JOptionPane.showMessageDialog(this, "Restaurant registered successfully!");

            restaurantIdField.setText("");
            nameField.setText("");
            addressField.setText("");
        });
    }
}
