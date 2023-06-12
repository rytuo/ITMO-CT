package com.rytuo.clock;

import java.time.Instant;

public interface Clock {
    long SEC_IN_HOUR = 60 * 60;
    Instant now();
}
