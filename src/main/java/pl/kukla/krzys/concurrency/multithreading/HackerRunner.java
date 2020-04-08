package pl.kukla.krzys.concurrency.multithreading;

import pl.kukla.krzys.concurrency.multithreading.hackers.AscendingHackerThread;
import pl.kukla.krzys.concurrency.multithreading.hackers.DescendingHackerThread;
import pl.kukla.krzys.concurrency.multithreading.hackers.PasswordProperties;
import pl.kukla.krzys.concurrency.multithreading.hackers.PoliceThread;
import pl.kukla.krzys.concurrency.multithreading.hackers.Vault;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Krzysztof Kukla
 */
public class HackerRunner {

    public static void main(String[] args) {
        int randomValue = new Random().nextInt(PasswordProperties.MAX_PASSWORD);
        Vault vault = new Vault(randomValue);
        Thread ascendingHackerThread = new AscendingHackerThread(vault);
        Thread descendingHackerThread = new DescendingHackerThread(vault);
        Thread policeThread = new PoliceThread();

        List<Thread> threadList = Arrays.asList(ascendingHackerThread, descendingHackerThread, policeThread);
        System.out.println("Random value is " + randomValue);
        threadList.forEach(Thread::start);
    }

}
