#ifndef METOPT_MATRIX_H
#define METOPT_MATRIX_H

#include <utility>
#include <vector>
#include <cstdlib>
#include <cmath>
#include <ctime>

typedef std::vector<double> vector_;
typedef std::vector<std::vector<double>> matrix_;

class profile_matrix {
private:
    int n;
    vector_ d, al, au;
    std::vector<int> il, iu;

public:
    explicit profile_matrix(matrix_ &m) {
        n = (int)m.size();

        d = vector_ ();
        al = vector_();
        au = vector_();
        il = std::vector<int>();
        iu = std::vector<int>();
        for (int i = 0, j; i < n; ++i) {
            d.push_back(m[i][i]);

            il.push_back((int)al.size());
            for (j = 0; j < i && m[i][j] == 0; ++j);
            for (; j < i; ++j) { al.push_back(m[i][j]); }

            iu.push_back((int)au.size());
            for (j = 0; j < i && m[j][i] == 0; ++j);
            for (; j < i; ++j) { au.push_back(m[j][i]); }
        }
        il.push_back((int)al.size());
        iu.push_back((int)au.size());
    }

    /// генерируем матрицу как в задании
    explicit profile_matrix(int n, int k) {
        this->n = n;

        srand(time(nullptr));
        std::vector<int> default_values = {0, -1, -2, -3, -4};

        d = vector_();
        al = vector_();
        au = vector_();
        il = std::vector<int>(n + 1, 0);
        iu = std::vector<int>(n + 1, 0);

        int sum = 0;
        for (int i = 0; i < n; i++) {
            int size = std::rand() % (i + 1);
            il[i + 1] = il[i] + size;
            for (int j = 0; j < size; ++j) {
                int nv = default_values[std::rand() % 5];
                al.push_back(nv);
                sum += nv;
            }

            size = std::rand() % (i + 1);
            iu[i + 1] = iu[i] + size;
            for (int j = 0; j < size; ++j) {
                int nv = default_values[std::rand() % 5];
                au.push_back(nv);
                sum += nv;
            }
        }

        d[0] = -sum + pow(10, -k);
        for (int i = 1; i < n; i++) {
            d[i] = -sum;
        }
    }

    int size() const {
        return n;
    }

    /// получаем элемент матрицы по индексу
    double get(int i, int j) {
        if (i == j) {
            return d[i];
        } else if (i > j) {
            // номер относительно начала массива
            int k = j - i + il[i + 1] - il[i];
            return k < 0 ? 0 : al[k];
        } else {
            // номер относительно начала массива
            int k = i - j + iu[j + 1] - iu[j];
            return k < 0 ? 0 : au[k];
        }
    }

    /// меняем элемент по индексу
    void set(int i, int j, double v) {
        if (i == j) {
            d[i] = v;
        } else if (i > j) {
            // номер относительно начала массива
            int k = j - i + il[i + 1] - il[i];
            if (k < 0) {
                for (int l = 0; l < -k - 1; ++l) {
                    al.insert(al.begin() + il[i], 0);
                }
                al.insert(al.begin() + il[i], v);
                for (int l = i + 1; l < il.size(); ++l) {
                    il[l] -= k;
                }
            } else {
                al[k] = v;
            }
        } else {
            // номер относительно начала массива
            int k = i - j + iu[j + 1] - iu[j];
            if (k < 0) {
                for (int l = 0; l < -k - 1; ++l) {
                    au.insert(au.begin() + iu[j], 0);
                }
                au.insert(au.begin() + iu[j], v);
                for (int l = j + 1; l < iu.size(); ++l) {
                    iu[l] -= k;
                }
            } else {
                au[k] = v;
            }
        }
    }
};

vector_ operator*(profile_matrix &m, const vector_ &v) {
    vector_ res(v.size());
    for (int k = 0; k < v.size(); k++) {
        for (int i = 0; i < v.size(); i++) {
            res[i] = 0;
            for (int j = 0; j < v.size(); j++) {
                res[i] += m.get(i, j) * v[j];
            }
        }
    }
    return res;
}

class sparse_matrix {
private:
    int n;
    vector_ d, al, au;
    std::vector<int> ia, ja;
public:
    explicit sparse_matrix(matrix_ &m) {
        n = (int)m.size();

        d = vector_ ();
        al = vector_();
        au = vector_();
        ia = std::vector<int>();
        ja = std::vector<int>();
        for (int i = 0, j; i < n; ++i) {
            d.push_back(m[i][i]);
            ia.push_back((int)al.size());
            for (j = 0; j < i; ++j) {
                if (m[i][j] != 0 || m[j][i] != 0) {
                    al.push_back(m[i][j]);
                    au.push_back(m[j][i]);
                }
            }
        }
        ia.push_back((int)al.size());
    }

    /// генерируем матрицу как в задании
    explicit sparse_matrix(int n, int multiplier) {
        this->n = n;

        srand(time(nullptr));
        std::vector<int> default_values = {0, -1, -2, -3, -4};

        d = vector_();
        al = vector_();
        au = vector_();
        ia = std::vector<int>(n + 1, 0);
        ja = std::vector<int>(); // ?

        int sum = 0;
        for (int i = 1; i < n; i++) {
            ia[i + 1] = ia[i];
            for (int j = 0; j < i; ++j) {
                int nv1 = default_values[std::rand() % 5] * multiplier;
                int nv2 = default_values[std::rand() % 5] * multiplier;
                if (nv1 != 0 || nv2 != 0) {
                    al.push_back(nv1);
                    au.push_back(nv2);
                    ja.push_back(j);
                    ia[i + 1]++;
                }
            }
        }

        d[0] = -sum + 1;
        for (int i = 1; i < n; i++) {
            d[i] = -sum;
        }
    }

    int size() const {
        return n;
    }

    /// получаем элемент матрицы по индексу
    double get(int i, int j) {
        if (i == j) {
            return d[i];
        } else {
            bool reverse = false;
            if (j > i) {
                std::swap(i, j);
                reverse = true;
            }
            int k = ia[i];
            for (; k < ia[i + 1] && ja[k] < j; ++k) {}
            if (ja[k] == j) {
                return reverse ? au[k] : al[k];
            }
            return 0;
        }
    }

    /// меняем элемент по индексу
    void set(int i, int j, double v) {
        if (i == j) {
            d[i] = v;
        } else {
            bool reverse = false;
            if (j > i) {
                std::swap(i, j);
                reverse = true;
            }
            int k = ia[i];
            for (; k < ia[i + 1] && ja[k] < j; ++k) {}
            if (ja[k] == j) {
                if (reverse) {
                    au[k] = v;
                } else {
                    al[k] = v;
                }
            } else {
                al.insert(al.begin() + k, v);
                au.insert(au.begin() + k, 0);
                ja.insert(ja.begin() + k, j);
                if (reverse) {
                    std::swap(al[k], au[k]);
                }
                for (int l = i + 1; l <= n; ++l) {
                    ia[l]++;
                }
            }
        }
    }
};

matrix_ to_matrix_(sparse_matrix &m) {
    int n = m.size();
    matrix_ res = matrix_(n, vector_(n));
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            res[i][j] = m.get(i, j);
        }
    }
    return res;
}

vector_ operator*(sparse_matrix &A, vector_ &v) {
    int n = (int)A.size();
    vector_ t = vector_(n, 0);
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            t[i] = A.get(i, j) * v[i];
        }
    }
    return t;
}

/// Генератор Гильбертовых матриц
matrix_ guilbert_generator(int n) {
    matrix_ m = matrix_(n, vector_(n));
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            m[i][j] = 1.0 / (i + j + 1);
        }
    }
    return m;
}

/// Генератор плотных матриц
matrix_ dense_generator(int n) {
    matrix_ m = matrix_(n, vector_(n));
    std::srand(time(nullptr));
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            m[i][j] = std::rand();
        }
    }
    return m;
}

matrix_ matrix_copy(matrix_ &m) {
    matrix_ res = matrix_(m.size(), vector_(m.size(), 0));
    for (int i = 0; i < m.size(); ++i) {
        std::copy(m[i].begin(), m[i].end(), res[i]);
    }
    return res;
}

/// Генератор вектора свободных коэффициентов
vector_ free_generator(int n) {
    vector_ b = vector_(n);
    std::srand(time(nullptr));
    for (int i = 0; i < n; ++i) {
        b[i] = std::rand();
    }
    return b;
}

#endif //METOPT_MATRIX_H
