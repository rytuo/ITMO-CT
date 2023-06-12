#ifndef METOPT_LINEAR_ALGEBRA_H
#define METOPT_LINEAR_ALGEBRA_H

#include <cmath>
#include <vector>

typedef std::vector<std::vector<double>> matrix_;
typedef std::vector<double> vector_;

// скалярное произведение векторов
double scalar(const vector_& v1, const vector_& v2) {
    double res = 0;
    for (int i = 0; i < v1.size(); ++i) {
        res += v1[i] * v2[i];
    }
    return res;
}

// длина вектора
double module(const vector_& v) {
    return sqrt(scalar(v, v));
}

matrix_ get_addition(const matrix_ &m, int i, int j) {
    int n = (int)m.size();
    matrix_ nm = matrix_(n - 1, vector_ (n - 1, 0));
    int q = 0;
    for (int t = 0; t < n; ++t) {
        if (t == i) {
            continue;
        }
        int r = 0;
        for (int k = 0; k < n; ++k) {
            if (k == j) {
                continue;
            }
            nm[q][r] = m[t][k];
            r++;
        }
        q++;
    }
}

double module(const matrix_ &m) {
    if (m.size() == 1) {
        return m[0][0];
    }
    double res = 0;
    int n = (int)m.size();
    for (int i = 0; i < n; ++i) {
        res += std::pow(-1, i) * m[0][i] * module(get_addition(m, 0, i));
    }
}

matrix_ reverse(const matrix_ &m) {
    int n = (int)m.size();
    matrix_ A_ = matrix_(n, vector_(n));
    double k = 1 / module(m);
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            A_[j][i] = module(get_addition(m, i, j)) * k;
        }
    }
}

// Умножение вектора на число
vector_ operator*(const vector_ &v, double a) {
    vector_ t = vector_(v.size());
    for (int i = 0; i < v.size(); ++i) {
        t[i] = v[i] * a;
    }
    return t;
}

// Сложение векторов
vector_ operator+(const vector_& v1, const vector_& v2) {
    vector_ t = vector_(v1.size());
    for (int i = 0; i < v1.size(); i++) {
        t[i] = v1[i] + v2[i];
    }
    return t;
}

vector_ operator-(const vector_& v1, const vector_& v2) {
    vector_ t = vector_(v1.size());
    for (int i = 0; i < v1.size(); i++) {
        t[i] = v1[i] - v2[i];
    }
    return t;
}

// Умножение вектора на матрицу (получается вектор-строка)
vector_ operator*(const vector_& v, const matrix_& m) {
    vector_ result(v.size(), 0);
    for (int i = 0; i < m.size(); i++) {
        for (int j = 0; j < m[i].size(); j++) {
            result[i] += m[j][i] * v[j];
        }
    }
    return result;
}

// Умножение матрицы на вектор (получается вектор-столбец)
vector_ operator*(const matrix_& m, const vector_& v) {
    vector_ res(v.size());
    for (int i = 0; i < m.size(); i++) {
        res[i] = scalar(m[i], v);
    }
    return res;
}

// Умножение матрицы на матрицу
matrix_ operator*(const matrix_& m1, const matrix_& m2) {
    matrix_ res(m1.size());
    for (int i = 0; i < res.size(); i++) {
        res[i] = m1[i] * m2;
    }
    return res;
}

matrix_ operator*(matrix_ m, double a) {
    matrix_ t = matrix_(m.size(), vector_(m.size()));
    for (int i = 0; i < t.size(); ++i) {
        for (int j = 0; j < t.size(); ++j) {
            t[i][j] = m[i][j] * a;
        }
    }
    return t;
}

#endif //METOPT_LINEAR_ALGEBRA_H
