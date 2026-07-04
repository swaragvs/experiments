package util;

import model.User;
import model.Restaurant;
import model.Order;
import model.DeliveryPerson;
import model.MenuItem;

import java.io.*;
import java.util.HashMap;

public class FileUtil {
    private static final String USERS_FILE = "users.ser";
    private static final String RESTAURANTS_FILE = "restaurants.ser";
    private static final String ORDERS_FILE = "orders.ser";
    private static final String DELIVERY_PEOPLE_FILE = "delivery_people.ser";
    private static final String MENU_ITEMS_FILE = "menu_items.ser";

    public static void saveUsers(HashMap<String, User> users) {
        saveObject(users, USERS_FILE);
    }

    public static HashMap<String, User> loadUsers() {
        Object obj = loadObject(USERS_FILE);
        if (obj == null) {
            return new HashMap<>();
        }
        return (HashMap<String, User>) obj;
    }

    public static void saveRestaurants(HashMap<String, Restaurant> restaurants) {
        saveObject(restaurants, RESTAURANTS_FILE);
    }

    public static HashMap<String, Restaurant> loadRestaurants() {
        Object obj = loadObject(RESTAURANTS_FILE);
        if (obj == null) {
            return new HashMap<>();
        }
        return (HashMap<String, Restaurant>) obj;
    }
    public static void saveOrders(HashMap<String, Order> orders) {
        saveObject(orders, ORDERS_FILE);
    }

    public static HashMap<String, Order> loadOrders() {
        Object obj = loadObject(ORDERS_FILE);
        if (obj == null) {
            return new HashMap<>();
        }
        return (HashMap<String, Order>) obj;
    }

    public static void saveDeliveryPeople(HashMap<String, DeliveryPerson> deliveryPeople) {
        saveObject(deliveryPeople, DELIVERY_PEOPLE_FILE);
    }

    public static HashMap<String, DeliveryPerson> loadDeliveryPeople() {
        Object obj = loadObject(DELIVERY_PEOPLE_FILE);
        if (obj == null) {
            return new HashMap<>();
        }
        return (HashMap<String, DeliveryPerson>) obj;
    }

    public static void saveMenuItems(HashMap<String, MenuItem> menuItems) {
        saveObject(menuItems, MENU_ITEMS_FILE);
    }

    public static HashMap<String, MenuItem> loadMenuItems() {
        Object obj = loadObject(MENU_ITEMS_FILE);
        if (obj == null) {
            return new HashMap<>();
        }
        return (HashMap<String, MenuItem>) obj;
    }

    private static void saveObject(Object obj, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(obj);
            System.out.println(fileName + " saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object loadObject(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = ois.readObject();
            System.out.println(fileName + " loaded successfully.");
            return obj;
        } catch (java.io.FileNotFoundException e) {
            // It's okay if the serialized file doesn't exist yet; return null so callers can initialize empty maps.
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
