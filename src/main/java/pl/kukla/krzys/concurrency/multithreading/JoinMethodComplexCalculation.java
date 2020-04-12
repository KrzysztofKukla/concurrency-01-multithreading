package pl.kukla.krzys.concurrency.multithreading;

import java.math.BigInteger;

/**
 * @author Krzysztof Kukla
 */
public class JoinMethodComplexCalculation {

    public static void main(String[] args) throws InterruptedException {
        BigInteger base1 = new BigInteger("10");
        BigInteger power1 = new BigInteger("3");

        BigInteger base2 = new BigInteger("20");
        BigInteger power2 = new BigInteger("3");

        BigInteger result = new JoinMethodComplexCalculation().calculateResult(base1, power1, base2, power2);
        System.out.println("Result-> " + result);
    }

    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        BigInteger result = BigInteger.ZERO;

        PowerCalculatingThread powerThread1 = new PowerCalculatingThread(base1, power1);
        PowerCalculatingThread powerThread2 = new PowerCalculatingThread(base2, power2);
        powerThread1.start();
        powerThread2.start();
        powerThread1.join(500);
        powerThread2.join(500);

        return powerThread1.getResult().add(powerThread2.getResult());
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            result = base;
            for (BigInteger i = BigInteger.ONE; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
        }

        public BigInteger getResult() {
            return result;
        }

    }

}
