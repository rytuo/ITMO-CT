#ifndef METOPT_LOGGERS_H
#define METOPT_LOGGERS_H

#include "matrix.h"

#include <fstream>
#include <iostream>
#include <iomanip>

const std::string ROOT_DIRECTORY = "/home/rytuo/work/metOpt/MetOpt/lab3/";

/// Ввод из файла: размерность n, матрица A, вектор b
int input(const std::string &filename, matrix_ &A, vector_ &b, vector_ &exact_solution) {
    std::ifstream is(ROOT_DIRECTORY + "tests/" + filename);
    if (!is) {
        std::cerr << "Could not open file: " + filename << std::endl;
        return 0;
    } else {
        std::cout << "New test: " + filename << std::endl;
    }
    int n;
    is >> n;
    A = matrix_(n, vector_(n));
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            is >> A[i][j];
        }
    }
    b = vector_(n);
    for (int i = 0; i < n; ++i) {
        is >> b[i];
    }
    exact_solution = vector_(n);
    for (int i = 0; i < n; ++i) {
        is >> exact_solution[i];
    }
    is.close();
    return 1;
}

/// Инициализация логгера в выбранный файл
std::ofstream logger_start(const std::string &filename, const std::string &name) {
    std::ofstream os;
    os.open(ROOT_DIRECTORY + "output/" + filename);
    os << std::setprecision(4) << std::fixed;
    os << name;
    return os;
}

#endif //METOPT_LOGGERS_H
