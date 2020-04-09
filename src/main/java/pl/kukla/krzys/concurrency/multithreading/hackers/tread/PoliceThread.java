package pl.kukla.krzys.concurrency.multithreading.hackers.tread;

/**
 * @author Krzysztof Kukla
 */
public class PoliceThread extends Thread {
    @Override
    public void run() {
        for (int i = 20; i > 0; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Seconds left-> " + i);
        }

        System.out.println("Game over for your hackers");
        System.exit(0);
    }

    @Override
    public synchronized void start() {
        this.setName(this.getClass().getSimpleName());
        System.out.println("Starting " + this.getName() + " thread");
        super.start();
    }

}
