package main;

import service.UserService;
import gui.MainFrame;
import model.User;  // Importing classes from model package

import javax.swing.*;
import java.awt.*;

public class Main {
    private static final UserService userService = new UserService();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });

    // Example usage of classes from model package
    // Create a sample user using the available constructor
    User user = new User("u1", "Test User", "test@example.com", "password", "123 Main St");
        // You can use other classes similarly from model package
    }
}
