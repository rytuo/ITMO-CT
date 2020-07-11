#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

int main()
{
	freopen("skyscraper.in", "r", stdin);
	freopen("skyscraper.out", "w", stdout);

	int n, w;
	cin >> n >> w;
	vector<int> c(n);
	for (int i = 0; i < n; i++)
		cin >> c[i];

	vector<pair<int, long long>> d(pow(2, n), pair<int, long long>(0, 0));
	long long fir, sec;
	d[0] = pair<int, long long>(0, 0);
	vector<bool> a(pow(2, n), false);


	for (int x = 0; x < pow(2, n); x++) {
		for (int i = 0; i < n; i++) {
			if ((x & (1 << i)) == 0) {
				if (c[i] + d[x].second <= w) {
					fir = d[x].first;
					sec = d[x].second + c[i];
				}
				else {
					fir = d[x].first + 1;
					sec = c[i];
				}
				if (a[x + (1 << i)]) {
					d[x + (1 << i)] = min(d[x + (1 << i)],
						pair<int, long long>(fir, sec));
				}
				else {
					d[x + (1 << i)] = pair<int, long long>(fir, sec);
					a[x + (1 << i)] = true;
				}
			}
		}
	}

	int num;
	if (d[pow(2, n) - 1].second == 0)
		num = d[pow(2, n) - 1].first;
	else
		num = d[pow(2, n) - 1].first + 1;

	cout << num << endl;
	vector<vector<int>> res(num, vector<int>(0));
	int last = pow(2, n) - 1, level = 0;

	for (int j = 0; j < n; j++) {
		for (int i = 0; i < n; i++) {
			if ((last >> i) & 1) {
				if (d[last - (1 << i)].first == d[last].first) {
					if (d[last - (1 << i)].second + c[i] == d[last].second) {
						res[level].push_back(i + 1);
						last -= (1 << i);
						break;
					}
				}
				else if (d[last - (1 << i)].first + 1 == d[last].first &&
					d[last].second == c[i]) {
					res[level++].push_back(i + 1);
					last -= (1 << i);
					break;
				}
			}
		}
	}

	for (int i = 0; i < res.size(); i++) {
		cout << res[i].size() << " ";
		for (int j = 0; j < res[i].size(); j++) {
			cout << res[i][j] << " ";
		}
		cout << endl;
	}

	return 0;
}