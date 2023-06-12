#ifndef METOPT_TESTS_H
#define METOPT_TESTS_H

#include <iostream>
#include "methods.h"

double eps = 10e-7;

void test_newtons(extended_function &f, const vector_ &x0) {
    vector_ x;
    x = classic_newton(f, x0, eps);
    x = search_newton(f, x0, eps);
    x = descent_newton(f, x0, eps);
}

void test_newtons_random() {
    auto func1 = [](const vector_ &x) {
        return std::pow(x[0], 2) + std::pow(x[1], 2);
    };
    auto grad1 = [](const vector_ &x) {
        return vector_{2 * x[0], 2 * x[1]};
    };
    auto gess1 = [](const vector_ &x) {
        return matrix_{{2, 0}, {0, 2}};
    };
    extended_function f1 = extended_function(func1, grad1, gess1);
    test_newtons(f1, {5, 5});

    auto func2 = [](const vector_ &x) {
        return 2 * std::pow(x[0], 2) + 2 * std::pow(x[1], 2) + 5 * x[0] - 5 * x[1] + 10;
    };
    auto grad2 = [](const vector_ &x) {
        return vector_ {4 * x[0] + 5, 4 * x[1] - 5};
    };
    auto gess2 = [](const vector_ &x) {
        return matrix_ {{4, 0}, {0, 4}};
    };
    extended_function f2 = extended_function(func2, grad2, gess2);
    test_newtons(f2, {5, 5});

    auto func3 = [](const vector_ &x) {
        return std::log(std::pow(x[0], 2) + std::pow(x[1], 2) + 1);
    };
    auto grad3 = [](const vector_ &x) {
        return vector_ {2 * x[0] / (std::pow(x[0], 2) + std::pow(x[1], 2) + 1), 2 * x[1] / (std::pow(x[0], 2) + std::pow(x[1], 2) + 1)};
    };
    auto gess3 = [](const vector_ &x) {
        return matrix_ {{2 * (x[1] * x[1] - x[0] * x[0] + 1) / pow((x[0] * x[0] + x[1] * x[1] + 1), 2), -4 * x[0] * x[1] / pow((x[0] * x[0] + x[1] * x[1] + 1), 2)},
                        {-4 * x[0] * x[1] / pow((x[0] * x[0] + x[1] * x[1] + 1), 2), 2 * (-x[1] * x[1] + x[0] * x[0] + 1) / pow((x[0] * x[0] + x[1] * x[1] + 1), 2)}};
    };
    extended_function f3 = extended_function(func3, grad3, gess3);
    test_newtons(f3, {0.5, 0.5});
    test_newtons(f3, {0.05, 0.05});
    test_newtons(f3, {0.00005, 0.00005});
}

void test_newtons_fixed() {
    auto func1 = [](const vector_ &x) {
        return std::pow(x[0], 2) + std::pow(x[1], 2) - 1.2 * x[0] * x[1];
    };
    auto grad1 = [](const vector_ &x) {
        return vector_ {2 * x[0] - 1.2 * x[1], 2 * x[1] - 1.2 * x[0]};
    };
    auto gess1 = [](const vector_ &x) {
        return matrix_ {{2, -1.2},
                        {-1.2, 2}};
    };
    extended_function f1 = extended_function(func1, grad1, gess1);
    test_newtons(f1, {4, 1});

    auto func2 = [](const vector_ &x) {
        return 100 * std::pow(x[0], 4) - 200 * x[0] * x[0] * x[1] + 100 * x[1] * x[1] + x[0] * x[0] + 1 - 2 * x[0];
    };
    auto grad2 = [](const vector_ &x) {
        return vector_ {400 * std::pow(x[0], 3) - 400 * x[0] * x[1] + 2 * x[0] - 2, -200 * x[0] * x[0] + 200 * x[1]};
    };
    auto gess2 = [](const vector_ &x) {
        return matrix_ {{1200 * x[0] * x[0] - 400 * x[1] + 2, -400 * x[0]},
                        {-400 * x[0], 200}};
    };
    extended_function f2(func2, grad2, gess2);
    test_newtons(f2, {-1.2, 1});
}

void test_quazinewtons(extended_function &f, const vector_ &x0) {
    vector_ x;
    x = classic_newton(f, x0, eps);
    x = dfp(f, x0, eps);
    x = pauel(f, x0, eps);
}

void test_quazinewtons_fixed() {
    auto func1 = [](const vector_ &x) {
        return 100 * std::pow(x[0], 4) - 200 * x[0] * x[0] * x[1] + 100 * x[1] * x[1] + x[0] * x[0] + 1 - 2 * x[0];
    };
    auto grad1 = [](const vector_ &x) {
        return vector_ {400 * std::pow(x[0], 3) - 400 * x[0] * x[1] + 2 * x[0] - 2, -200 * x[0] * x[0] + 200 * x[1]};
    };
    auto gess1 = [](const vector_ &x) {
        return matrix_ {{1200 * x[0] * x[0] - 400 * x[1] + 2, -400 * x[0]},
                        {-400 * x[0], 200}};
    };
    extended_function f1 = extended_function(func1, grad1, gess1);
    test_quazinewtons(f1, {0, 0});

    auto func2 = [](const vector_ &x) {
        return std::pow(x[0] * x[0] + x[1] - 11, 2) + std::pow(x[0] + x[1] * x[1] - 7, 2);
    };
    auto grad2 = [](const vector_ &x) {
        return vector_ {4 * std::pow(x[0], 3) + 4 * x[0] * x[1] + 2 * x[1] * x[1] - 42 * x[0] - 14,
                        4 * std::pow(x[1], 3) + 4 * x[0] * x[1] + 2 * x[0] * x[0] - 26 * x[1] - 22};
    };
    auto gess2 = [](const vector_ &x) {
        return matrix_ {{12 * x[0] * x[0] + 4 * x[1] - 42, 4 * x[1] + 4 * x[0]},
                        {4 * x[0] + 4 * x[1], 12 * x[1] * x[1] + 4 * x[0] - 26}};
    };
    extended_function f2 = extended_function(func2, grad2, gess2);
    test_quazinewtons(f2, {0, 0});
}

#endif //METOPT_TESTS_H
