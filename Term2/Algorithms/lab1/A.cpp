#include <stdio.h>
#include <vector>
#include <iostream>
 
typedef long long int ull;
using namespace std;
 
int main() {
	//freopen("input.txt", "r", stdin);
	//freopen("output.txt", "w", stdout);
	ios::sync_with_stdio(false);
	cin.tie();
 
	int n, x, y;
	cin >> n >> x >> y;
	vector<ull> a(n);
	cin >> a[0];
 
	for (int i = 1; i < n; i++) {
		a[i] = (x * a[i - 1] + y) % 65536;
	}
	for (int i = 1; i < n; i++) {
		a[i] += a[i - 1];
	}
 
	int m, z, t;
	cin >> m >> z >> t;
	if (m == 0) {
		cout << 0 << endl;
	}
	else {
		vector<int> c(2 * m);
		cin >> c[0];
		for (int i = 1; i < 2 * m; i++) {
			c[i] = ((long long)z * c[i - 1] + t + 1073741824) % 1073741824;
		}
		for (int i = 0; i < 2 * m; i++) {
			c[i] = c[i] % n;
		}
 
		ull result = 0;
		int l, r;
		for (int i = 0; i < m; i++) {
			if (c[2 * i] <= c[2 * i + 1]) {
				l = c[2 * i];
				r = c[2 * i + 1];
			}
			else {
				l = c[2 * i + 1];
				r = c[2 * i];
			}
			if (l == 0) {
				result += a[r];
			}
			else {
				result += a[r] - a[l - 1];
			}
		}
 
		cout << result;
	}
 
	return 0;
}