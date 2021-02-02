package su.grinev;

import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable {

    private BlockingQueue<Runnable> tasks;
    private volatile boolean stop;
    private CountingSemaphore onCompletion;

    public Worker(BlockingQueue<Runnable> tasks, CountingSemaphore onCompletion){
        this.tasks=tasks;
        this.stop=false;
        this.onCompletion=onCompletion;
    }

    public void EnqueueTask(Runnable task) {
        try {
            tasks.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            Runnable task = null;
            synchronized (tasks) {
                if (tasks.size() == 0) this.onCompletion.countDown();
            }
            try {
                task=tasks.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            task.run();
        }
    }
}
