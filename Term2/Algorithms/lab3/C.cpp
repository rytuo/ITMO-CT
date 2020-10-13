#include <iostream>
#include <vector>
#include <cmath>
 
using namespace std;
 
int n;
vector<vector<int>> cost;
vector<vector<int>> dp;
vector<int> depth;
 
int min(int l, int r) {
    return (l < r ? l : r);
}
 
int lca(int u, int v) {
    int res = INT32_MAX;
    if (depth[u] < depth[v]) // d[u] >= d[v]
        swap(u, v);
    for (int i = log2(n); i >= 0; i--) {
        if (depth[dp[u][i]] - depth[v] >= 0) {
            res = min(res, cost[u][i]);
            u = dp[u][i];
        }
    } // d[u] = d[v]
    if (u == v)
        return res;
    for (int i = log2(n); i >= 0; i--) {
        if (dp[u][i] != dp[v][i]) {
            res = min(res, min(cost[u][i], cost[v][i]));
            u = dp[u][i];
            v = dp[v][i];
        }
    }
    return min(res, min(cost[u][0], cost[v][0]));
}
 
int main() {
    freopen("minonpath.in", "r", stdin);
    freopen("minonpath.out", "w", stdout);
    int m, u, v;
    cin >> n;
    depth.resize(n + 1, 0);
    cost.resize(n + 1, vector<int>(log2(n) + 1, INT32_MAX));
    dp.resize(n + 1, vector<int>(log2(n) + 1, 1));
 
    for (int i = 2 ; i <= n; i++) {
        cin >> dp[i][0] >> cost[i][0];
        depth[i] = 1 + depth[dp[i][0]];
    }
    cin >> m;
    for (int j = 1; j <= log2(n); j++) {
        for (int i = 1; i <= n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
            cost[i][j] = min(cost[i][j - 1], cost[dp[i][j - 1]][j - 1]);
        }
    }
 
    for (int i = 0; i < m; i++) {
        cin >> u >> v;
        cout << lca(u, v) << "\n";
    }
 
    return 0;
}