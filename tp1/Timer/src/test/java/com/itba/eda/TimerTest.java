package com.itba.eda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimerTest {
    private final static long TEST_MILLIS = 9213043123L;

    @Test
    @DisplayName("Should output correct duration in milliseconds")
    public void testDuration() {
        Timer timer = new Timer(0);
        timer.stop(TEST_MILLIS);

        assertEquals(TEST_MILLIS, timer.getElapsedMillis());
    }
}
