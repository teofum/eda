package com.itba.eda;

import org.joda.time.Instant;

public class TimerJodaTester {
    public static void main(String[] args) {
        var timer = new TimerJoda();
        timer.stop(Instant.now().plus(99381239));

        System.out.println(timer);
    }
}
