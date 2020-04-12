package pl.kukla.krzys.concurrency.multithreading;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * @author Krzysztof Kukla
 */
public class ThreadAsDeamon {
    public static void main(String[] args) {

        //if we will pass very big numbers then the calculation takes very long time
        BigInteger base = new BigInteger("200000");
        BigInteger power = new BigInteger("1000000");

        Runnable longComputationTask = new LongComputationTaskThread(base, power);
        Thread thread = new Thread(longComputationTask);

        //Daemons run in background
        // if the main thread terminates then whole app is terminated as well, dont care about thread in deamon
        thread.setDaemon(true);

        thread.start();
        thread.interrupt();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class LongComputationTaskThread implements Runnable {
        private BigInteger base;
        private BigInteger power;

        @Override
        public void run() {
            BigInteger result = pow(base, power);
            System.out.println(base + "^" + power + "=" + result);

        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {

                //here we checks if currentThread got interrupted from outside
                //if true we stops calculation and exit from thread
//                if (Thread.currentThread().isInterrupted()) {
//                    System.out.println("Prematurely interrupted computation");
//                    return BigInteger.ZERO;
//                }
                result = result.multiply(base);
            }
            return result;
        }

    }

}
