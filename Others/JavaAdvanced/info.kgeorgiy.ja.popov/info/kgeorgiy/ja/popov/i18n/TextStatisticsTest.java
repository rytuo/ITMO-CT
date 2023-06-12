package info.kgeorgiy.ja.popov.i18n;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;

import java.util.Locale;

@FixMethodOrder
public class TextStatisticsTest {

    private static final String TEXT = "Duis nec zzzzzz est gravida, tincidunt urna at, finibus eros. Nunc in elementum velit. Fusce a imperdiet felis. Donec pulvinar massa eros, ac auctor magna tristique id. Morbi et mattis ligula. Curabitur quis erat eros. Aenean ornare tortor enim, ut auctor dolor egestas sed. Nulla a magna enim. Sed commodo urna porttitor urna blandit rhoncus. Aliquam accumsan, nulla non fermentum consectetur, sem magna blandit diam, ut iaculis orci justo in turpis. Aliquam accumsan, nulla non fermentum consectetur, sem magna blandit diam, ut iaculis orci justo in turpis.";

    private static final String RUSSIAN_TEXT = "Кружок по рисованию пройдет 27.08.01 в доме искусств. С собой иметь 2 кисточки 32 упаковки краски и бумагу. Стоимость посещения $3 для детей и $6 для взрослых вместе с детьми.";
    private static final Locale LOCALE_RU = new Locale("ru", "RU");
    private static final Locale LOCALE_EN = Locale.US;

    @Test
    public void test01_sentences() {
        assertStatistic(TextStatistics.getStatistics(TEXT, LOCALE_EN)[0],
                11, 10,
                "Aenean ornare tortor enim, ut auctor dolor egestas sed.",
                "Sed commodo urna porttitor urna blandit rhoncus.",
                "Nulla a magna enim.",
                "Aliquam accumsan, nulla non fermentum consectetur, sem magna blandit diam, ut iaculis orci justo in turpis.",
                "49.9091");
    }

    @Test
    public void test02_words() {
        assertStatistic(TextStatistics.getStatistics(TEXT, LOCALE_EN)[1],
                87, 59, "a", "zzzzzz", "a", "consectetur", "5.1954");
    }

    @Test
    public void test03_numbers() {
        assertEmptyStatistic(TextStatistics.getStatistics(TEXT, LOCALE_RU)[2]);
        String new_text = addWords("100", "000000", "-0241.20", "-00,00", "111,222", "$100");
        assertStatistic(TextStatistics.getStatistics(new_text, LOCALE_EN)[2],
                5, 4, "000000", "111,222", null, null, "22312.6400");
    }

    @Test
    public void test04_currencies() {
        assertEmptyStatistic(TextStatistics.getStatistics(TEXT, LOCALE_RU)[3]);
        String new_text = addWords("$test", "$1", "4$", "$1.0", "$4");
        assertStatistic(TextStatistics.getStatistics(new_text, LOCALE_EN)[3],
                3, 2, "$1", "$4", null, null, "$2.00");
    }

    @Test
    public void test05_dates() {
        assertEmptyStatistic(TextStatistics.getStatistics(TEXT, LOCALE_RU)[4]);
        String new_text = addWords("1.1.00", "01.01.2000", "1/1/11", "4314", "30.12.99");
        assertStatistic(TextStatistics.getStatistics(new_text, LOCALE_RU)[4],
                3, 2, "30.12.99", "1.1.00", null, null, "31.12.1999");
    }

    @Test
    public void test06_russian() {
        Statistics[] statistics = TextStatistics.getStatistics(RUSSIAN_TEXT, LOCALE_RU);
        assertStatistic(statistics[0], 3, 3, "Кружок по рисованию пройдет 27.08.01 в доме искусств.",
                "Стоимость посещения $3 для детей и $6 для взрослых вместе с детьми.",
                "Кружок по рисованию пройдет 27.08.01 в доме искусств.",
                "Стоимость посещения $3 для детей и $6 для взрослых вместе с детьми.",
                "57.6667");
        assertStatistic(statistics[1], 25, 23, "бумагу", "упаковки", "в", "рисованию", "5.1200");
        assertStatistic(statistics[2], 2, 2, "2", "32", null, null, "17.0000");
        assertEmptyStatistic(statistics[3]);
        assertStatistic(statistics[4], 1, 1, "27.08.01", "27.08.01", null, null, "27.08.2001");
    }

    private void assertStatistic(Statistics statistics, long entries, long uniqueEntries,
                                 String min, String max, String minLen, String maxLen, String average) {
        Assert.assertEquals(statistics.entries, entries);
        Assert.assertEquals(statistics.uniqueEntries, uniqueEntries);
        Assert.assertEquals(statistics.min, min);
        Assert.assertEquals(statistics.max, max);
        Assert.assertEquals(statistics.minLen, minLen);
        Assert.assertEquals(statistics.maxLen, maxLen);
        Assert.assertEquals(statistics.average, average);
    }

    private void assertEmptyStatistic(Statistics statistics) {
        assertStatistic(statistics, 0, 0, null, null, null, null, null);
    }

    private String addWords(String... words) {
        String[] before = TEXT.split(" ");
        String[] after = new String[before.length + words.length];
        int n = before.length / words.length + 1;
        after[0] = before[0];
        for (int i = 1, j = 1, k = 0; i < after.length; ++i) {
            after[i] = i % n == 0 ? words[k++] : before[j++];
        }
        return String.join(" ", after);
    }
}
