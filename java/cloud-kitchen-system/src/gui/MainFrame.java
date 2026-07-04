package gui;


import javax.swing.*;
import java.awt.*;
import gui.UserRegistrationPanel;
import gui.UserLoginPanel;
import gui.RestaurantRegistrationPanel;
import gui.RestaurantMenuPanel;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Food Delivery System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Create the navigation panel
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(1, 4));

        JButton userRegButton = new JButton("User Registration");
        JButton userLoginButton = new JButton("User Login");
        JButton restaurantRegButton = new JButton("Restaurant Registration");
        JButton restaurantMenuButton = new JButton("Manage Restaurant");

        navPanel.add(userRegButton);
        navPanel.add(userLoginButton);
        navPanel.add(restaurantRegButton);
        navPanel.add(restaurantMenuButton);

        add(navPanel, BorderLayout.NORTH);

        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);

        UserRegistrationPanel userRegPanel = new UserRegistrationPanel();
        UserLoginPanel userLoginPanel = new UserLoginPanel();
        RestaurantRegistrationPanel restaurantRegPanel = new RestaurantRegistrationPanel();
        RestaurantMenuPanel restaurantMenuPanel = new RestaurantMenuPanel();

        contentPanel.add(userRegPanel, "User Registration");
        contentPanel.add(userLoginPanel, "User Login");
        contentPanel.add(restaurantRegPanel, "Restaurant Registration");
        contentPanel.add(restaurantMenuPanel, "Manage Restaurant");

        add(contentPanel, BorderLayout.CENTER);

        userRegButton.addActionListener(e -> cardLayout.show(contentPanel, "User Registration"));
        userLoginButton.addActionListener(e -> cardLayout.show(contentPanel, "User Login"));
        restaurantRegButton.addActionListener(e -> cardLayout.show(contentPanel, "Restaurant Registration"));
        restaurantMenuButton.addActionListener(e -> cardLayout.show(contentPanel, "Manage Restaurant"));

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
