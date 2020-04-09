package pl.kukla.krzys.concurrency.multithreading.interrupt;

import org.junit.jupiter.api.Test;
import pl.kukla.krzys.concurrency.multithreading.InterruptTask;

/**
 * @author Krzysztof Kukla
 */
class InterruptTaskTest {

    @Test
    void interruptedTest() throws Exception {
        Thread thread = new InterruptTask();

        thread.start();
    }

}