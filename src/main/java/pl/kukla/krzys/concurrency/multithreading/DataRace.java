package pl.kukla.krzys.concurrency.multithreading;

/**
 * @author Krzysztof Kukla
 */
public class DataRace {

    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkDataRace();
            }
        });

        thread1.start();
        thread2.start();
    }

    private static class SharedClass {
        //to fix Data Race issue we need to provide synchronized for shared data - variables in this case
        //synchronization of method which modify shared data
        //declaration of shared variables as volatile
        private volatile int x = 0;
        private volatile int y = 0;

        //to optimize performance instructions method are executed out of order
        public synchronized void increment() {
            x++;
            y++;
        }

        public synchronized void checkDataRace() {
            if (x != y) {
                System.out.println("DataRaceException-> x!=y " + x + "->" + y);
            }
        }

    }

}
