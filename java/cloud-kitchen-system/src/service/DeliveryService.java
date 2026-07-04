package service;


import gui.MainFrame;
import model.DeliveryPerson;
import model.Order;
import util.FileUtil;

import java.util.HashMap;

public class DeliveryService {
    private HashMap<String, DeliveryPerson> deliveryPeople;

    public DeliveryService() {
        deliveryPeople = FileUtil.loadDeliveryPeople();
    }

    public void assignOrder(String deliveryPersonId, Order order) {
        DeliveryPerson deliveryPerson = deliveryPeople.get(deliveryPersonId);
        if (deliveryPerson != null) {
            deliveryPerson.getAssignedOrders().add(order);
            FileUtil.saveDeliveryPeople(deliveryPeople);
        }
    }

    public void updateDeliveryPersonLocation(String deliveryPersonId, String newLocation) {
        DeliveryPerson deliveryPerson = deliveryPeople.get(deliveryPersonId);
        if (deliveryPerson != null) {
            deliveryPerson.setCurrentLocation(newLocation);
            FileUtil.saveDeliveryPeople(deliveryPeople);
        }
    }

    // Other methods to manage delivery personnel
}
