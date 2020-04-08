package pl.kukla.krzys.concurrency.multithreading.hackers;

/**
 * @author Krzysztof Kukla
 */
public class DescendingHackerThread extends HackerThread {

    public DescendingHackerThread(Vault vault) {
        super(vault);
    }

    @Override
    public void run() {
        for (int guess = PasswordProperties.MAX_PASSWORD; guess >= 0; guess--) {
            checkPassword(this, guess);
        }
    }

}
