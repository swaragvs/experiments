package service;

import gui.MainFrame;
import model.Order;
import util.FileUtil;

import java.util.HashMap;

public class OrderService {
    private HashMap<String, Order> orders;

    public OrderService() {
        orders = FileUtil.loadOrders();
        if (orders == null) {
            orders = new HashMap<>();
        }
    }

    // Method name expected by GUI
    public void createOrder(Order order) {
        placeOrder(order);
    }

    public void placeOrder(Order order) {
        orders.put(order.getOrderId(), order);
        FileUtil.saveOrders(orders);
    }

    public void cancelOrder(String orderId) {
        orders.remove(orderId);
        FileUtil.saveOrders(orders);
    }

    public java.util.List<Order> getOrdersByUserId(String userId) {
        java.util.List<Order> result = new java.util.ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getUserId() != null && order.getUserId().equals(userId)) {
                result.add(order);
            }
        }
        return result;
    }

    // Other methods related to order management
}
