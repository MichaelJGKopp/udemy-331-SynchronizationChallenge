package dev.lpa;

public class Consumer implements Runnable {

  private ShoeWarehouse shoeWarehouse;

  public Consumer(ShoeWarehouse shoeWarehouse) {
    this.shoeWarehouse = shoeWarehouse;
  }

  @Override
  public void run() {
    for (int i = 0; i < 5; i++) {
      shoeWarehouse.fulfillOrder();
    }
  }
}
