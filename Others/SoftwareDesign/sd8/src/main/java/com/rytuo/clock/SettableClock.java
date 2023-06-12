package com.rytuo.clock;

import java.time.Instant;

public class SettableClock implements Clock {

    private Instant now;

    public SettableClock(Instant now) {
        this.setNow(now);
    }

    public void setNow(Instant now) {
        this.now = now;
    }

    public void addHour() {
        setNow(now.plusSeconds(SEC_IN_HOUR));
    }

    @Override
    public Instant now() {
        return now;
    }
}
