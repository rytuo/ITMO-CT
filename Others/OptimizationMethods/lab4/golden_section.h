#ifndef METOPT_GOLDEN_SECTION_H
#define METOPT_GOLDEN_SECTION_H

int number_of_iterations;

double golden_section(const std::function<double (double)> &f, double eps) {
    const double GOLDEN_RATIO = (1 + std::sqrt(5)) / 2;
    bool left = true;
    double a = -10000, b = 10000;
    double x1 = a + (b - a) / GOLDEN_RATIO, x2;
    double f_x1 = f(x1), f_x2;
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

#endif //METOPT_GOLDEN_SECTION_H
