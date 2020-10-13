#include <iostream>
#include <vector>
#include <string>
#include <math.h>
 
using namespace std;
 
int main() {
	//freopen("input.txt", "r", stdin);
	//freopen("output.txt", "w", stdout);
	ios::sync_with_stdio(false);
	cin.tie();
 
	int n, m, a0, u, v, k;
	cin >> n >> m >> a0 >> u >> v;
	vector<int> a(n);
	a[0] = a0;
	for (int i = 1; i < n; i++) {
		a[i] = (23 * a[i - 1] + 21563) % 16714589;
	}
 
	vector<int> p(n+1);
	int z = 1, zn = -1;
	for(int i = 0; i <= n; i++) {
		if (i == z) {
			z *= 2;
			zn++;
		}
		p[i] = zn;
	}
 
	vector<vector<int>> t(n, vector<int>(p[n]+1));
 
	for (int i = 0; i < n; i++) {
		t[i][0] = a[i];
	}
	for (int k = 1; k <= p[n]; k++) {
		for (int i = 0; i <= n - pow(2, k); i++) {
			t[i][k] = __min(t[i][k - 1], t[i + pow(2, k - 1)][k - 1]);
		}
	}
 
	long long r;
	if (v > u) {
		k = p[v - u + 1];
		r = __min(t[u - 1][k], t[v - pow(2, k)][k]);
	}
	else {
		k = p[u - v + 1];
		r = __min(t[v - 1][k], t[u - pow(2, k)][k]);
	}
	for (int i = 1; i < m; i++) {
		u = ((17 * u + 751 + r + 2 * i) % n) + 1;
		v = ((13 * v + 593 + r + 5 * i) % n) + 1;
		if (v > u) {
			k = p[v - u + 1];
			r = __min(t[u - 1][k], t[v - pow(2, k)][k]);
		}
		else {
			k = p[u - v + 1];
			r = __min(t[v - 1][k], t[u - pow(2, k)][k]);
		}
	}
 
	cout << u << " " << v << " " << r;
 
	return 0;
}