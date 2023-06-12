#include "tests.h"

int main() {
    test_simple();
    test_lu_diagonal();
    test_lu_guilbert();
    test_lu_gauss();
    test_conjugate_simple();
    test_conjugate_diagonal();
    test_conjugate_reverse_diagonal();
    test_conjugate_guilbert();
}