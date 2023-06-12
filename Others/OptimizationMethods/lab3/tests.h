#ifndef METOPT_TESTS_H
#define METOPT_TESTS_H

#include "loggers.h"
#include "methods.h"
#include "linear_algebra.h"

/// Тест методов на уже созданных матрицах
void test_simple() {
    matrix_ A;
    vector_ b;
    vector_ exact_solution;

    for (int i = 0; i < 4; ++i) {
        std::string input_filename = "test_" + std::to_string(i) + ".txt";
        if (!input(input_filename, A, b, exact_solution)) {
            continue;
        }

        std::string output_filename = "test_" + std::to_string(i) + ".csv";
        std::ofstream os = logger_start(output_filename, "");

        vector_ x;
        if (i % 2) {
            x = gauss(A, b);
        } else {
            profile_matrix P = profile_matrix(A);
            x = lu_solver(P, b);
        }

        for (double j : x) {
            os << j << " ";
        }
        os.close();
    }
}

/// Тест LU-метода на матрицах с диагональным преобладанием
void test_lu_diagonal() {
    std::ofstream os = logger_start("test_lu_diagonal.csv", "n,k,||x* - x_k||,||x* - x_k|| / ||x*||\n");

    for (int n = 10; n <= 1000; n *= 10) {
        for (int k = 0; k <= 10; ++k) {
            profile_matrix p = profile_matrix(n, k);
            vector_ exact_solution(n);
            for (int i = 0; i < n; i++) {
                exact_solution[i] = i + 1;
            }
            vector_ b = p * exact_solution;
            vector_ x = lu_solver(p, b);
            vector_ absolute_accuracy(x.size());
            for (int j = 0; j < x.size(); j++) {
                absolute_accuracy[j] = exact_solution[j] - x[j];
            }
            os << n << "," << k << "," << module(absolute_accuracy) << "," << module(absolute_accuracy) / module(exact_solution) << std::endl;
        }
    }
    os.close();
}

/// Тест LU-метода на Гильбертовых матрицах
void test_lu_guilbert() {
    std::ofstream os = logger_start("test_lu_guilbert.csv", "n,||x* - x_k||,||x* - x_k|| / ||x*||\n");

    for (int n = 1; n <= 101; n += 10) {
        matrix_ g = guilbert_generator(n);
        profile_matrix p = profile_matrix(g);
        vector_ exact_solution(n);
        for (int i = 0; i < n; i++) {
            exact_solution[i] = i + 1;
        }
        vector_ b = p * exact_solution;
        vector_ x = lu_solver(p, b);
        vector_ absolute_accuracy(x.size());
        for (int j = 0; j < x.size(); j++) {
            absolute_accuracy[j] = exact_solution[j] - x[j];
        }
        os << n << "," << module(absolute_accuracy) << "," << module(absolute_accuracy) / module(exact_solution) << std::endl;
    }
    os.close();
}

/// Тест метода сравнение методов LU и Гаусса
void test_lu_gauss() {
    std::ofstream os = logger_start("test_lu_gauss.csv", "№,LU absolute,gauss absolute, LU relative, gauss relative\n");

    for (int n = 10; n <= 101; n += 10) {
        matrix_ g2 = dense_generator(n);
        profile_matrix g1 = profile_matrix(g2);
        vector_ exact_solution(n);
        for (int i = 0; i < n; i++) {
            exact_solution[i] = i + 1;
        }
        vector_ b1 = g2 * exact_solution, b2 = vector_(n);
        std::copy(b1.begin(), b1.end(), b2.begin());
        vector_ x1 = lu_solver(g1, b1);
        vector_ x2 = gauss(g2, b2);
        vector_ absolute_accuracy1(n), absolute_accuracy2(n);
        for (int j = 0; j < n; j++) {
            absolute_accuracy1[j] = exact_solution[j] - x1[j];
            absolute_accuracy2[j] = exact_solution[j] - x2[j];
        }
        os << n << "," << module(absolute_accuracy1) << "," << module(absolute_accuracy1) / module(exact_solution) << ","
        << module(absolute_accuracy2) << "," << module(absolute_accuracy2) / module(exact_solution) << std::endl;
    }
    os.close();
}

/// Тест метода сопряженных градиентов на уже созданных матрицах
void test_conjugate_simple() {
    matrix_ A;
    vector_ b;
    vector_ exact_solution;

    for (int i = 0; i < 4; ++i) {
        std::string input_filename = "test_" + std::to_string(i) + ".txt";
        if (!input(input_filename, A, b, exact_solution)) {
            continue;
        }

        std::string output_filename = "test_conjugate_" + std::to_string(i) + ".csv";
        std::ofstream os = logger_start(output_filename, "");

        sparse_matrix P = sparse_matrix(A);
        vector_ x(b.size());
        int iterations = conjugate_gradient(P, b, 10e-7, x);

        for (double j : x) {
            os << j << " ";
        }
        os.close();
    }
}

/// Тест метода сопряженных градиентов на отрицательных матрицах с различным числом обусловленности
void test_conjugate_diagonal() {
    std::ofstream os = logger_start("test_conjugate_diagonal.csv", "n,iterations,||x* - x_k||,||x* - x_k|| / ||x*||,cond\n");

    for (int n = 10; n <= 1000; n *= 10) {
        for (int k = 0; k < 10; ++k) {
            sparse_matrix p = sparse_matrix(n, 1);
            matrix_ m = to_matrix_(p);
            double cond = module(m) / module(reverse(m));
            vector_ exact_solution(n);
            for (int i = 0; i < n; i++) {
                exact_solution[i] = i + 1;
            }
            vector_ b = p * exact_solution, x(n);
            int iterations = conjugate_gradient(p, b, 10e-7, x);
            vector_ absolute_accuracy(x.size());
            for (int j = 0; j < x.size(); j++) {
                absolute_accuracy[j] = exact_solution[j] - x[j];
            }
            os << n << "," << iterations << "," << module(absolute_accuracy) << "," <<
                module(absolute_accuracy) / module(exact_solution) << "," << cond << std::endl;
        }
    }
    os.close();
}

/// Тест метода сопряженных градиентов на положительных матрицах с различным числом обусловленности
void test_conjugate_reverse_diagonal() {
    std::ofstream os = logger_start("test_conjugate_reverse_diagonal.csv", "n,iterations,||x* - x_k||,||x* - x_k|| / ||x*||,cond\n");

    for (int n = 10; n <= 1000; n *= 10) {
        for (int k = 0; k < 10; ++k) {
            sparse_matrix p = sparse_matrix(n, -1);
            matrix_ m = to_matrix_(p);
            double cond = module(m) / module(reverse(m));
            vector_ exact_solution(n);
            for (int i = 0; i < n; i++) {
                exact_solution[i] = i + 1;
            }
            vector_ b = p * exact_solution, x(n);
            int iterations = conjugate_gradient(p, b, 10e-7, x);
            vector_ absolute_accuracy(x.size());
            for (int j = 0; j < x.size(); j++) {
                absolute_accuracy[j] = exact_solution[j] - x[j];
            }
            os << n << "," << iterations << "," << module(absolute_accuracy) << "," <<
               module(absolute_accuracy) / module(exact_solution) << "," << cond << std::endl;
        }
    }
    os.close();
}

/// Тест метода сопряженных градиентов на Гильбертовых матрицах
void test_conjugate_guilbert() {
    std::ofstream os = logger_start("test_conjugate_guilbert.csv", "n,iterations,||x* - x_k||,||x* - x_k|| / ||x*||,cond\n");

    for (int n = 10; n <= 1000; n *= 10) {
        for (int k = 0; k < 10; ++k) {
            matrix_ g = guilbert_generator(n);
            double cond = module(g) / module(reverse(g));
            sparse_matrix p = sparse_matrix(g);
            vector_ exact_solution(n);
            for (int i = 0; i < n; i++) {
                exact_solution[i] = i + 1;
            }
            vector_ b = g * exact_solution;
            vector_ x = vector_ (n);
            int iterations = conjugate_gradient(p, b, 10e-7, x);
            vector_ absolute_accuracy(x.size());
            for (int j = 0; j < x.size(); j++) {
                absolute_accuracy[j] = exact_solution[j] - x[j];
            }
            os << n << "," << iterations << "," << module(absolute_accuracy) << "," <<
               module(absolute_accuracy) / module(exact_solution) << "," << cond << std::endl;
        }
    }
    os.close();
}

#endif //METOPT_TESTS_H
