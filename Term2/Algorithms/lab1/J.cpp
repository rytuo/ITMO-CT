#include <iostream>
#include <vector>
#include <string>
#include <math.h>
 
using namespace std;
 
int n, m, k = 1;
vector<pair<int, int>> wall;
pair<int, int> e;
 
void make_tree(int ind) {
	if (ind >= k - 1) {
		if (ind > k + n - 2) {
			wall[ind] = e;
		}
		else {
			wall[ind].second = ind - k + 1;
		}
	}
	else {
		make_tree(2 * ind + 1);
		make_tree(2 * ind + 2);
		wall[ind] = wall[2 * ind + 1];
	}
}
 
void prop(int ind, int l, int r) {
	if (r - l <= 0) {
		return;
	}
	if (wall[ind].first > wall[2 * ind + 1].first) {
		wall[2 * ind + 1].first = wall[ind].first;
	}
	if (wall[ind].first > wall[2 * ind + 2].first) {
		wall[2 * ind + 2].first = wall[ind].first;
	}
}
 
void upd(int l, int r, int val, int ind, int lx, int rx) {
	prop(ind, l, r);
	if (l > rx || r < lx) {
		return;
	}
	if (lx <= l && r <= rx) {
		if (wall[ind].first < val) {
			wall[ind].first = val;
		}
		return;
	}
	int m = (l + r + 1) / 2 - 1;
	upd(l, m, val, 2 * ind + 1, lx, rx);
	upd(m + 1, r, val, 2 * ind + 2, lx, rx);
	wall[ind] = __min(wall[2 * ind + 1], wall[2 * ind + 2]);
}
 
pair<int, int> check(int l, int r, int ind, int lx, int rx) {
	prop(ind, l, r);
	if (l > rx || r < lx) {
		return e;
	}
	if (lx <= l && r <= rx) {
		return wall[ind];
	}
	int m = (l + r + 1) / 2 - 1;
	pair<int, int> lm, rm;
	lm = check(l, m, 2 * ind + 1, lx, rx);
	rm = check(m+1, r, 2 * ind + 2, lx, rx);
	return __min(lm, rm);
}
 
int main() {
	//freopen("input.txt", "r", stdin);
	//freopen("output.txt", "w", stdout);
	ios::sync_with_stdio(false);
	cin.tie();
 
	cin >> n >> m;
	while (k < n) {
		k *= 2;
	}
	wall.resize(2 * k - 1);
	e = make_pair(10e8, 10e8);
	make_tree(0);
 
	string s;
	int a, b, c;
	for (int i = 0; i < m; i++) {
		cin >> s;
		if (s == "defend") {
			cin >> a >> b >> c;
			upd(0, k-1, c, 0, a - 1, b - 1);
		}
		else {
			cin >> a >> b;
			pair<int, int> ans = check(0, k-1, 0, a - 1, b - 1);
			cout << ans.first << " " << ans.second + 1 << "\n";
		}
	}
 
	return 0;
}