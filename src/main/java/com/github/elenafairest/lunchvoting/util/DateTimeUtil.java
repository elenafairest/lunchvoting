package com.github.elenafairest.lunchvoting.util;

import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.LocalTime;

@UtilityClass
public class DateTimeUtil {
    public static final LocalTime UPDATE_VOTE_TIME_BORDER = LocalTime.of(11, 0);
    private static Clock clock = Clock.systemDefaultZone();

    public static Clock getClock() {
        return clock;
    }

    public static void setClock(Clock clock) {
        DateTimeUtil.clock = clock;
    }
}