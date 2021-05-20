package hcmus.client.gui;

public class TimerRunnable implements Runnable {

    private volatile boolean running = true;
    private int timer;
    private Runnable fn;

    public void terminate() {
        running = false;
    }

    public TimerRunnable(Runnable fn, int timer) {
        this.fn = fn;
        this.timer = timer;
    }

    @Override
    public void run() {
        while (running) {
            fn.run();
            try {
                Thread.sleep(timer);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }
}
