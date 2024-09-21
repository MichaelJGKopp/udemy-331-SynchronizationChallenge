package dev.lpa;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

  public static void main(String[] args) {

    Random random = new Random();
    ShoeWarehouse shoeWarehouse = new ShoeWarehouse(2);

//    Callable<Order> orderingTask = () -> {
//      Order order = generateOrder(random);
//      try {
//        Thread.sleep(random.nextInt(500, 1000));
//        shoeWarehouse.receiveOrder(order);
//      } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//      }
//      return order;
//    };

    ExecutorService orderingService = Executors.newCachedThreadPool();

//    List<Callable<Order>> tasks = Collections.nCopies(15, orderingTask);
//    try {
//      List<Future<Order>> futures = orderingService.invokeAll(tasks);
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }

    try {
//      Thread.sleep(random.nextInt(500), 2000);
      for (int j = 0; j < 15; j++) {
        Thread.sleep(random.nextInt(500), 2000);
        orderingService.submit(() -> shoeWarehouse.receiveOrder(generateOrder(random)));
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    for (int i = 0; i < 15; i++) {
      shoeWarehouse.consumerFulfillOrder();
    }

    orderingService.shutdown();
    shoeWarehouse.consumerShutdown();
    try {
      orderingService.awaitTermination(6, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private static Order generateOrder(Random random) {
    return new Order(
      random.nextLong(1000000, 9999999),
      ShoeWarehouse.PRODUCT_LIST[random.nextInt(0, 3)],
      random.nextInt(0, 10));
  }
}
