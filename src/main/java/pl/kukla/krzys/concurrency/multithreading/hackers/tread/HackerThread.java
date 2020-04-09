package pl.kukla.krzys.concurrency.multithreading.hackers.tread;

import pl.kukla.krzys.concurrency.multithreading.hackers.domain.Vault;

/**
 * @author Krzysztof Kukla
 */
public abstract class HackerThread extends Thread {
    protected Vault vault;

    public HackerThread(Vault vault) {
        this.vault = vault;
        this.setName(this.getClass().getSimpleName());
        this.setPriority(MAX_PRIORITY);
    }

    @Override
    public void start() {
        System.out.println("Starting thread " + this.getName());
        super.start();
    }

    void checkPassword(Thread hackerThread, int guess) {
        System.out.println(hackerThread.getName() + " checking, current guess is " + guess);
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (vault.getPassword() == guess) {
            System.out.println(this.getName() + " guessed the password " + guess);
            System.exit(0);
        }
    }

}
