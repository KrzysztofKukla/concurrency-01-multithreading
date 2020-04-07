package pl.kukla.krzys.concurrency.multithreading.basic;

import org.junit.jupiter.api.Test;

/**
 * @author Krzysztof Kukla
 */
public class Basic {

    @Test
    void newThread() throws Exception {
        Thread thread = new Thread(() -> {
            System.out.println("Current thread name-> " + Thread.currentThread().getName());
            System.out.println(" with priority->" + Thread.currentThread().getPriority());
        });

        thread.setName("New Worker Thread");
        thread.setPriority(Thread.MAX_PRIORITY);

        //breakpoints freeze all threads
        System.out.println(Thread.currentThread().getName() + " before starting a new Thread");

        //it starts new Thread and passing to Operation System
        thread.start();

        System.out.println(Thread.currentThread().getName() + " after starting a new Thread");

        //sleep current thread fo 1 second
//        Thread.sleep(1000);
    }

    @Test
    void exceptionForEntireThread() throws Exception {
        Thread thread = new Thread(() -> {
            throw new RuntimeException("Intentional message");
        });

        // here we set exception handler for entire thread
        // that handle will be called if any Exception was thrown inside the thread and did not get caught anywhere ( nigdzie indziej nie został
        // złapany )
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Critical error happened inside thread " + e.getMessage());
                //in real application this could be a place where we can clean up some of the resources
                // or log additional data
            }
        });

        //it starts new Thread and passing to Operation System
        thread.start();

        System.out.println(Thread.currentThread().getName() + " after starting a new Thread");

    }

}
