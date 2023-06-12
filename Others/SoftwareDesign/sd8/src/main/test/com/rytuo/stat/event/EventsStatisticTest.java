package com.rytuo.stat.event;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rytuo.clock.Clock;
import com.rytuo.clock.SettableClock;

import static org.assertj.core.api.Assertions.assertThat;

public class EventsStatisticTest {

    private final Random random = new Random();
    private SettableClock clock;
    private EventsStatistic eventsStatistic;

    @BeforeEach
    void beforeEach() {
        clock = new SettableClock(Instant.now());
        eventsStatistic = new EventsStatisticImpl(clock);
    }

    @Test
    void testGetEventStatisticByName() {
        eventsStatistic.incEvent("test1");
        assertThat(eventsStatistic.getEventStatisticByName("test1"))
                .isEqualTo(1d / Clock.SEC_IN_HOUR);
    }

    @Test
    void testInvalidName() {
        assertThat(eventsStatistic.getEventStatisticByName("test5"))
                .isEqualTo(0d);
        assertThat(eventsStatistic.getAllEventStatistic())
                .isEmpty();
    }

    @Test
    void testExpiredEvent() {
        String name = "test1";
        eventsStatistic.incEvent(name);
        assertThat(eventsStatistic.getEventStatisticByName(name))
                .isEqualTo(1d / Clock.SEC_IN_HOUR);
        assertThat(eventsStatistic.getAllEventStatistic())
                .hasSize(1)
                .containsEntry(name, 1d / Clock.SEC_IN_HOUR);
        clock.addHour();
        assertThat(eventsStatistic.getEventStatisticByName(name))
                .isEqualTo(0d);
        assertThat(eventsStatistic.getAllEventStatistic())
                .hasSize(1)
                .containsEntry(name, 0d);
    }

    @Test
    void testExpiredAndNewEvents() {
        String name1 = "test1", name2 = "test2";
        eventsStatistic.incEvent(name1);
        clock.addHour();
        eventsStatistic.incEvent(name2);
        assertThat(eventsStatistic.getEventStatisticByName(name1))
                .isEqualTo(0d);
        assertThat(eventsStatistic.getEventStatisticByName(name2))
                .isEqualTo(1d / Clock.SEC_IN_HOUR);
    }

    @Test
    void testGetAllEventStatistic() {
        String name1 = "test1",
                name2 = "test2",
                name3 = "test3";

        int n1 = random.nextInt(1000),
                n2 = random.nextInt(1000),
                n3 = random.nextInt(1000);

        for (int i = 0; i < n1; i++) {
            eventsStatistic.incEvent(name1);
        }
        for (int i = 0; i < n2; i++) {
            eventsStatistic.incEvent(name2);
        }
        for (int i = 0; i < n3; i++) {
            eventsStatistic.incEvent(name3);
        }

        assertThat(eventsStatistic.getAllEventStatistic())
                .hasSize(3)
                .containsEntry(name1, (double)n1 / Clock.SEC_IN_HOUR)
                .containsEntry(name2, (double)n2 / Clock.SEC_IN_HOUR)
                .containsEntry(name3, (double)n3 / Clock.SEC_IN_HOUR);
    }

    @Test
    void testManyEvents() {
        int n = 1000;
        for (int i = 0; i < n; i++) {
            int m = random.nextInt(1, n);
            for (int j = 0; j < m; j++) {
                eventsStatistic.incEvent("test" + i + "_" + m);
            }
        }

        var statistic = eventsStatistic.getAllEventStatistic();
        assertThat(statistic)
                .hasSize(n)
                .allSatisfy((name, value) -> {
                    assertThat(name).matches(Pattern.compile("^test[0-9]+_[0-9]+$"));
                    double times = Double.parseDouble(name.split("_")[1]);
                    assertThat(value).isEqualTo(times / Clock.SEC_IN_HOUR);
                });
    }

    @Test
    void testMultithreading() throws InterruptedException {
        Map<String, Integer> stat = new ConcurrentHashMap<>();
        CountDownLatch latch = new CountDownLatch(2);
        Thread[] threads = new Thread[4];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                Random r = new Random();
                for (int j = 0; j < 1000_000; j++) {
                    int n = r.nextInt(10);
                    eventsStatistic.incEvent("test" + n);
                    stat.merge("test" + n, 1, Integer::sum);
                }
                latch.countDown();
            });
            threads[i].start();
        }
        latch.await();
        assertThat(eventsStatistic.getAllEventStatistic())
                .hasSameSizeAs(stat)
                .allSatisfy((k, v) -> assertThat(v).isEqualTo((double)stat.get(k) / Clock.SEC_IN_HOUR));
    }
}
