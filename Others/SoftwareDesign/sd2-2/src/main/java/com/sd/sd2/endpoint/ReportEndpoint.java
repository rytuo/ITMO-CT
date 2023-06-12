package com.sd.sd2.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sd.sd2.model.domain.AttendanceStatReport;
import com.sd.sd2.model.domain.DailyAttendanceReport;
import com.sd.sd2.model.query.GetDailyAttendanceQuery;
import com.sd.sd2.model.query.GetAttendanceStatQuery;
import com.sd.sd2.service.query.ReportQueryHandler;

@RestController
@RequestMapping("/report")
public class ReportEndpoint {

    private final ReportQueryHandler reportQueryHandler;

    @Autowired
    public ReportEndpoint(ReportQueryHandler reportQueryHandler) {
        this.reportQueryHandler = reportQueryHandler;
    }

    @GetMapping("/daily")
    public DailyAttendanceReport getDailyAttendance(
            @RequestParam long id
    ) {
        return reportQueryHandler.getDailyAttendance(
                new GetDailyAttendanceQuery(id)
        );
    }

    @GetMapping("/mean")
    public AttendanceStatReport getAttendanceStat(
            @RequestParam long id
    ) {
        return reportQueryHandler.getAttendanceStat(
                new GetAttendanceStatQuery(id)
        );
    }
}
