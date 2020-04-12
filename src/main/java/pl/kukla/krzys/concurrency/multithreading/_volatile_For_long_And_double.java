package pl.kukla.krzys.concurrency.multithreading;

import java.util.Random;

/**
 * @author Krzysztof Kukla
 */
public class _volatile_For_long_And_double {

    public static void main(String[] args) {
        Metrics metrics = new Metrics();

        BusinessLogic businessLogicThread1 = new BusinessLogic(metrics);
        BusinessLogic businessLogicThread2 = new BusinessLogic(metrics);

        PrintMetrics printMetricsThread = new PrintMetrics(metrics);

        businessLogicThread1.start();
        businessLogicThread2.start();
        printMetricsThread.start();
    }

    private static class PrintMetrics extends Thread {
        private Metrics metrics;

        private PrintMetrics(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //getAverage() method is not synchronized, so we guaranteed that we will not slow down business logic threads( nie będziemy spowalniać
                // wykonania logiki biznesowej )
                double currentAverage = metrics.getAverage();

                System.out.println("Current average is-> " + currentAverage);
            }
        }

    }

    private static class BusinessLogic extends Thread {
        private Metrics metrics;
        private Random random = new Random();

        private BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();

                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                long end = System.currentTimeMillis();
                metrics.addSample(end - start);
            }
        }

    }

    private static class Metrics {
        private long count = 0;

        //long and double are not safe thread/synchronized to read or write to or from so we need to declare it as 'volatile'
        //this guarantee 'average' will be atomic
        private volatile double average = 0.0;

        public void addSample(long sample) {
            double currentSum = average * count;
            count++;
            average = (currentSum + sample) / count;
        }

        //primitive types and reference are atomic so we dont need to specify it as synchronized

        public double getAverage() {
            return average;
        }

    }

}
