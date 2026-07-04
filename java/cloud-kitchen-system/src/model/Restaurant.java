package model;

import model.MenuItem;

import java.io.Serializable;
import java.util.HashMap;

public class Restaurant implements Serializable {
    private String restaurantId;
    private String name;
    private String address;
    private HashMap<String, MenuItem> menu;

    public Restaurant(String restaurantId, String name, String address) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.address = address;
        this.menu = new HashMap<>();
    }

    // Getters and setters
    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HashMap<String, MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(HashMap<String, MenuItem> menu) {
        this.menu = menu;
    }
}
