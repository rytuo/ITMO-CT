#ifndef METOPT_ALGO_H
#define METOPT_ALGO_H

#include "abstract_algo.h"

#include <vector>
#include <fstream>
#include <iomanip>

double algo::dichotomy(const std::function<double(double)> &f, double a, double b, double eps) {
    number_of_iterations = 0;
    double d = eps / 2;
    while ((b - a) / 2 > eps) {
        number_of_iterations += 2;
        double x1 = (a + b) / 2 - d;
        double x2 = (a + b) / 2 + d;
        if (f(x1) > f(x2)) {
            a = x1;
        } else {
            b = x2;
        }
    }
    return (a + b) / 2;
}

double algo::golden_section(const std::function<double(double)> &f, double a, double b, double eps) {
    bool left = true;
    double x1 = a + (b - a) / GOLDEN_RATIO, x2,
        f_x1 = f(x1), f_x2;
    number_of_iterations = 1;
    while ((b - a) / 2 > eps) {
        if (left) {
            x2 = x1;
            f_x2 = f_x1;
            x1 = b - (b - a) / GOLDEN_RATIO;
            f_x1 = f(x1);
        } else {
            x1 = x2;
            f_x1 = f_x2;
            x2 = a + (b - a) / GOLDEN_RATIO;
            f_x2 = f(x2);
        }
        number_of_iterations++;
        if (f_x1 > f_x2) {
            a = x1;
            left = false;
        } else {
            b = x2;
            left = true;
        }
    }
    return (a + b) / 2;
}

double algo::fibonacci(const std::function<double(double)> &f, double a, double b, double eps) {
    std::vector<double> fib;
    fib.push_back(1);
    fib.push_back(1);
    int n = 2;
    for (; (b - a) / eps >= fib.back(); ++n) {
        fib.push_back(fib.back() + fib[n - 2]);
    }

    bool left = true;
    double x1 = a + (fib[n - 2]  / fib[n - 1]) * (b - a), x2,
        f_x1 = f(x1), f_x2;
    number_of_iterations = 1;
    for (int k = 0; k < n - 2; k++) {
        if (left) {
            x2 = x1;
            f_x2 = f_x1;
            x1 = a + (fib[n - 3 - k] / fib[n - 1 - k]) * (b - a);
            f_x1 = f(x1);
        } else {
            x1 = x2;
            f_x1 = f_x2;
            x2 = a + (fib[n - 2 - k] / fib[n - 1 - k]) * (b - a);
            f_x2 = f(x2);
        }
        number_of_iterations++;
        if (f_x1 > f_x2) {
            a = x1;
            left = false;
        } else {
            b = x2;
            left = true;
        }
    }
    return (a + b) / 2;
}

double algo::parabolic(const std::function<double(double)> &f, double a, double b, double eps) {
    double prev_x = a, x1 = a, x2 = (a + b) / 2, x3 = b;
    double f_x1 = f(x1), f_x2 = f(x2), f_x3 = f(x3);
    number_of_iterations = 3;
    while (true) {
        if (x3 == x1 || x2 == x1 || x3 == x2) {
            return x2;
        }
        double a0 = f_x1, a1 = (f_x2 - f_x1) / (x2 - x1), a2 =
                ((f_x3 - f_x1) / (x3 - x1) - (f_x2 - f_x1) / (x2 - x1)) / (x3 - x2);
        if (a1 == a2) {
            return x2;
        }
        double x = (x1 + x2 - (a1 / a2)) / 2;
        double f_x = f(x);
        number_of_iterations++;
        if (fabs(x - prev_x) <= eps) {
            return x;
        }
        if (x < x2) {
            if (f_x >= f_x2) {
                x1 = x;
                f_x1 = f_x;
            } else {
                x3 = x2;
                f_x3 = f_x2;
                x2 = x;
                f_x2 = f_x;
            }
        } else {
            if (f_x >= f_x2) {
                x3 = x;
                f_x3 = f_x;
            } else {
                x1 = x2;
                f_x1 = f_x2;
                x2 = x;
                f_x2 = f_x;
            }
        }
        prev_x = x;
    }
}

double algo::brent(const std::function<double(double)> &f, double a, double c, double eps) {
    number_of_iterations = 1;
    double x, w, v, x_res, w_res, v_res, d, e;
    x = v = w = (a + c) / 2;
    x_res = w_res = v_res = f(x);
    d = e = c - a;
    while (true) {
        number_of_iterations++;
        double g = e, u = -1;
        e = d;
        if (x != v && x != w && w != v && x_res != v_res && x_res != w_res && v_res != w_res) {
            if (w > v) {
                double a0 = v_res, a1 = (x_res - v_res) / (x - v), a2 =
                        ((w_res - v_res) / (w - v) - (x_res - v_res) / (x - v)) / (w - x);
                u = (v + x - (a1 / a2)) / 2;
            } else {
                double a0 = w_res, a1 = (x_res - w_res) / (x - w), a2 =
                        ((v_res - w_res) / (v - w) - (x_res - w_res) / (x - w)) / (v - x);
                u = (w + x - (a1 / a2)) / 2;
            }
        }
        if (u - a > eps && u < c - eps && fabs(u - x) < g / 2) {
            d = fabs(u - x);
        } else {
            if (x < (c - a) / 2) {
                u = x + (c - x) / GOLDEN_RATIO;
                d = c - x;
            } else {
                u = x - (x - a) / GOLDEN_RATIO;
                d = x - a;
            }
        }
        if (fabs(u - x) < eps) {
            return u;
        }
        double u_res = f(u);
        if (u_res <= x_res) {
            if (u >= x) {
                a = x;
            } else {
                c = x;
            }
            v = w;
            w = x;
            x = u;
            v_res = w_res;
            w_res = x_res;
            x_res = u_res;
        } else {
            if (u >= x) {
                c = u;
            } else {
                a = u;
            }
            if (u_res <= w_res || w == x) {
                v = w;
                w = u;
                v_res = w_res;
                w_res = u_res;
            } else if (u_res <= v_res || v == x || v == w) {
                v = u;
                v_res = u_res;
            }
        }
    }
}

double algo::dichotomy_csv(const std::function<double(double)> &f, double a, double b, double eps) {
    std::ofstream myfile;
    myfile.open("dichotomy.csv");
    myfile << std::setprecision(6) << std::fixed;
    myfile << "n,number of iterations,a,b,x1,x2,f(x1),f(x2),b-a,k" << std::endl;
    number_of_iterations = 0;
    double d = eps / 2;
    double prev_b_a = b - a;
    while (fabs(b - a) / 2 > eps) {
        number_of_iterations += 2;
        double x1 = (a + b) / 2 - d;
        double x2 = (a + b) / 2 + d;
        myfile << number_of_iterations / 2 << "," << number_of_iterations << "," << a << ","  << b << "," << x1
        << "," << x2 << "," << f(x1) << "," << f(x2) << "," << b - a << "," << prev_b_a / (b - a) <<  std::endl;
        prev_b_a = b - a;
        if (f(x1) > f(x2)) {
            a = x1;
        } else {
            b = x2;
        }
    }
    myfile.close();
    return (a + b) / 2;
}

double algo::golden_section_csv(const std::function<double(double)> &f, double a, double b, double eps) {
    std::ofstream myfile;
    myfile.open("golden_section.csv");
    myfile << std::setprecision(6) << std::fixed;
    myfile << "n,number of iterations,a,b,x1,x2,f(x1),f(x2),b-a,k" << std::endl;
    double prev_b_a = b - a;
    number_of_iterations = 1;
    bool left = true;
    double x1 = a + (b - a) / GOLDEN_RATIO, x2,
            f_x1 = f(x1), f_x2;
    while ((b - a) / 2 > eps) {
        if (left) {
            x2 = x1;
            f_x2 = f_x1;
            x1 = b - (b - a) / GOLDEN_RATIO;
            f_x1 = f(x1);
        } else {
            x1 = x2;
            f_x1 = f_x2;
            x2 = a + (b - a) / GOLDEN_RATIO;
            f_x2 = f(x2);
        }
        number_of_iterations++;
        myfile << number_of_iterations - 1 << "," << number_of_iterations << "," << a << ","  << b << "," << x1
        << "," << x2 << "," << f(x1) << "," << f(x2) << "," << b - a << "," << prev_b_a / (b - a) <<  std::endl;
        prev_b_a = b - a;
        if (f_x1 > f_x2) {
            a = x1;
            left = false;
        } else {
            b = x2;
            left = true;
        }
    }
    myfile.close();
    return (a + b) / 2;
}

double algo::fibonacci_csv(const std::function<double(double)> &f, double a, double b, double eps) {
    std::ofstream myfile;
    myfile.open("fibonacci.csv");
    myfile << std::setprecision(6) << std::fixed;
    myfile << "n,number of iterations,a,b,x1,x2,f(x1),f(x2),b-a,k" << std::endl;
    std::vector<double> fib;
    fib.push_back(1);
    fib.push_back(1);
    int n = 2;
    for (; (b - a) / eps >= fib.back(); ++n) {
        fib.push_back(fib.back() + fib[n - 2]);
    }

    bool left = true;
    double x1 = a + (fib[n - 2]  / fib[n - 1]) * (b - a), x2,
            f_x1 = f(x1), f_x2;
    number_of_iterations = 1;
    double prev_b_a = b - a;
    for (int k = 0; k < n - 2; k++) {
        if (left) {
            x2 = x1;
            f_x2 = f_x1;
            x1 = a + (fib[n - 3 - k] / fib[n - 1 - k]) * (b - a);
            f_x1 = f(x1);
        } else {
            x1 = x2;
            f_x1 = f_x2;
            x2 = a + (fib[n - 2 - k] / fib[n - 1 - k]) * (b - a);
            f_x2 = f(x2);
        }
        number_of_iterations++;
        myfile << k + 1 << "," << number_of_iterations << "," << a << ","  << b << "," << x1 << "," << x2 << "," <<
               f(x1) << "," << f(x2) << "," << b - a << "," << prev_b_a / (b - a) <<  std::endl;
        prev_b_a = b - a;
        if (f_x1 > f_x2) {
            a = x1;
            left = false;
        } else {
            b = x2;
            left = true;
        }
    }
    myfile.close();
    return (a + b) / 2;
}

double algo::parabolic_csv(const std::function<double(double)> &f, double a, double b, double eps) {
    std::ofstream myfile;
    myfile.open("parabolic.csv");
    myfile << std::setprecision(6) << std::fixed;
    myfile << "n,number of iterations,x1,x2,x3,f(x1),f(x2),f(x3),x,f(x),b-a,k" << std::endl;
    double prev_x = a, x1 = a, x2 = (a + b) / 2, x3 = b;
    double f_x1 = f(x1), f_x2 = f(x2), f_x3 = f(x3);
    number_of_iterations = 3;
    double prev_b_a = x3 - x1;
    while (true) {
        if (x3 == x1 || x2 == x1 || x3 == x2) {
            myfile.close();
            return x2;
        }
        double a0 = f_x1, a1 = (f_x2 - f_x1) / (x2 - x1), a2 =
                ((f_x3 - f_x1) / (x3 - x1) - (f_x2 - f_x1) / (x2 - x1)) / (x3 - x2);
        if (a1 == a2) {
            myfile.close();
            return x2;
        }
        double x = (x1 + x2 - (a1 / a2)) / 2;
        double f_x = f(x);
        number_of_iterations++;
        myfile << number_of_iterations - 3 << "," << number_of_iterations << "," << x1 << "," << x2 << "," << x3
               << "," << f_x1 << "," << x2 << "," << x3 << "," << x << "," << f_x << "," << x3 - x1 << "," << prev_b_a / (x3 - x1) <<  std::endl;
        prev_b_a = x3 - x1;
        if (fabs(x - prev_x) <= eps) {
            myfile.close();
            return x;
        }
        if (x < x2) {
            if (f_x >= f_x2) {
                x1 = x;
                f_x1 = f_x;
            } else {
                x3 = x2;
                f_x3 = f_x2;
                x2 = x;
                f_x2 = f_x;
            }
        } else {
            if (f_x >= f_x2) {
                x3 = x;
                f_x3 = f_x;
            } else {
                x1 = x2;
                f_x1 = f_x2;
                x2 = x;
                f_x2 = f_x;
            }
        }
        prev_x = x;
    }
}

double algo::brent_csv(const std::function<double(double)> &f, double a, double c, double eps) {
    std::ofstream myfile;
    myfile.open("brent.csv");
    myfile << std::setprecision(6) << std::fixed;
    myfile << "n,number of iterations,a,c,x,f(x),w,f(w),u,f(u),b-a,k,method" << std::endl;
    std::string method;
    double prev_c_a = c - a;
    number_of_iterations = 1;
    double x, w, v, x_res, w_res, v_res, d, e;
    x = v = w = (a + c) / 2;
    x_res = w_res = v_res = f(x);
    d = e = c - a;
    while (true) {
        number_of_iterations++;
        double g = e, u = -1;
        e = d;
        if (x != v && x != w && w != v && x_res != v_res && x_res != w_res && v_res != w_res) {
            if (w > v) {
                double a0 = v_res, a1 = (x_res - v_res) / (x - v), a2 =
                        ((w_res - v_res) / (w - v) - (x_res - v_res) / (x - v)) / (w - x);
                u = (v + x - (a1 / a2)) / 2;
            } else {
                double a0 = w_res, a1 = (x_res - w_res) / (x - w), a2 =
                        ((v_res - w_res) / (v - w) - (x_res - w_res) / (x - w)) / (v - x);
                u = (w + x - (a1 / a2)) / 2;
            }
            method = "Parabolic";
        }
        if (u - a > eps && u < c - eps && fabs(u - x) < g / 2) {
            d = fabs(u - x);
        } else {
            if (x < (c - a) / 2) {
                u = x + (c - x) / GOLDEN_RATIO;
                d = c - x;
            } else {
                u = x - (x - a) / GOLDEN_RATIO;
                d = x - a;
            }
            method = "Golden section";
        }
        myfile << number_of_iterations - 1 << "," << number_of_iterations << "," << a << ","  << c << "," << x << ","
        << x_res << "," << w << "," << w_res << "," << u << "," << f(u) << "," << c - a << "," << prev_c_a / (c - a) << "," << method << std::endl;
        prev_c_a = c - a;
        if (fabs(u - x) < eps) {
            myfile.close();
            return u;
        }
        double u_res = f(u);
        if (u_res <= x_res) {
            if (u >= x) {
                a = x;
            } else {
                c = x;
            }
            v = w;
            w = x;
            x = u;
            v_res = w_res;
            w_res = x_res;
            x_res = u_res;
        } else {
            if (u >= x) {
                c = u;
            } else {
                a = u;
            }
            if (u_res <= w_res || w == x) {
                v = w;
                w = u;
                v_res = w_res;
                w_res = u_res;
            } else if (u_res <= v_res || v == x || v == w) {
                v = u;
                v_res = u_res;
            }
        }
    }
}

void algo::all_csv(const std::function<double(double)> &f, double a, double b, double eps) {
    std::ofstream file_all;
    file_all << std::setprecision(10) << std::fixed;
    file_all.open("all.csv");
    file_all << "name,result,number of iterations" << std::endl;
    file_all << "Dichotomy," << dichotomy(f, a, b, eps) << "," << number_of_iterations << std::endl;
    file_all << "Golden section," << golden_section(f, a, b, eps) << "," << number_of_iterations << std::endl;
    file_all << "Fibonacci," << fibonacci(f, a, b, eps) << "," << number_of_iterations << std::endl;
    file_all << "Parabolic," << parabolic(f, a, b, eps) << "," << number_of_iterations << std::endl;
    file_all << "Combined Brent," << brent(f, a, b, eps) << "," << number_of_iterations << std::endl;
    file_all.close();
}

void algo::eps_csv(const std::function<double(double)> &f, double a, double b, double eps) {
    std::ofstream file_eps;
    file_eps.open("eps.csv");
    file_eps << "eps,dichotomy,golden section,fibonacci,parabolic,combined brent" << std::endl;
    for (int i = 0; i < 16; i++) {
        eps = pow(10, -i);
        std::cout << std::setprecision(i + 1);
        std::cout << std::fixed;
        file_eps << eps << ",";
        dichotomy(f, a, b, eps);
        file_eps << number_of_iterations << ",";
        golden_section(f, a, b, eps);
        file_eps << number_of_iterations << ",";
        fibonacci(f, a, b, eps);
        file_eps << number_of_iterations << ",";
        parabolic(f, a, b, eps);
        file_eps << number_of_iterations << ",";
        brent(f, a, b, eps);
        file_eps << number_of_iterations << std::endl;
    }
    file_eps.close();
}

void algo::create_csv(const std::function<double(double)> &f, double a, double b, double eps) {
    all_csv(f, a, b, eps);
    dichotomy_csv(f, a, b, eps);
    golden_section_csv(f, a, b, eps);
    fibonacci_csv(f, a, b, eps);
    parabolic_csv(f, a, b, eps);
    brent_csv(f, a, b, eps);
    eps_csv(f, a, b, eps);
}

#endif //METOPT_ALGO_H
