#ifndef METOPT_ABSTRACT_ALGO_H
#define METOPT_ABSTRACT_ALGO_H

#include <functional>
#include <cmath>

namespace algo {
    typedef const std::function<double(double)> &func;

    /// golden ratio for golden section method
    const double GOLDEN_RATIO = (1 + sqrt(5)) / 2;

    /// Counts function calls
    size_t number_of_iterations = 0;

    /**
     * Dichotomy method of finding min value
     * @require a < b && function unimodal in [a, b]
     * @param f function for research
     * @param a left border
     * @param b right border
     * @param eps absolute accuracy
     * @return Min value in given range with given accuracy
     */
    double dichotomy(func f, double a, double b, double eps);

    /**
     * Golden section method of finding min value
     * @require a < b && function unimodal in [a, b]
     * @param f function for research
     * @param a left border
     * @param b right border
     * @param eps absolute accuracy
     * @return Min value in given range with given accuracy
     */
    double golden_section(func f, double a, double b, double eps);

    /**
     * Fibonacci method of finding min value
     * @require a < b && function unimodal in [a, b]
     * @param f function for research
     * @param a left border
     * @param b right border
     * @param eps absolute accuracy
     * @return Min value in given range with given accuracy
     */
    double fibonacci(func f, double a, double b, double eps);

    /**
     * Parabolic method of finding min value
     * @require a < b && function unimodal in [a, b]
     * @param f function for research
     * @param a left border
     * @param b right border
     * @param eps absolute accuracy
     * @return Min value in given range with given accuracy
     */
    double parabolic(func f, double a, double b, double eps);

    /**
     * Combined Brent method of finding min value
     * @require a < b && function unimodal in [a, b]
     * @param f function for research
     * @param a left border
     * @param b right border
     * @param eps absolute accuracy
     * @return Min value in given range with given accuracy
     */
    double brent(func f, double a, double c, double eps);

    /**
     * Dichotomy method of finding min value
     * @require a < b && function unimodal in [a, b]
     * @param f function for research
     * @param a left border
     * @param b right border
     * @param eps absolute accuracy
     * @return Min value in given range with given accuracy, writes data to dichotomy.csv
     */
    double dichotomy_csv(func f, double a, double b, double eps);

    /**
     * Golden section method of finding min value
     * @require a < b && function unimodal in [a, b]
     * @param f function for research
     * @param a left border
     * @param b right border
     * @param eps absolute accuracy
     * @return Min value in given range with given accuracy, writes data to golden_section.csv
     */
    double golden_section_csv(func f, double a, double b, double eps);

    /**
     * Fibonacci method of finding min value
     * @require a < b && function unimodal in [a, b]
     * @param f function for research
     * @param a left border
     * @param b right border
     * @param eps absolute accuracy
     * @return Min value in given range with given accuracy, writes data to fibonacci.csv
     */
    double fibonacci_csv(func f, double a0, double b0, double eps);

    /**
     * Parabolic method of finding min value
     * @require a < b && function unimodal in [a, b]
     * @param f function for research
     * @param a left border
     * @param b right border
     * @param eps absolute accuracy
     * @return Min value in given range with given accuracy, writes data to parabolic.csv
     */
    double parabolic_csv(func f, double a, double b, double eps);

    /**
     * Combined Brent method of finding min value
     * @require a < b && function unimodal in [a, b]
     * @param f function for research
     * @param a left border
     * @param b right border
     * @param eps absolute accuracy
     * @return Min value in given range with given accuracy, writes data to brent.csv
     */
    double brent_csv(func f, double a, double c, double eps);

    /**
     * Function creating all.csv data file
     * @param f function for research
     * @param a left border
     * @param b right border
     * @param eps absolute accuracy
     */
    void all_csv(func f, double a, double c, double eps);

    /**
     * Function creating eps.csv data file
     * @param f function for research
     * @param a left border
     * @param b right border
     * @param eps absolute accuracy
     */
    void eps_csv(func f, double a, double c, double eps);

    /**
     * Function creating all .csv files
     * @param f function for research
     * @param a left border
     * @param b right border
     * @param eps absolute accuracy
     */
    void create_csv(func f, double a, double c, double eps);
}


#endif //METOPT_ABSTRACT_ALGO_H
