package dev.lpa;

import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {

    ShoeWarehouse shoeWarehouse = new ShoeWarehouse();
    List<Order> orders = new ArrayList<>();
    for (int i = 1; i <= 10; i++) {
      orders.add(new Order(i,"Shoe" + i, i));
    }

    Thread producer = new Thread(new Producer(shoeWarehouse, orders));
    Thread consumer = new Thread(new Consumer(shoeWarehouse));
    Thread consumer2 = new Thread(new Consumer(shoeWarehouse));

    producer.start();
    consumer.start();
    consumer2.start();

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
