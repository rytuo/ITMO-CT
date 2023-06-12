package com.rytuo.stat.event;

import java.util.Collection;
import java.util.Map;
import java.util.Queue;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import com.rytuo.clock.Clock;

public class EventsStatisticImpl implements EventsStatistic {

    private final Clock clock;
    private final Map<String, Queue<Long>> stats = new ConcurrentHashMap<>();

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        stats.putIfAbsent(name, new ConcurrentLinkedQueue<>());
        stats.get(name).add(clock.now().toEpochMilli());
    }

    @Override
    public Double getEventStatisticByName(String name) {
        if (!stats.containsKey(name)) {
            return 0d;
        }
        Queue<Long> queue = stats.get(name);
        long time = clock.now().minusSeconds(Clock.SEC_IN_HOUR).toEpochMilli();
        removeOlder(queue, time);
        return getRPM(queue);
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        long time = clock.now().minusSeconds(Clock.SEC_IN_HOUR).toEpochMilli();
        stats.values().forEach(s -> removeOlder(s, time));
        return stats.entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        e -> getRPM(e.getValue())
                ));
    }

    @Override
    public void printStatistic() {
        StringJoiner result = new StringJoiner("\n");
        getAllEventStatistic().forEach((key, value) -> result.add(key + ": " + value));
        System.out.println(result);
    }

    private static Double getRPM(Collection<Long> stat) {
        return (double)stat.size() / Clock.SEC_IN_HOUR;
    }

    private static void removeOlder(Collection<Long> stat, long time) {
        stat.removeIf(l -> l <= time);
    }
}
