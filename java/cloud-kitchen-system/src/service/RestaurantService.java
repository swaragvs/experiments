package service;
import gui.MainFrame;
import model.MenuItem; // Import MenuItem if it's defined in the model package

import model.Restaurant;
import util.FileUtil;

import java.util.HashMap;

public class RestaurantService {
    private HashMap<String, Restaurant> restaurants;

    public RestaurantService() {
        restaurants = FileUtil.loadRestaurants();
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.put(restaurant.getRestaurantId(), restaurant);
        FileUtil.saveRestaurants(restaurants);
    }

    // Convenience method expected by GUI
    public void addRestaurant(String restaurantId, String name, String address) {
        Restaurant restaurant = new Restaurant(restaurantId, name, address);
        addRestaurant(restaurant);
    }

    public Restaurant getRestaurantById(String restaurantId) {
        return restaurants.get(restaurantId);
    }

    public void addMenuItemToRestaurant(String restaurantId, MenuItem menuItem) {
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant != null) {
            restaurant.getMenu().put(menuItem.getItemId(), menuItem);
            FileUtil.saveRestaurants(restaurants);
        }
    }

    public void updateRestaurantMenu(String restaurantId, HashMap<String, MenuItem> menu) {
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant != null) {
            restaurant.setMenu(menu);
            FileUtil.saveRestaurants(restaurants);
        }
    }

    // Other methods related to restaurant management
}
