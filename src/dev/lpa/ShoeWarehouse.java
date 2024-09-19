package dev.lpa;

import java.util.ArrayList;
import java.util.List;

public class ShoeWarehouse {

  public final static String[] PRODUCT_LIST = {
    "Shoe1",
    "Shoe2",
    "Shoe3"
  };
  private List<Order> orders;

  public ShoeWarehouse() {
    this.orders = new ArrayList<>();
  }

  public synchronized void receiveOrder(Order order) {

    while (orders.size() >= 8) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    orders.add(order);
    System.out.println("Add" + order);
    notifyAll();
  }

  public synchronized Order fulfillOrder() {

    while (orders.isEmpty()) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    Order order = orders.removeFirst();
    System.out.println(Thread.currentThread().getName() + " Fulfill " + order);
    notifyAll();
    return order;
  }

}
