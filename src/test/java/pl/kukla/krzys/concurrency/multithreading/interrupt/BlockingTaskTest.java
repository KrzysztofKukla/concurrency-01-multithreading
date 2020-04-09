package pl.kukla.krzys.concurrency.multithreading.interrupt;

import org.junit.jupiter.api.Test;
import pl.kukla.krzys.concurrency.multithreading.BlockingTask;

/**
 * @author Krzysztof Kukla
 */
class BlockingTaskTest {

    @Test
    void interruptedTest() throws Exception {
        Thread thread = new BlockingTask();

        thread.start();
    }

}