package dev.lpa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoeWarehouse {

  private final ExecutorService consumer;

  public final static String[] PRODUCT_LIST = {
    "Shoe1",
    "Shoe2",
    "Shoe3"
  };
  private List<Order> orders;

  public ShoeWarehouse(int threadNumber) {
    this.orders = new ArrayList<>();
    this.consumer = Executors.newFixedThreadPool(threadNumber);
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
    System.out.println(Thread.currentThread().getName() + " adds" + order);
    notifyAll();
  }

  public synchronized void consumerFulfillOrder() {
      consumer.submit(this::fulfillOrder);
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
    System.out.println("\u001B[34m" + Thread.currentThread().getName() + " fulfills " + order);
    notifyAll();
    return order;
  }

  public void consumerShutdown() {
    consumer.shutdown();
  }

}
