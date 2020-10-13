#include <iostream>
#include <string>
#include <math.h>
#include <vector>
 
using namespace std;
 
int k = 1, e = 10e8;
vector<int> parking;
 
void make_tree(int ind) {
	if (ind > k - 2)
		return;
	make_tree(2 * ind + 1);
	make_tree(2 * ind + 2);
	parking[ind] = __min(parking[2*ind+1], parking[2*ind+2]);
}
 
int get_min(int l, int r, int ind, int lx, int rx) {
	if (rx < l || r < lx)
		return e;
	if (lx <= l && r <= rx)
		return parking[ind];
	int m = (l + r + 1) / 2 - 1;
	int lm = get_min(l, m, 2 * ind + 1, lx, rx);
	int rm = get_min(m+1, r, 2 * ind + 2, lx, rx);
	return __min(lm, rm);
}
 
void upd(int ind, int mode) {
	if (mode == 1) {
		parking[ind + k - 1] = e;
	}
	else {
		parking[ind + k - 1] = ind;
	}
	ind = ind + k - 1;
	while (ind != 0) {
		ind = (ind - 1) / 2;
		parking[ind] = __min(parking[2 * ind + 1], parking[2 * ind + 2]);
	}
}
 
int main() {
	freopen("parking.in", "r", stdin);
	freopen("parking.out", "w", stdout);
 
	int n, m;
	cin >> n >> m;
	while (k < n) {
		k *= 2;
	}
	parking.resize(2 * k - 1, e);
	for (int i = 0; i < n; i++) {
		parking[k - 1 + i] = i;
	}
	make_tree(0);
 
	string s;
	int x;
	while (cin >> s) {
		cin >> x;
		if (s == "enter") {
			int after = get_min(0, k-1, 0, x-1, n-1);
			if (after == e) {
				after = get_min(0, k - 1, 0, 0, x - 2);
			}
			upd(after, 1);
			cout << after + 1 << "\n";
		}
		else {
			upd(x - 1, 0);
		}
	}
 
	return 0;
}