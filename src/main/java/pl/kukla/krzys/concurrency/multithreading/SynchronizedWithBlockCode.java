package pl.kukla.krzys.concurrency.multithreading;

/**
 * @author Krzysztof Kukla
 */
//atomic operation allows to run everything in single operation
//a++ is actually 3 operations
public class SynchronizedWithBlockCode {

    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();

        Thread incrementThread = new IncrementThread(inventoryCounter);
        Thread decrementThread = new DecrementThread(inventoryCounter);

        incrementThread.start();
        //wait until incrementThread finish
//        incrementThread.join(1000);

        //then start second Thread
        decrementThread.start();
        //wait until incrementThread finish
//        decrementThread.join(1000);

        System.out.println("Currently we have " + inventoryCounter.getItems() + " items");
    }

    private static class IncrementThread extends Thread {
        private InventoryCounter inventoryCounter;

        private IncrementThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }

    }

    private static class DecrementThread extends Thread {
        private InventoryCounter inventoryCounter;

        private DecrementThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }

    }

    private static class InventoryCounter {
        private int items = 0;

        Object object = new Object();

        public void increment() {
            synchronized (object) {
                items++;
            }
        }

        public void decrement() {
            synchronized (object) {
                items--;
            }
        }

        public int getItems() {
            synchronized (object) {
                return items;
            }
        }

    }

}
