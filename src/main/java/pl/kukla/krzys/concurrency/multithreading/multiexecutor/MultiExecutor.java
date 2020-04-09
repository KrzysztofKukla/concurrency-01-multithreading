package pl.kukla.krzys.concurrency.multithreading.multiexecutor;

import java.util.List;

/**
 * @author Krzysztof Kukla
 */
public class MultiExecutor {
    private List<Runnable> tasks;

    // Add any necessary member variables here

    /*
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        tasks.forEach((t) ->
            new Thread(t).start());
    }

}
