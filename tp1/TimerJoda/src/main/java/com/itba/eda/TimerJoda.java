package com.itba.eda;

import org.joda.time.Duration;
import org.joda.time.Instant;

public class TimerJoda {
    private final Instant start;
    private Duration elapsed;

    TimerJoda() {
        start = Instant.now();
    }

    TimerJoda(Instant start) {
        this.start = start;
    }

    public void stop(Instant end) {
        elapsed = new Duration(start, end);
    }

    public void stop() {
        stop(Instant.now());
    }

    @Override
    public String toString() {
        var period = elapsed.toPeriod();
        var days = period.getDays();
        var hours = period.getHours();
        var minutes = period.getMinutes();
        var seconds = period.getSeconds();
        var millis = period.getMillis();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("(%d ms) ", elapsed.getMillis()));
        if (days > 0) sb.append(String.format("%d days ", days));
        if (days > 0 || hours > 0) sb.append(String.format("%d hours ", hours));
        if (days > 0 || hours > 0 || minutes > 0) sb.append(String.format("%d minutes ", minutes));
        sb.append(String.format("%d.%d s", seconds, millis));

        return sb.toString();
    }
}
