#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <vector>
#include <string>

using namespace std;

int main()
{
	//freopen("input.txt", "r", stdin);
	//freopen("output.txt", "w", stdout);

	string s;
	int n, m;
	cin >> n >> m;
	vector<vector<char>> board(n, vector<char>(m, '.'));
	vector<vector<vector<unsigned long long>>> dp(n + 1,
		vector<vector<unsigned long long>>(m + 1, 
			vector<unsigned long long>(pow(2, m), 0)));

	for (int i = 0; i < n; i++) {
		cin >> s;
		for (int j = 0; j < m; j++)
			if (s[j] == 'X')
				board[i][j] = 'X';
	}
	dp[0][0][0] = 1;

	for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int p = 0; p < pow(2, m); p++) {
					if (board[i][j] == 'X') {
						if (!(p & (1 << j))) {
							dp[i][j + 1][p] += dp[i][j][p];
						}
					}
					else {
						if (p & (1 << j)) {
							dp[i][j + 1][p - (1 << j)] += dp[i][j][p];
						}
						else {
							dp[i][j + 1][p + (1 << j)] += dp[i][j][p];
							if (j < m - 1 && !(p & (1 << (j + 1)))) {
								dp[i][j + 1][p + (1 << j + 1)] += dp[i][j][p];
							}
						}
					}
				}
			}
		for (int p = 0; p < pow(2, m); p++) {
				dp[i + 1][0][p] = dp[i][m][p];
		}
	}

	cout << dp[n][0][0];

	return 0;
}