#include <iostream>
#include <vector>
#include <cmath>
 
using namespace std;
 
int n;
vector<vector<int>> dp;
vector<int> depth;
 
int lca(int u, int v) {
    if (depth[u] < depth[v]) // d[u] >= d[v]
        swap(u, v);
    for (int i = log2(n); i >= 0; i--) {
        if (depth[dp[u][i]] - depth[v] >= 0)
            u = dp[u][i];
    } // d[u] = d[v]
    if (u == v)
        return u;
    for (int i = log2(n); i >= 0; i--) {
        if (dp[u][i] != dp[v][i]) {
            u = dp[u][i];
            v = dp[v][i];
        }
    }
    return dp[u][0];
}
 
int main() {
    int m, u, v;
    cin >> n;
    vector<int> anc(n + 1, 1);
    depth.resize(n + 1, 0);
    for (int i = 2 ; i <= n; i++) {
        cin >> anc[i];
        depth[i] = 1 + depth[anc[i]]; // 1 <= anc[i] < i
    }
    cin >> m;
    dp.resize(n + 1, vector<int>(log2(n) + 1, 1));
    for (int i = 1; i <= n; i++)
        dp[i][0] = anc[i];
    for (int j = 1; j <= log2(n); j++) {
        for (int i = 1; i <= n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
        }
    }
 
    for (int i = 0; i < m; i++) {
        cin >> u >> v;
        cout << lca(u, v) << "\n";
    }
 
    return 0;
}