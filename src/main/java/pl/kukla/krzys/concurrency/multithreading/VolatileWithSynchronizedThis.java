package pl.kukla.krzys.concurrency.multithreading;

/**
 * @author Krzysztof Kukla
 */
public class VolatileWithSynchronizedThis {

    public static void main(String[] args) {
        Metrics metrics = new Metrics();
        BusinessLogicThread businessLogicThread1 = new BusinessLogicThread(metrics);
        BusinessLogicThread businessLogicThread2 = new BusinessLogicThread(metrics);
        PrintThread printThread = new PrintThread(metrics);

        businessLogicThread1.start();
        businessLogicThread2.start();
        printThread.start();
    }

    private static class PrintThread extends Thread {
        private Metrics metrics;

        private PrintThread(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Current->" + metrics.getCurrent() + " min->" + metrics.getMin() + " max->" + metrics.getMax());
            }
        }

    }

    private static class BusinessLogicThread extends Thread {
        private Metrics metrics;

        private BusinessLogicThread(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                long end = System.currentTimeMillis();

                metrics.addSample(end - start);
            }
        }

    }

    private static class Metrics {
        private long current;
        private volatile long min;
        private volatile long max;

        // Add all necessary member variables

        /**
         * Initializes all member variables
         */
        public Metrics() {
            min = Long.MAX_VALUE;
            max = Long.MIN_VALUE;
        }

        /**
         * Adds a new sample to our metrics.
         */
        public void addSample(long newSample) {
            current = newSample;
            synchronized (this) {
                min = Math.min(min, current);
                max = Math.max(max, current);
            }
        }

        /**
         * Returns the smallest sample we've seen so far.
         */
        public long getMin() {
            return min;
        }

        /**
         * Returns the biggest sample we've seen so far.
         */
        public long getMax() {
            return max;
        }

        public long getCurrent() {
            return current;
        }

    }

}
