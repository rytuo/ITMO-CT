package info.kgeorgiy.ja.popov.i18n;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TextStatistics {

    private static final int DATE_FORMAT = DateFormat.SHORT;
    private static final int COLLATOR_MODE = Collator.IDENTICAL;

    public static void main(String[] args) {
        if (args == null || args.length != 4 || Arrays.stream(args).anyMatch(Objects::isNull)) {
            System.out.printf("Usage:%n    TextStatistics textLocale outputLocale inputFile reportFile%n");
            return;
        }

        Locale textLocale = getLocale(args[0], "input");
        Locale outputLocale = getLocale(args[1], "output");
        if (textLocale == null || outputLocale == null) {
            return;
        }
        ResourceBundle bundle = getBundle(outputLocale);
        if (bundle == null) {
            System.out.println("Unsupported output locale, please select english or russian.");
            return;
        }
        String text;
        try {
            text = Files.readString(Path.of(args[2]));
        } catch (IOException e) {
            System.out.println("Can't read input file. Path should be from the content root");
            return;
        }

        Statistics[] statistics = getStatistics(text, textLocale);
        String report = createReport(args[2], bundle, statistics);

        try {
            Files.writeString(Path.of(args[3]), report);
        } catch (IOException e) {
            System.out.println("Can't write output file");
        }
    }

    private static Locale getLocale(String localeString, String name) {
        Locale locale = getLocale(localeString);
        try {
            if (locale != null && locale.getISO3Language() != null && locale.getISO3Country() != null) {
                return locale;
            }
        } catch (MissingResourceException ignored) {
        }
        System.out.println("Invalid " + name + " locale format, should be: language[_country[_variant]]");
        return null;
    }

    private static Locale getLocale(String locale) {
        String[] parts = locale.split("_");
        switch (parts.length) {
            case 3:
                return new Locale(parts[0], parts[1], parts[2]);
            case 2:
                return new Locale(parts[0], parts[1]);
            case 1:
                return new Locale(parts[0]);
            default:
                return null;
        }
    }

    private static ResourceBundle getBundle(Locale locale) {
        switch (locale.getISO3Language()) {
            case "rus":
                return ResourceBundle.getBundle("info.kgeorgiy.ja.popov.i18n.UsageResourceBundle_ru");
            case "eng":
                return ResourceBundle.getBundle("info.kgeorgiy.ja.popov.i18n.UsageResourceBundle_en");
            default:
                return null;
        }
    }

    public static Statistics[] getStatistics(String text, Locale locale) {
        List<String> sentences = split(text, BreakIterator.getSentenceInstance(locale));
        List<String> words = new ArrayList<>();
        List<String> numbers = new ArrayList<>();
        List<String> sums = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        DateFormat dateFormat = DateFormat.getDateInstance(DATE_FORMAT, locale);
        Collator collator = Collator.getInstance(locale);
        collator.setStrength(COLLATOR_MODE);

        split(text, BreakIterator.getWordInstance(locale)).forEach(token -> {
            try {
                if (dateFormat.parse(token) != null) {
                    dates.add(token);
                    return;
                }
            } catch (ParseException ignored) {
            }
            try {
                if (currencyFormat.parse(token) != null) {
                    sums.add(token);
                    return;
                }
            } catch (ParseException ignored) {
            }
            if (token.codePoints().anyMatch(Character::isLetter)) {
                words.add(token);
                return;
            }
            try {
                if (numberFormat.parse(token) != null) {
                    numbers.add(token);
                }
            } catch (ParseException ignored) {
            }
        });

        return new Statistics[]{countStringStatistics(sentences, collator),
                countStringStatistics(words, collator),
                countNumberStatistics(numbers, numberFormat),
                countCurrenciesStatistics(sums, currencyFormat),
                countDateStatistics(dates, dateFormat)};
    }

    private static List<String> split(final String text, final BreakIterator boundary) {
        boundary.setText(text);
        final List<String> parts = new ArrayList<>();
        for (
                int begin = boundary.first(), end = boundary.next();
                end != BreakIterator.DONE;
                begin = end, end = boundary.next()
        ) {
            parts.add(text.substring(begin, end));
        }
        return parts.stream().map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
    }

    private static final Function<Double, String> doubleOutput = d -> String.format("%.4f", d);

    private static Statistics countStringStatistics(List<String> tokens, Collator collator) {
        Statistics statistics = countStatistics(tokens, collator, s -> s, s -> (double)s.length(), doubleOutput);
        Comparator<String> lengthComparator = Comparator.comparingInt(String::length);
        statistics.minLen = tokens.stream().min(lengthComparator).orElse("");
        statistics.maxLen = tokens.stream().max(lengthComparator).orElse("");
        return statistics;
    }

    private static Function<String, Double> toDoubleGenerator(NumberFormat format) {
        return s -> {
            try {
                return format.parse(s).doubleValue();
            } catch (ParseException ignored) {
                return 0.0;
            }
        };
    }

    private static Statistics countNumberStatistics(List<String> tokens, NumberFormat format) {
        Function<String, Double> toDouble = toDoubleGenerator(format);
        Comparator<String> comp = Comparator.comparingDouble(toDouble::apply);
        return countStatistics(tokens, comp, s -> toDouble.apply(s).toString(), toDouble, doubleOutput);
    }

    private static Statistics countCurrenciesStatistics(List<String> tokens, NumberFormat format) {
        Function<String, Double> toDouble = toDoubleGenerator(format);
        Comparator<String> comp = Comparator.comparingDouble(toDouble::apply);
        return countStatistics(tokens, comp, s -> toDouble.apply(s).toString(), toDouble, d -> format.format(d.longValue()));
    }

    private static Statistics countDateStatistics(List<String> tokens, DateFormat format) {
        Function<String, Double> toDouble = s -> {
            try {
                return (double)format.parse(s).getTime();
            } catch (ParseException ignored) {
                return 0.0;
            }
        };
        Comparator<String> comp = Comparator.comparingDouble(toDouble::apply);
        return countStatistics(tokens, comp, d -> toDouble.apply(d).toString(), toDouble, d -> format.format(new Date(d.longValue())));
    }

    private static Statistics countStatistics(List<String> tokens, Comparator<? super String> comparator, Function<String, String> toDistinct,
                                                  Function<String, Double> toDouble, Function<Double, String> toStr) {
        Statistics statistics = new Statistics();
        statistics.entries = tokens.size();
        if (statistics.entries > 0) {
            statistics.uniqueEntries = tokens.stream().map(toDistinct).distinct().count();
            statistics.min = tokens.stream().min(comparator).orElse(null);
            statistics.max = tokens.stream().max(comparator).orElse(null);
            statistics.average = toStr.apply(tokens.stream().map(toDouble)
                    .reduce(0.0, Double::sum) / statistics.entries);
        }
        return statistics;
    }

    public static String createReport(String file, ResourceBundle bundle, Statistics[] statistics) {
        return String.format("%s%n%s%n%s%n%s%n%s%n%s%n%s",
                createHeader(bundle, file),
                createSummaryReport(bundle, statistics),
                createUnitReport(bundle, statistics[0], "sentence", true),
                createUnitReport(bundle, statistics[1], "word", true),
                createUnitReport(bundle, statistics[2], "number", false),
                createUnitReport(bundle, statistics[3], "sum", false),
                createUnitReport(bundle, statistics[4], "date", false));
    }

    private static String createHeader(ResourceBundle bundle, String file) {
        return String.format("%s \"%s\"", bundle.getString("Analyzed file"), file);
    }

    private static String createSummaryReport(ResourceBundle bundle, Statistics[] statistics) {
        return String.format("%s%n\t%s: %d.%n\t%s: %d.%n\t%s: %d.%n\t%s: %d.%n\t%s: %d.",
                bundle.getString("Summary statistics"),
                bundle.getString("Number of sentences"), statistics[0].entries,
                bundle.getString("Number of words"), statistics[1].entries,
                bundle.getString("Number of numbers"), statistics[2].entries,
                bundle.getString("Number of sums"), statistics[3].entries,
                bundle.getString("Number of dates"), statistics[4].entries);
    }

    private static String createUnitReport(ResourceBundle bundle, Statistics statistics, String unit, boolean isString) {
        String result = String.format("%s%n\t%s: %d",
                bundle.getString("Statistics for " + unit + "s"),
                bundle.getString("Number of " + unit + "s"), statistics.entries);
        if (statistics.entries > 1) {
            result += String.format(" (%d %s).", statistics.uniqueEntries, bundle.getString("distinct"));
        } else {
            result += ".";
        }
        if (statistics.entries > 0) {
            if (isString) {
                result += String.format("%n\t%s: \"%s\".%n\t%s: \"%s\".%n\t%s: %d (\"%s\").%n\t%s: %d (\"%s\").%n\t%s: %s.",
                        bundle.getString("Minimal " + unit), statistics.min,
                        bundle.getString("Maximal " + unit), statistics.max,
                        bundle.getString("Minimal length of " + unit), statistics.minLen.length(), statistics.minLen,
                        bundle.getString("Maximal length of " + unit), statistics.maxLen.length(), statistics.maxLen,
                        bundle.getString("Average length of " + unit), statistics.average);
            } else {
                result += String.format("%n\t%s: %s.%n\t%s: %s.%n\t%s: %s.",
                        bundle.getString("Minimal " + unit), statistics.min,
                        bundle.getString("Maximal " + unit), statistics.max,
                        bundle.getString("Average " + unit), statistics.average);
            }
        }
        return result;
    }
}
