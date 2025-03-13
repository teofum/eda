package com.itba.eda;

public class Timer {
    private static final long NANOS_TO_MILLIS = 1_000_000;
    private static final long MILLIS_TO_SECONDS = 1_000;
    private static final long SECONDS_TO_MINUTES = 60;
    private static final long MINUTES_TO_HOURS = 60;
    private static final long HOURS_TO_DAYS = 24;

    private final long start;
    private long elapsed = -1;

    Timer() {
        start = System.nanoTime();
    }

    Timer(long start) {
        this.start = start;
    }

    public void stop() {
        var end = System.nanoTime();
        elapsed = end - start;
    }

    public void stop(long endMillis) {
        var end = endMillis * NANOS_TO_MILLIS;

        if (end < start) throw new RuntimeException("Timer stopped before start");
        elapsed = end - start;
    }

    public long getElapsedMillis() {
        return elapsed / NANOS_TO_MILLIS;
    }

    public long getMillis() {
        return getElapsedMillis() % MILLIS_TO_SECONDS;
    }

    public long getSeconds() {
        return getElapsedMillis() / MILLIS_TO_SECONDS % SECONDS_TO_MINUTES;
    }

    public long getMinutes() {
        return getElapsedMillis() / (MILLIS_TO_SECONDS * SECONDS_TO_MINUTES) % MINUTES_TO_HOURS;
    }

    public long getHours() {
        return getElapsedMillis() / (MILLIS_TO_SECONDS * SECONDS_TO_MINUTES * MINUTES_TO_HOURS) % HOURS_TO_DAYS;
    }

    public long getDays() {
        return getElapsedMillis() / (MILLIS_TO_SECONDS * SECONDS_TO_MINUTES * MINUTES_TO_HOURS * HOURS_TO_DAYS);
    }

    @Override
    public String toString() {
        if (elapsed < 0) throw new RuntimeException("Invalid time interval!");

        var millis = getMillis();
        var seconds = getSeconds();
        var minutes = getMinutes();
        var hours = getHours();
        var days = getDays();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("(%d ms) ", getElapsedMillis()));
        if (days > 0) sb.append(String.format("%d days ", days));
        if (days > 0 || hours > 0) sb.append(String.format("%d hours ", hours));
        if (days > 0 || hours > 0 || minutes > 0) sb.append(String.format("%d minutes ", minutes));
        sb.append(String.format("%d.%d s", seconds, millis));

        return sb.toString();
    }
}
