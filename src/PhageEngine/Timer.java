package PhageEngine;

public class Timer extends Loop {

    private long startTime;
    private long time;
    private long pausedTime;
    private boolean running;

    public Timer() {
        addObject(this);

        reset();
        running = false;
        time = 0;
        pausedTime = 0;
        startTime = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time){
        reset();
        startTime = System.currentTimeMillis() - time;
    }

    public boolean isRunning() {
        return running;
    }

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
        pausedTime = time;
    }

    public void reset() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onUpdate() {
        if (!running) {
            startTime = System.currentTimeMillis() - pausedTime;
        }
        time = System.currentTimeMillis() - startTime;
    }
}
