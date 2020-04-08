package pl.kukla.krzys.concurrency.multithreading.hackers;

/**
 * @author Krzysztof Kukla
 */
public class AscendingHackerThread extends HackerThread {

    public AscendingHackerThread(Vault vault) {
        super(vault);
    }

    @Override
    public void run() {
        for (int guess = 0; guess < PasswordProperties.MAX_PASSWORD; guess++) {
            checkPassword(this, guess);
        }
    }

}
