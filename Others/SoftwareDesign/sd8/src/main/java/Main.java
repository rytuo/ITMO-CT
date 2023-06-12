import java.util.Random;

import com.rytuo.clock.Clock;
import com.rytuo.clock.NormalClock;
import com.rytuo.stat.event.EventsStatistic;
import com.rytuo.stat.event.EventsStatisticImpl;

public class Main {
    public static void main(String... args) {
        Clock clock = new NormalClock();
        EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < random.nextInt(10000); j++) {
                eventsStatistic.incEvent("test" + i);
            }
        }
        eventsStatistic.printStatistic();
    }
}
