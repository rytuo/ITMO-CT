#ifndef METOPT_METHODS_H
#define METOPT_METHODS_H

#include <vector>
#include <functional>
#include "linear_algebra.h"
#include "golden_section.h"
#include <fstream>
#include <iomanip>
#include <map>

std::map<int, std::string> log_names =
        {{0, "classic"},
         {1, "search"},
         {2, "descent"},
         {3, "DFP"},
         {4, "pauel"}};

std::ofstream logger_start(const std::string &filename, const std::string &name) {
    std::ofstream os;
    os.open(filename);
    if (os.fail()) {
        std::cerr << "ofstream failed to open";
    }
    os << std::setprecision(8) << std::fixed;
    os << name;
    return os;
}


class extended_function {
private:
    std::function<matrix_(vector_)> _gess;
    std::function<vector_(vector_)> _grad;
    std::function<double(vector_)> _func;
public:
    explicit extended_function(const std::function<double(vector_)> &func,
                               const std::function<vector_(vector_)> &grad,
                               const std::function<matrix_(vector_)> &gess) {
        _func = func;
        _grad = grad;
        _gess = gess;
    }

    double func(const vector_ &x) {
        return _func(x);
    }

    vector_ grad(const vector_ &x) {
        return _grad(x);
    }

    matrix_ gess(const vector_ &x) {
        return _gess(x);
    }
};

vector_ newton(const vector_ &x0, const double &eps,
               const std::function<vector_(vector_)> &getP,
               const std::function<double(vector_, vector_)> &getA,
               const int method_name,
               const int func_number) {
    std::ofstream log = logger_start(log_names.at(method_name) + "_f" + std::to_string(func_number) + ".csv", "point\n");
    vector_ x = vector_(x0.size());
    std::copy(x0.begin(), x0.end(), x.begin());
    size_t iter = 0;
    while (true) {
        for (double i : x) {
            log << i << " ";
        }
        log << std::endl;
        iter++;
        vector_ p = getP(x);
        double a = getA(x, p);
        vector_ pa = p * a;
        if (module(pa) < eps) {
            log << "NOI: " << iter;
            log.flush();
            log.close();
            return x;
        }
        x = x + pa;
    }
}

vector_ classic_newton(extended_function &f, const vector_ &x0, const double &eps) {
    auto getP = [&](const vector_ &x){
        return reversed(f.gess(x)) * f.grad(x) * -1;
    };
    auto getA = [&](const vector_ &x, const vector_ &p) {
        return 1;
    };
    return newton(x0, eps, getP, getA, 0, 0);
}

vector_ search_newton(extended_function &f, const vector_ &x0, const double &eps) {
    auto getP = [&](const vector_ &x){
        return reversed(f.gess(x)) * f.grad(x) * -1;
    };
    auto getA = [&](const vector_ &x, const vector_ &p) {
        auto sf = [&](const double a) {
            return f.func(x + p * a);
        };

        return golden_section(sf, eps);
    };
    return newton(x0, eps, getP, getA, 1, 1);
}

vector_ descent_newton(extended_function &f, const vector_ &x0, const double &eps) {
    auto getP = [&](const vector_ &x){
        return gauss(f.gess(x), vector_(x.size(), 0)-f.grad(x));
    };
    auto getA = [&](const vector_ &x, const vector_ &p) {
        return 1;
    };
    return newton(x0, eps, getP, getA, 2, 2);
}

vector_ quazinewton(extended_function &f, const vector_ &x0, const double &eps,
                    const std::function<matrix_ (matrix_, vector_, vector_)> &getG,
                    const int method_name, const int func_number) {
    size_t iter = 0;
    std::ofstream log = logger_start(log_names.at(method_name) + "_f" + std::to_string(func_number) + ".csv", "point\n");
    matrix_ prev_g = matrix_(x0.size(), vector_(x0.size(), 0));
    for (int i = 0; i < x0.size(); ++i) {
        prev_g[i][i] = 1;
    }
    vector_ prev_w = f.grad(x0) * -1;
    vector_ p = prev_w, x = x0;
    auto sf = [&](const double a) {
        return f.func(x - p * a);
    };
    double a = golden_section(sf, eps);
    vector_ prev_x = x;
    x = x0 + p * a;

    while (true) {
        iter++;
        for (double i : x) {
            log << i << " ";
        }
        vector_ dx = x - prev_x;
        if (module(x - prev_x) < eps) {
            log << "NOI: " << iter;
            log.flush();
            log.close();
            return x;
        }
        vector_ w = f.grad(x);
        vector_ dw = w - prev_w;
        matrix_ g = getG(prev_g, dx, dw);
        p = g * f.grad(x) * -1;
        a = golden_section(sf, eps);
        prev_g = g;
        prev_x = x;
        prev_w = w;
        x = x + p * a;
    }
}

vector_ dfp(extended_function &f, const vector_ &x0, const double eps) {
    auto getG = [&](const matrix_ &prev_g, const vector_ &dx, const vector_ &dw) {
        int n = (int)prev_g.size();
        matrix_ first = matrix_(n, vector_(n));
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                first[i][j] = dx[i] * dx[j];
            }
        }
        matrix_ second = matrix_(n, vector_(n));
        vector_ gw = prev_g * dw;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                second[i][j] = gw[i] * dw[j];
            }
        }
        return prev_g + first * (-1 / scalar(dw, dx)) +
               second * transparent(prev_g) * (-1 / scalar(prev_g * dw, dw));
    };
    return quazinewton(f, x0, eps, getG, 3, 3);
}

vector_ pauel(extended_function &f, const vector_ &x0, const double eps) {
    auto getG = [&](const matrix_ &prev_g, const vector_ &dx, const vector_ &dw) {
        int n = (int)prev_g.size();
        vector_ y = dx + prev_g * dw;
        matrix_ m = matrix_(n, vector_(n));
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                m[i][j] = y[i] * y[j];
            }
        }
        return prev_g + m * (-1 / scalar(dw, y));
    };
    return quazinewton(f, x0, eps, getG, 4, 4);
}

#endif //METOPT_METHODS_H
