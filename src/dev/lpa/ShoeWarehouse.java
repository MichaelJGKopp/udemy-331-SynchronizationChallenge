package dev.lpa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShoeWarehouse {

  private final ExecutorService consumer;
  private Lock lock = new ReentrantLock();
  private Condition condition = lock.newCondition();

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

  public void receiveOrder(Order order) {

    lock.lock();
    try {
      while (orders.size() >= 20) {
          condition.await(30, TimeUnit.SECONDS);
      }
        orders.add(order);
        System.out.println(Thread.currentThread().getName() + " adds" + order);
        condition.signalAll();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      lock.unlock();
    }
  }

  public void consumerFulfillOrder() {
      consumer.submit(this::fulfillOrder);
  }

  public Order fulfillOrder() {

    Order order = null;
    try {
      lock.lock();
      while (orders.isEmpty()) {
        condition.await(30, TimeUnit.SECONDS);
      }

      order = orders.removeFirst();
      System.out.println("\u001B[34m" + Thread.currentThread().getName() + " fulfills " + order);
      condition.signalAll();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      lock.unlock();
    }
    return order;
  }

  public void consumerShutdown() {
    consumer.shutdown();
  }

}
