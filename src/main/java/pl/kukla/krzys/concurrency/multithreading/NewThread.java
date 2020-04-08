package pl.kukla.krzys.concurrency.multithreading;

/**
 * @author Krzysztof Kukla
 */
public class NewThread extends Thread {
    @Override
    public void run() {
        System.out.println("Second Thread-> " + this.getName());
    }

}
