package dev.lpa;

import java.util.ArrayList;
import java.util.List;

public class Producer implements Runnable {

  ShoeWarehouse shoeWarehouse;
  List<Order> orders;

  public Producer(ShoeWarehouse shoeWarehouse, List<Order> orders) {
    this.shoeWarehouse = shoeWarehouse;
    this.orders = new ArrayList<>(orders);
  }

  @Override
  public void run() {
    for (var order : orders) {
      shoeWarehouse.receiveOrder(order);
    }
  }
}
