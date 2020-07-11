#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <vector>
#include <string>

using namespace std;

int main()
{
	//freopen("input.txt", "r", stdin);
	//freopen("output.txt", "w", stdout);

	int n, m;
	cin >> n >> m;
	if (n < m) {
		swap(n, m);
	}

	vector<vector<vector<unsigned long long>>> dp(n + 1,
		vector<vector<unsigned long long>>(m + 1,
			vector<unsigned long long>(pow(2, m + 1), 0)));

	dp[0][0][0] = 1;

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			for (int p = 0; p < pow(2, m + 1); p++) {
				for (int t = 0; t <= 1; t++) {
					if (i == 0 || j == 0 || (((p >> j - 1) & 1) != t) || 
											(((p >> j) & 1) != t) || 
											(((p >> j + 1) & 1) != t)) {
						dp[i][j + 1][((p & ~(1 << j)) | (t << j))] +=
							dp[i][j][p];
					}
				}
			}
		}
		for (int p = 0; p < pow(2, m + 1); p++) {
			dp[i + 1][0][(p & ~(1 << m)) << 1] += dp[i][m][p];
		}
	}

	unsigned long long res = 0;
	for (int i = 0; i < pow(2, m + 1); i++)
		res += dp[n][0][i];

	cout << res;

	return 0;
}