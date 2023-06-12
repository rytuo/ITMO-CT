package com.sd.sd2.model.domain;

import java.time.LocalDateTime;
import java.util.Map;

public record DailyAttendanceReport(long id, Map<LocalDateTime, Integer> dailyAttendance) {
}
