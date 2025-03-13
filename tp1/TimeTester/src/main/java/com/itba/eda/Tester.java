package com.itba.eda;

import org.joda.time.Instant;

public class Tester {
    public static void main(String[] args) {
        TimerJoda myCrono = new TimerJoda(Instant.ofEpochMilli(10));
        myCrono.stop(Instant.ofEpochMilli(10 + 93623040));
        System.out.println(myCrono);
    }
}
