package dev.lpa;

import java.util.ArrayList;
import java.util.List;

public class ShoeWarehouse {

  public static List<String> products = new ArrayList<>(List.of(
    "Shoe1",
    "Shoe2",
    "Shoe3"
  ));
  private List<Order> orders;

  public ShoeWarehouse() {
    this.orders = new ArrayList<>();
  }

  public synchronized void receiveOrder(Order order) {

    while (!orders.isEmpty()) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    orders.add(order);
    System.out.println("Add" + order);
    notify();
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
