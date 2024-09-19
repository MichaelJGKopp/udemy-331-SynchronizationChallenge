package dev.lpa;

import java.util.Random;

public class Main {

  public static void main(String[] args) {

    Random random = new Random();
    ShoeWarehouse shoeWarehouse = new ShoeWarehouse();

    Thread producer = new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        shoeWarehouse.receiveOrder(new Order(
          random.nextLong(1000000, 9999999),
          ShoeWarehouse.PRODUCT_LIST[random.nextInt(0,3)],
          random.nextInt(0, 10)));
      }
    });
    producer.start();

    for (int i = 0; i < 2; i++) {
      Thread consumer = new Thread(() -> {
        for (int j = 0; j < 5; j++) {
          shoeWarehouse.fulfillOrder();
        }
      });
      consumer.start();
    }

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
