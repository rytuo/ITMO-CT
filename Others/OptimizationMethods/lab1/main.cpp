#include <iostream>

#include "algo.h"

/**
 * Console logger function
 * @param name name of method
 * @param eps accuracy
 * @param res result
 */
void log(const std::string& name, double eps, double res) {
    std::cout << "Method used:          " << name << std::endl;
    std::cout << "Absolute error        " << eps << std::endl;
    std::cout << "Result:               " << res << std::endl;
    std::cout << "Number of iterations: " << algo::number_of_iterations << std::endl;
    std::cout << std::endl;
}

int main() {
    /**
    * Function for research
    * @param x function argument
    * @return function value
    */
    auto f = [](double x) {
        return -3.0 * x * sin(0.75 * x) + exp(-2.0 * x);
    };

    /**
     * absolute accuracy
     */
    double eps = 10e-11;

    std::cout << std::setprecision(10);
    std::cout << std::fixed;
    log("Dichotomy", eps, algo::dichotomy(f, 0, 2 * M_PI, eps));
    log("Golden section", eps, algo::golden_section(f, 0, 2 * M_PI, eps));
    log("Fibonacci", eps, algo::fibonacci(f, 0, 2 * M_PI, eps));
    log("Parabolic", eps, algo::parabolic(f, 0, 2 * M_PI, eps));
    log("Combined Brent", eps, algo::brent(f, 0, 2 * M_PI, eps));

    algo::create_csv(f, 0, 2 * M_PI, eps);

    return 0;
}