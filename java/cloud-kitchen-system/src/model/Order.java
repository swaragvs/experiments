package model;


import java.io.Serializable;
import java.util.HashMap;

// Import MenuItem from the correct package
import model.MenuItem;

public class Order implements Serializable {
    private String orderId;
    private String userId;
    private String restaurantId;
    // For simple UI integration we store single itemId and price per order (GUI expects these)
    private String itemId;
    private double price;
    private HashMap<MenuItem, Integer> items;
    private String status;
    private String deliveryPersonId;

    public Order(String orderId, String userId, String restaurantId) {
        this.orderId = orderId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.items = new HashMap<>();
        this.status = "Pending";
    }

    // Constructor used by GUI when creating an order for a single item
    public Order(String orderId, String userId, String restaurantId, String itemId, double price) {
        this(orderId, userId, restaurantId);
        this.itemId = itemId;
        this.price = price;
    }

    // Getters and setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public HashMap<MenuItem, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<MenuItem, Integer> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(String deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    // New getters used by GUI
    public String getItemId() {
        return itemId;
    }

    public double getPrice() {
        return price;
    }
}
