package su.grinev;

import java.util.concurrent.atomic.AtomicInteger;

public class CountingSemaphore {

    private AtomicInteger counter;

    public CountingSemaphore(int counter) {
        this.counter=new AtomicInteger(counter);
    }

    public void countUp() {
        this.counter.incrementAndGet();
    }

    public void countDown() {
        if (this.counter.decrementAndGet() == 0)
            synchronized (this) {
                this.notify();
            }
    }

    public void await() throws InterruptedException {
        if (this.counter.get() > 0) {
            synchronized (this) {
                this.wait();
            }
        }
    }

}