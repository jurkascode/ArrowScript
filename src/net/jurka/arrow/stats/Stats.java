package net.jurka.arrow.stats;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by E6420 on 11.01.14.
 */
public class Stats {

    private static Stats instance;

    private long startTime = 0l;

    private AtomicInteger logsCut = new AtomicInteger(0);

    private AtomicInteger clicks = new AtomicInteger(0);

    private AtomicInteger missClicks = new AtomicInteger(0);

    private AtomicInteger arrowShaft = new AtomicInteger(0);

    private String status = "";

    protected Stats() {
        startTime = System.currentTimeMillis();
        status = "Waiting...";

    }

    public static Stats getInstance() {
        if (instance == null) {
            instance = new Stats();
        }
        return instance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getLogsCut() {
        return logsCut.get();
    }

    public void incrLogsCut() {
        this.logsCut.incrementAndGet();
    }

    public int getClicks() {
        return clicks.get();
    }

    public void incrClicks() {
        this.clicks.incrementAndGet();
    }

    public int getMissClicks() {
        return missClicks.get();
    }

    public void incrMissClicks() {
        this.missClicks.incrementAndGet();
    }


    public void setArrowShaft(int ammount) {
        this.arrowShaft.set(ammount);
    }

    public int getArrowShaft() {
        return arrowShaft.get();
    }

    public void incArrowShaft(int i) {
        arrowShaft.set(arrowShaft.get() + i);
    }
}
