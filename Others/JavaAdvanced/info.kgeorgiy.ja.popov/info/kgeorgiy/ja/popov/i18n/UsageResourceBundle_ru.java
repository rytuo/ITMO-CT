package info.kgeorgiy.ja.popov.i18n;

import java.util.*;

public class UsageResourceBundle_ru extends ListResourceBundle {
    private static final Object[][] CONTENTS = {
            {"Analyzed file", "Анализируемый файл"},
            {"Summary statistics", "Сводная статистика"},
            {"Number of sentences", "Число предложений"},
            {"Number of words", "Число слов"},
            {"Number of numbers", "Число чисел"},
            {"Number of sums", "Число сумм"},
            {"Number of dates", "Число дат"},
            {"Statistics for sentences", "Статистика по предложениям"},
            {"Minimal sentence", "Минимальное предложение"},
            {"Maximal sentence", "Максимальное предложение"},
            {"Minimal length of sentence", "Минимальная длина предложения"},
            {"Maximal length of sentence", "Максимальная длина предложения"},
            {"Average length of sentence", "Средняя длина предложения"},
            {"Statistics for words", "Статистика по словам"},
            {"Minimal word", "Минимальное слово"},
            {"Maximal word", "Максимальное слово"},
            {"Minimal length of word", "Минимальная длина слова"},
            {"Maximal length of word", "Максимальная длина слова"},
            {"Average length of word", "Средняя длина слова"},
            {"Statistics for numbers", "Статистика по числам"},
            {"Minimal number", "Минимальное число"},
            {"Maximal number", "Максимальное число"},
            {"Average number", "Среднее число"},
            {"Statistics for sums", "Статистика по суммам"},
            {"Minimal sum", "Минимальная сумма"},
            {"Maximal sum", "Максимальная сумма"},
            {"Average sum", "Средняя сумма"},
            {"Statistics for dates", "Статистика по датам"},
            {"Minimal date", "Минимальная дата"},
            {"Maximal date", "Максимальная дата"},
            {"Average date", "Средняя дата"},
            {"distinct", "различных"}
    };

    protected Object[][] getContents() {
        return CONTENTS;
    }
}
