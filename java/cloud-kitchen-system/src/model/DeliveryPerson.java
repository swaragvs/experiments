package model;

import model.Order; // Adjust the package name as necessary

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeliveryPerson implements Serializable {
    private String deliveryPersonId;
    private String name;
    private String currentLocation;
    private List<Order> assignedOrders;

    public DeliveryPerson(String deliveryPersonId, String name, String currentLocation) {
        this.deliveryPersonId = deliveryPersonId;
        this.name = name;
        this.currentLocation = currentLocation;
        this.assignedOrders = new ArrayList<>();
    }

    // Getters and setters
    public String getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(String deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public List<Order> getAssignedOrders() {
        return assignedOrders;
    }

    public void setAssignedOrders(List<Order> assignedOrders) {
        this.assignedOrders = assignedOrders;
    }
}
