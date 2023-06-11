#pragma GCC optimize("Ofast")

#include <iostream>
#include <vector>
#include <map>
#include <sstream>
#include <cmath>
#include <random>
#include <iomanip>

using namespace std;


// utils
struct Vertex {
    double score = -1;
    pair<int, Vertex*>* path_to_current = nullptr;
    vector<pair<int, Vertex*>> in;
    vector<pair<int, int>> active; // {activeRow, value}
    Vertex(): in(), active() {}
};

[[nodiscard]] inline vector<vector<int>> deep_copy(vector<vector<int>> &m) {
    vector<vector<int>> t(m.size(), vector<int>(m[0].size()));
    for (int i = 0; i < m.size(); ++i) {
        for (int j = 0; j < m[i].size(); ++j) {
            t[i][j] = m[i][j];
        }
    }
    return t;
}

inline void to_msf(vector<vector<int>> &m) {
    // assume g filled with 0|1

    // bottom triangle
    for (int col = 0, row = 0; row < m.size() && col < m[0].size(); col++) {
        if (m[row][col] == 0) {
            // make 1 in current row
            int i = row;
            while (++i < m.size()) {
                if (m[i][col] == 1) break;
            }
            if (i == m.size()) {
                continue; // all zeros in col
            }
            for (int k = 0; k < m[i].size(); ++k) {
                m[row][k] = m[row][k] ^ m[i][k];
            }
        }

        for (int i = row + 1; i < m.size(); i++) {
            if (m[i][col] == 1) {
                for (int k = 0; k < m[row].size(); ++k) {
                    m[i][k] = m[row][k] ^ m[i][k];
                }
            }
        }

        row++;
    }

    // top triangle
    vector<int> used(m.size(), 0);
    for (int i = 0, col = (int)m[0].size() - 1; i < m.size() && col >= 0; col--) {
        int row = (int)m.size() - 1;
        // find first (from bottom) unused row with 1
        while (row >= 0 && (used[row] == 1 || m[row][col] == 0)) {
            row--;
        }
        if (row >= 0) {
            ++i;
            used[row] = 1;
            // subtract other rows to 0 if they have 1 on current col
            for (int j = row - 1; j >= 0; --j) {
                if (m[j][col] == 1) {
                    for (int k = col; k >= 0; --k) {
                        m[j][k] = m[j][k] ^ m[row][k];
                    }
                }
            }
        }
    }
}

inline void process_vertex(
        int i,
        Vertex *from_v,
        vector<vector<int>> &msf,
        vector<pair<int, int>> &active,
        vector<pair<int, int>> &cur_active,
        map<vector<pair<int, int>>, Vertex> &next_layer
        ) {
    // count color before deleting inactive
    int color = 0;
    for (auto & j : cur_active) {
        color ^= j.second & msf[j.first][i];
    }

    // remove inactive
    for (int j = 0; j < cur_active.size(); ++j) {
        if (active[cur_active[j].first].second < i) {
            cur_active.erase(cur_active.begin() + j);
            break;
        }
    }

    // add to map if not exists
    if (next_layer.find(cur_active) == next_layer.end()) {
        Vertex to = Vertex();
        to.active = cur_active;
        next_layer[to.active] = to;
    }

    next_layer[cur_active].in.emplace_back(color, from_v);
}

[[nodiscard]] inline vector<vector<Vertex>> make_grid(vector<vector<int>> &msf) {
    // active: row -> active_range
    vector<pair<int, int>> active(msf.size());
    for (int i = 0; i < msf.size(); ++i) {
        auto v = msf[i];

        int b = 0;
        while (v[b] == 0) { b++; }

        int e = (int)v.size() - 1;
        while (v[e] == 0) { e--; }

        active[i] = pair(b, e - 1);
    }

    // fill vertices: layer_i -> active_mask -> Vertex
    vector<vector<Vertex>> grid(msf[0].size() + 1, vector<Vertex>());
    auto start = Vertex();
    grid[0] = {start};
    vector<Vertex> prev_layer = {start};
    map<vector<pair<int, int>>, Vertex> next_layer;
    for (int i = 0; i < msf[0].size(); ++i) {
        next_layer.clear();
        // find if new active layer
        int new_active = -1;
        for (int j = 0; j < active.size(); ++j) {
            if (active[j].first == i) {
                new_active = j;
                break;
            }
        }

        // add edges from prev layer to next
        for (auto & from_v : grid[i]) {
            auto from_active = from_v.active;

            if (new_active == -1) { // one edge
                process_vertex(
                        i,
                        &from_v,
                        msf,
                        active,
                        from_active,
                        next_layer
                );
            } else { // two edges
                // on 0
                auto active0 = from_active;
                active0.emplace_back(new_active, 0);
                process_vertex(
                        i,
                        &from_v,
                        msf,
                        active,
                        active0,
                        next_layer
                );


                // on 1
                auto active1 = from_active;
                active1.emplace_back(new_active, 1);

                process_vertex(
                        i,
                        &from_v,
                        msf,
                        active,
                        active1,
                        next_layer
                );
            }
        }

        // shift layers
        for (auto const &[k, v]: next_layer) {
            grid[i + 1].push_back(v);
        }
    }

    return grid;
}


// methods
inline void encode(vector<vector<int>> &g, vector<int> &data, vector<int> &res) {
    fill(res.begin(), res.end(), 0);
    for (int i = 0; i < res.size(); ++i) {
        for (int j = 0; j < data.size(); ++j) {
            res[i] ^= data[j] & g[j][i];
        }
    }
}

inline void decode(vector<vector<Vertex>> &grid, vector<double> &data, vector<int> &res) {
    fill(res.begin(), res.end(), 0);
    grid[0][0].score = 0;

    // minimize scalar mul layer by layer for all input edges on vertex
    for (int i = 1; i < grid.size(); ++i) {
        for (int j = 0; j < grid[i].size(); ++j) {
            grid[i][j].path_to_current = nullptr;
            for (int k = 0; k < grid[i][j].in.size(); ++k) {
                double cur_score = (grid[i][j].in[k].second->score) + (grid[i][j].in[k].first == 0 ? 1.0 : -1.0) * data[i - 1];
                if (grid[i][j].path_to_current == nullptr || cur_score > grid[i][j].score) {
                    grid[i][j].score = cur_score;
                    grid[i][j].path_to_current = &grid[i][j].in[k];
                }
            }
        }
    }

    // collect path_to_current
    int i = (int)res.size() - 1;
    Vertex cur = grid.back()[0];
    while (cur.path_to_current) {
        res[i--] = cur.path_to_current->first;
        cur = *(cur.path_to_current->second);
    }
}

[[nodiscard]] inline double simulate(
        double noise_level,
        int number_of_iterations,
        int max_errors,
        vector<vector<Vertex>> &grid,
        vector<vector<int>> &g
        ) {
    mt19937 get_random((random_device()) ());
    double std = sqrt((double)pow(10, -noise_level / 10) * ((double)g[0].size() / (double)g.size()) / 2);
    normal_distribution<double> distribution{0, std};
    int i = 0, error_count = 0;
    vector<int> generated(g.size()), encoded(g[0].size()), decoded(g[0].size());
    vector<double> noisy(encoded.size());
    while (i < number_of_iterations && error_count < max_errors) {
        for (int & j : generated) {
            j = (int)get_random() & 1;
        }
        encode(g, generated, encoded);
        for (int j = 0; j < encoded.size(); ++j) {
            noisy[j] = (encoded[j] == 0 ? 1 : -1) + distribution(get_random);
        }
        decode(grid, noisy, decoded);
        for (int j = 0; j < encoded.size(); ++j) {
            if (encoded[j] != decoded[j]) {
                error_count++;
                break;
            }
        }

        i++;
    }
    return (double)error_count / (double)i;
}


int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
    cout << setprecision(10);

    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);

    int n, k;
    cin >> n >> k;

    vector<vector<int>> g(k, vector<int>(n)); // k * n
    for (int i = 0; i < k; ++i) {
        for (int j = 0; j < n; ++j) {
            cin >> g[i][j];
        }
    }

    auto msf = deep_copy(g);
    to_msf(msf);
    auto grid = make_grid(msf);


    for (const auto & i : grid) {
        cout << i.size() << " ";
    }
    cout << "\n";

    string line;
    vector<int> result(n); // n

    while (getline(cin, line)) {
        istringstream iss(line);
        string command;
        iss >> command;
        string res;
        if (command == "Encode") {
            int t;
            vector<int> data(0); // k
            while (iss >> t) {
                data.push_back(t);
            }
            encode(g, data, result);
            for (int i : result) {
                cout << i << " ";
            }
            cout << "\n";
        } else if (command == "Decode") {
            double t;
            vector<double> data(0);
            while (iss >> t) {
                data.push_back(t);
            }
            decode(grid, data, result);
            for (int i : result) {
                cout << i << " ";
            }
            cout << "\n";
        } else if (command == "Simulate") {
            double noise_level;
            int number_of_iterations, max_errors;
            iss >> noise_level >> number_of_iterations >> max_errors;
            cout << simulate(noise_level, number_of_iterations, max_errors, grid, g) << "\n";
        }
    }

    return 0;
}
