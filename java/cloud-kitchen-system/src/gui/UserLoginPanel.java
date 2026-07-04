package gui;

import service.UserService;

import javax.swing.*;
import java.awt.*;

public class UserLoginPanel extends JPanel {
    private final UserService userService = new UserService();

    public UserLoginPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("User Login");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(titleLabel, constraints);

        constraints.gridwidth = 1;

        JLabel userIdLabel = new JLabel("User ID:");
        constraints.gridy = 1;
        constraints.gridx = 0;
        add(userIdLabel, constraints);

        JTextField userIdField = new JTextField(20);
        constraints.gridx = 1;
        add(userIdField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridy = 2;
        constraints.gridx = 0;
        add(passwordLabel, constraints);

        JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        add(passwordField, constraints);

        JButton loginButton = new JButton("Login");
        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        add(loginButton, constraints);

        loginButton.addActionListener(e -> {
            String userId = userIdField.getText();
            String password = new String(passwordField.getPassword());

            if (userService.authenticateUser(userId, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                // Navigate to the user's main panel (order panel, etc.)
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials, please try again.");
            }
        });
    }
}
