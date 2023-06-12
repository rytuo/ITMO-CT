package com.sd.sd2.service.query;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sd.sd2.model.domain.AttendanceStatReport;
import com.sd.sd2.model.domain.DailyAttendanceReport;
import com.sd.sd2.model.event.Event;
import com.sd.sd2.model.query.GetAttendanceStatQuery;
import com.sd.sd2.model.query.GetDailyAttendanceQuery;
import com.sd.sd2.repository.event.EventRepositoryDAL;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

@Service
public class ReportQueryHandler implements PostInsertEventListener {

    private final Map<Long, UserStats> stats = new HashMap<>();

    @Autowired
    private EventRepositoryDAL eventRepositoryDAL;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    private void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(this);

        initStats();
    }

    private void initStats() {
        for (var event : eventRepositoryDAL.getAll()) {
            processEvent(event);
        }
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {
        if (!(event.getEntity() instanceof Event e)) {
            return;
        }
        processEvent(e);
    }

    private void processEvent(Event event) {
        switch (event.getEventType()) {
            case CREATE_SUBSCRIPTION -> stats.put(event.getSubscriptionId(), new UserStats());
            case ENTER -> stats.get(event.getSubscriptionId()).entered(event.getTimestamp());
            case EXIT -> stats.get(event.getSubscriptionId()).exited(event.getTimestamp());
        }
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {
        return false;
    }


    public DailyAttendanceReport getDailyAttendance(GetDailyAttendanceQuery query) {
        UserStats stat = stats.getOrDefault(query.id(), null);
        if (stat == null) {
            throw new RuntimeException("No such user found");
        }
        return new DailyAttendanceReport(query.id(), stat.getDailyAttendanceStat());
    }

    public AttendanceStatReport getAttendanceStat(GetAttendanceStatQuery query) {
        UserStats stat = stats.getOrDefault(query.id(), null);
        if (stat == null) {
            throw new RuntimeException("No such user found");
        }
        return new AttendanceStatReport(query.id(), stat.getMeanAttendance(), stat.getMeanDuration());
    }

    private static class UserStats {
        private final Map<LocalDateTime, Integer> dailyAttendance = new HashMap<>();
        private Date lastUnfinishedVisitEnter = null;
        private long totalAttendance = 0;
        private long totalDuration = 0;

        public void entered(Date date) {
            lastUnfinishedVisitEnter = date;
        }

        public void exited(Date date) {
            if (lastUnfinishedVisitEnter == null) {
                return;
            }

            LocalDateTime localDate = lastUnfinishedVisitEnter.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .atStartOfDay();

            dailyAttendance.merge(localDate, 1, (k, v) -> v + 1);
            totalAttendance += 1;
            totalDuration += date.getTime() - lastUnfinishedVisitEnter.getTime();

            lastUnfinishedVisitEnter = null;
        }

        public double getMeanAttendance() {
            return totalAttendance == 0 ? 0 : ((double) totalAttendance / dailyAttendance.size());
        }

        public double getMeanDuration() {
            return totalAttendance == 0 ? 0 : ((double)totalDuration / totalAttendance / 1000);
        }

        public Map<LocalDateTime, Integer> getDailyAttendanceStat() {
            return dailyAttendance;
        }
    }
}
