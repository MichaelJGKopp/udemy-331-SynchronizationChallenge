package dev.lpa;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

  public static void main(String[] args) {

    Random random = new Random();
    ShoeWarehouse shoeWarehouse = new ShoeWarehouse(2);

    Runnable producerRunnable = () -> {
      for (int i = 0; i < 15; i++) {
        shoeWarehouse.receiveOrder(generateOrder(random));
      }
    };
    ExecutorService producer = Executors.newSingleThreadExecutor(
      r -> new Thread(r, "\u001B[33mProducer"));
    producer.execute(producerRunnable);

    for (int i = 0; i < 15; i++) {
      shoeWarehouse.consumerFulfillOrder();
    }

    producer.shutdown();
    shoeWarehouse.consumerShutdown();
  }

  private static Order generateOrder(Random random) {
    return new Order(
      random.nextLong(1000000, 9999999),
      ShoeWarehouse.PRODUCT_LIST[random.nextInt(0, 3)],
      random.nextInt(0, 10));
  }
}
