#include <iostream>
#include <vector>
#include <cmath>
 
using namespace std;
 
int main() {
    int n;
    cin >> n;
    vector<int> p(n + 1);
    for (int i = 0; i < n; i++) {
        cin >> p[i + 1];
    }
 
    vector<vector<int>> dp(n + 1,vector<int>(log2(n) + 2, 0));
 
    for (int i = 1; i <= n; i++) {
        dp[i][0] = p[i];
    }
    for (int j = 1; j <= log2(n); j++) {
        for (int i = 1; i <= n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
        }
    }
 
    for (int i = 1; i <= n; i++) {
        cout << i << ":";
        for (int j : dp[i]) {
            if (j == 0)
                break;
            cout << " " << j;
        }
        cout << "\n";
    }
 
    return 0;
}