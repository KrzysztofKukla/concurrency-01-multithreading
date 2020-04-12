package pl.kukla.krzys.concurrency.multithreading.interrupt;

import org.junit.jupiter.api.Test;
import pl.kukla.krzys.concurrency.multithreading.InterruptThread;

/**
 * @author Krzysztof Kukla
 */
class InterruptThreadTest {

    @Test
    void interruptedTest() throws Exception {
        Thread thread = new InterruptThread();

        thread.start();
    }

}