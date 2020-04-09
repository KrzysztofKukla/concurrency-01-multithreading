package pl.kukla.krzys.concurrency.multithreading;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Krzysztof Kukla
 */
//factorial calculation ( pol. obliczanie silni )
public class FactorialCalculation {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(11111L, 22222L, 33L, 333333L, 4444L, 5555L, 666666L, 77777L, 8888L, 9999L);

        List<FactorialThread> threads = new ArrayList<>();

        inputNumbers.forEach((i) -> {
            FactorialThread factorialThread = new FactorialThread(i);
            threads.add(factorialThread);

            //to avoid waiting until finish all threads, we make all thread as deamons
            factorialThread.setDaemon(true);

            //race condition between 'factorialThread.start()' where the factorialThread is starting
            // and 'if (factorialThread.isFinished)' where the Main thread is checking result
            //we don't know which one will be in which stage
            factorialThread.start();
        });

        for (Thread t : threads) {
            //waiting at most {100} miliseconds until the Thread will be finished/terminated
            //always use 'join(100)' with value
            t.join(100);
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            //we add 'join' to force main Thread to wait until all FactorialThreads are finished
            if (factorialThread.isFinished) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("the calculation of " + inputNumbers.get(i) + " is still in progress");
            }
        }

    }

    static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        private BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = 1; i <= n; i++) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }

    }

}
