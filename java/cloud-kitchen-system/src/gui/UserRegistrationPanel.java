package gui;

import service.UserService;

import javax.swing.*;
import java.awt.*;

public class UserRegistrationPanel extends JPanel {
    private final UserService userService = new UserService();

    public UserRegistrationPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("User Registration");
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

        JLabel nameLabel = new JLabel("Name:");
        constraints.gridy = 2;
        constraints.gridx = 0;
        add(nameLabel, constraints);

        JTextField nameField = new JTextField(20);
        constraints.gridx = 1;
        add(nameField, constraints);

        JLabel emailLabel = new JLabel("Email:");
        constraints.gridy = 3;
        constraints.gridx = 0;
        add(emailLabel, constraints);

        JTextField emailField = new JTextField(20);
        constraints.gridx = 1;
        add(emailField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridy = 4;
        constraints.gridx = 0;
        add(passwordLabel, constraints);

        JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        add(passwordField, constraints);

        JLabel phoneLabel = new JLabel("Phone:");
        constraints.gridy = 5;
        constraints.gridx = 0;
        add(phoneLabel, constraints);

        JTextField phoneField = new JTextField(20);
        constraints.gridx = 1;
        add(phoneField, constraints);

        JButton registerButton = new JButton("Register");
        constraints.gridy = 6;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        add(registerButton, constraints);

        registerButton.addActionListener(e -> {
            String userId = userIdField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String phone = phoneField.getText();

            userService.registerUser(userId, name, email, password, phone);

            JOptionPane.showMessageDialog(this, "User registered successfully!");

            userIdField.setText("");
            nameField.setText("");
            emailField.setText("");
            passwordField.setText("");
            phoneField.setText("");
        });
    }
}
