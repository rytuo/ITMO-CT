#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <vector>
#include <string>


using namespace std;

int main()
{
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);

	int n, m;
	cin >> n >> m;
	vector<vector<int>> a(n, vector<int>(m));
	
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++)
			cin >> a[i][j];
	}

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			int max;
			if (i == 0) {
				if (j == 0) {
					max = 0;
				}
				else {
					max = a[i][j - 1];
				}
			}
			else {
				if (j == 0) {
					max = a[i - 1][j];
				}
				else {
					if (a[i - 1][j] > a[i][j - 1])
						max = a[i - 1][j];
					else
						max = a[i][j - 1];
				}
			}
			a[i][j] += max;
		}
	}

	int i = n - 1, j = m - 1;
	vector<char> res;
	while (i > 0 || j > 0) {
		if (i == 0) {
			j--;
			res.push_back('R');
		}
		else if (j == 0) {
			i--;
			res.push_back('D');
		}
		else {
			if (a[i - 1][j] > a[i][j - 1]) {
				i--;
				res.push_back('D');
			} 
			else {
				j--;
				res.push_back('R');
			}
		}	
	}

	cout << a[n - 1][m - 1] << endl;
	for (int i = res.size() - 1; i >= 0; i--)
		cout << res[i];
	return 0;
}