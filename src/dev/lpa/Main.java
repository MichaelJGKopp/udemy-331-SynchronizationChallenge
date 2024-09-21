package dev.lpa;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

  public static void main(String[] args) {

    Random random = new Random();
    ShoeWarehouse shoeWarehouse = new ShoeWarehouse(2);

    Callable<Order> task = () -> {
      Order order = generateOrder(random);
        shoeWarehouse.receiveOrder(order);
        return order;
    };

    List<Callable<Order>> tasks = Collections.nCopies(15, task);
    ExecutorService producer = Executors.newCachedThreadPool(r -> new Thread(r, "\u001B[33mProducer"));
    try {
      producer.invokeAll(tasks);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

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
