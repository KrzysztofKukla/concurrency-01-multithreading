package pl.kukla.krzys.concurrency.multithreading.hackers.tread;

import pl.kukla.krzys.concurrency.multithreading.hackers.domain.PasswordProperties;
import pl.kukla.krzys.concurrency.multithreading.hackers.domain.Vault;

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
