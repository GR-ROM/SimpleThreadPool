package su.grinev;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomThreadPool  {

    private final BlockingQueue<Runnable> tasks;
    private final ArrayList<Thread> threadList;
    private final ArrayList<Worker> workerList;
    private final CountingSemaphore onCompletionMutex;
    private int activeThreads;

    public CustomThreadPool(int threads) {
        if (threads==0) throw new IllegalArgumentException();
        this.activeThreads=0;
        this.tasks=new LinkedBlockingQueue<>();
        this.onCompletionMutex=new CountingSemaphore(0);
        this.threadList=new ArrayList<>();
        this.workerList=new ArrayList<>();
        for (int i=0;i!=threads;i++){
            Worker worker=new Worker(tasks, onCompletionMutex);
            workerList.add(worker);
            threadList.add(new Thread(worker));
        }
    }

    public void enqueueTask(Runnable task){
        try {
            tasks.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startExecutor(){
        threadList.forEach(t->{
            t.start();
            activeThreads++;
            onCompletionMutex.countUp();
        });
    }

    public void waitForComplete(){
            try {
                onCompletionMutex.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public void purgeQueue(){
        tasks.clear();
    }

}
