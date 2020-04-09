package pl.kukla.krzys.concurrency.multithreading;

/**
 * @author Krzysztof Kukla
 */
public class InterruptTask extends Thread {
    public static void main(String[] args) {
        Thread thread = new BlockingTaskThread();

        thread.start();

        //main Thread is interrupting other 'thread'
        thread.interrupt();
    }

    private static class BlockingTaskThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }

    }

}
