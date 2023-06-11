#pragma GCC optimize("Ofast")

#include <iostream>
#include <vector>
#include <map>
#include <sstream>
#include <cmath>
#include <random>
#include <iomanip>
#include <bitset>

using namespace std;

using poly = int;

poly primitive;
int n, m, d, k;

vector<vector<poly>> mul_table;

mt19937 get_random((random_device()) ());
uniform_real_distribution<double> distribution{0, 1};

// utils

[[nodiscard]] inline poly simple_poly_mul(poly a, poly b) {
    poly product = 0;
    while (a != 0 && b != 0) {
        if (b & 1) {
            product ^= a;
        }

        if ((a >> (m - 1)) & 1) {
            a = (a << 1) ^ primitive;
        } else {
            a <<= 1;
        }
        b >>= 1;
    }
    return product;
}

inline void precalc_mul_table() {
    mul_table = vector<vector<poly>>(n + 1, vector<poly>(n + 1));
    for (int i = 0; i <= n; ++i) {
        for (int j = 0; j <= i; ++j) {
            poly a = simple_poly_mul(i, j);
            mul_table[i][j] = a;
            mul_table[j][i] = a;
        }
    }
}

[[nodiscard]] inline poly poly_mul(poly a, poly b) {
    return mul_table[a][b];
}

[[nodiscard]] inline poly poly_pow(poly a, int degree) {
    while (degree < 0) {
        degree += n;
    }
    poly res = 1;
    while (degree > 0) {
        if (degree % 2 == 1) {
            res = poly_mul(res, a);
            degree--;
        }
        res = poly_mul(res, res);
        degree /= 2;
    }
    return res;
}

[[nodiscard]] inline vector<poly> vector_poly_add(const vector<poly> &a, const vector<poly> &b) {
    vector<poly> res(max(a.size(), b.size()), 0);
    for (int i = 0; i < a.size(); ++i) {
        res[i] ^= a[i];
    }
    for (int i = 0; i < b.size(); ++i) {
        res[i] ^= b[i];
    }
    return res;
}

[[nodiscard]] inline vector<poly> vector_poly_mul(const vector<poly> &a, const vector<poly> &b) {
    vector<poly> res(a.size() + b.size() - 1, 0);
    for (int i = 0; i < a.size(); ++i) {
        for (int j = 0; j < b.size(); ++j) {
            res[i + j] = res[i + j] ^ poly_mul(a[i], b[j]);
        }
    }
    return res;
}

inline void mul(vector<poly> &a, poly b) {
    for (auto &ai :a) {
        ai = poly_mul(ai, b);
    }
}

[[nodiscard]] inline poly calc_vector(vector<poly> &a, poly x) {
    if (a.empty()) {
        return 0;
    }
    poly res = a[0];
    poly xi = 1;
    for (int i = 1; i < a.size(); ++i) {
        xi = poly_mul(xi, x);
        res ^= poly_mul(a[i], xi);
    }
    return res;
}

// methods

// todo mb bit form
inline void encode(vector<poly> &g, vector<poly> &v, vector<poly> &res) {
    fill(res.begin(), res.end(), 0);
    int r = n - k;
    for (int i = 0; i < v.size(); ++i) {
        res[i + r] ^= v[i];
    }

    for (int i = n - 1; i >= g.size() - 1; --i) {
        if (res[i]) {
            for (int j = 0; j < g.size(); ++j) {
                res[i - g.size() + j + 1] ^= g[j];
            }
        }
    }

    for (int i = 0; i < v.size(); ++i) {
        res[i + r] ^= v[i];
    }
}

inline void decode(vector<poly> &g, vector<poly> &v, vector<poly> &alpha) {
    // find syndromes via Horner circuit
    vector<poly> syndromes(d - 1);
    for (int i = 0; i < d - 1; ++i) {
        syndromes[i] = calc_vector(v, alpha[i + 1]);
    }

    // Berlekamp–Massey algorithm
    poly mm = 0, l = 0;
    vector<poly> a{1}, b{1};
    vector<poly> t;
    poly delta;
    for (int r = 1; r < d; ++r) {
        if (r % 2 == 0) {
            continue;
        }
        delta = 0;
        for (int j = 0; j <= l; ++j) {
            delta ^= poly_mul(a[j], syndromes[r - 1 - j]);
        }
        if (delta == 0) {
            continue;
        }
        t = b;
        for (int i = 0; i < r - mm; ++i) {
            t.insert(t.begin(), 0);
        }
        mul(t, delta);
        t = vector_poly_add(a, t);
        if (2 * l < r) {
            b = a;
            mul(b, poly_pow(delta, -1));
            l = r - l;
            mm = r;
        }
        a = t;
    }
    for (int i = 0; i < n; ++i) {
        if (calc_vector(a, alpha[n - i]) == 0) {
            v[i] ^= 1;
        }
    }
}

[[nodiscard]] inline double simulate(
        vector<poly> &g,
        vector<poly> &alpha,
        double noise_level,
        int number_of_iterations,
        int max_errors
        ) {

    int i = 0, error_count = 0;
    vector<poly> generated(k), noisy, encoded(n), decoded(n);
    while (i < number_of_iterations && error_count < max_errors) {
        // create new vector
        for (poly & j : generated) {
            j = (poly)get_random() & 1;
        }
        // encode it
        encode(g, generated, encoded);
        // add noise
        noisy = encoded;
        for (int & j : noisy) {
            if (distribution(get_random) < noise_level) {
                j ^= 1;
            }
        }
        // decode noise
        decode(g, noisy, alpha);
        // compare with encoded
        if (encoded != noisy) {
            error_count++;
        }

        i++;
    }
    return (double)error_count / i;
}

int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
    cout << setprecision(10);

    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);

    cin >> n >> primitive >> d;

    m = (int)log2(n + 1);

    precalc_mul_table();

    // calc alphas
    vector<poly> alpha = {1};
    for (int i = 0; i < n; ++i) {
        alpha.push_back(poly_mul(alpha.back(), 2));
    }

    /*cout << "alphas\n";
    for (auto i: alpha) {
        cout << bitset<3>(i) << "\n";
    }*/

    // calc cyclotomic
    vector<int> used(n, 0);
    map<int, vector<int>> cyclotomic;
    for (int i = 0; i < n - 1; ++i) {
        if (!used[i]) {
            cyclotomic[i] = vector<int>();
            int j = i;
            while (!used[j]) {
                cyclotomic[i].push_back(j);
                used[j] = 1;
                j = (j * 2) % n;
            }
        }
    }

    /*cout << "\ncycles\n";
    for (const auto &[key, v]: cyclotomic) {
        cout << key << ": ";
        for (auto i: v) {
            cout << i << " ";
        }
         cout << "\n";
    }*/

    // calc minimals
    vector<vector<poly>> minimals;
    for (int i = 1; i < d; ++i) {
        if (cyclotomic.find(i) != cyclotomic.end())  {
            vector<poly> minimal{alpha[cyclotomic[i][0]], 1};
            for (int j = 1; j < cyclotomic[i].size(); ++j) {
                const vector<poly> cur_multiplier{alpha[cyclotomic[i][j]], 1};
                minimal = vector_poly_mul(minimal, cur_multiplier);
            }
            minimals.push_back(minimal);
        }
    }

    /*cout << "\nminimals\n";
    for (auto &minimal: minimals) {
        for (auto c: minimal) {
            cout << c << " ";
        }
        cout << "\n";
    }*/

    // calc generating
    vector<poly> g = minimals[0];
    for (int i = 1; i < minimals.size(); ++i) {
        g = vector_poly_mul(g, minimals[i]);
    }

    k = n - (int)g.size() + 1;
    cout << k << "\n";

    for (auto gi: g) {
        cout << gi << " ";
    }
    cout << "\n";

    vector<poly> res(n);
    string line;
    while (getline(cin, line)) {
        istringstream iss(line);
        string command;
        iss >> command;
        if (command == "Encode") {
            int t;
            vector<poly> v;
            while (iss >> t) {
                v.push_back(t);
            }
            encode(g, v, res);
            for (auto ri: res) {
                cout << ri << " ";
            }
            cout << "\n";
        } else if (command == "Decode") {
            int t;
            vector<poly> v;
            while (iss >> t) {
                v.push_back(t);
            }
            decode(g, v, alpha);
            for (auto ri: v) {
                cout << ri << " ";
            }
            cout << "\n";
        } else if (command == "Simulate") {
            double noise_level;
            int number_of_iterations, max_errors;
            iss >> noise_level >> number_of_iterations >> max_errors;
            cout << simulate(g, alpha, noise_level, number_of_iterations, max_errors) << "\n";
        }
    }
}