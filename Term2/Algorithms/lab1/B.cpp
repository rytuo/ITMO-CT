#include <iostream>
#include <math.h>
#include <vector>
#include <string>
 
typedef long long int ll;
 
using namespace std;
 
int k = 1;
vector<ll> tree;
 
void build_tree(int ind) {
	if (ind >= k - 1) {
		return;
	}
	build_tree(2 * ind + 1);
	build_tree(2 * ind + 2);
	tree[ind] = tree[2 * ind + 1] + tree[2 * ind + 2];
	return;
}
 
ll get_sum(int lb, int rb, int ind, int l, int r) {
	if (l <= lb && r >= rb) {
		return tree[ind];
	}
	if (r < lb || l > rb) {
		return 0;
	}
	int m = (rb + lb + 1) / 2 - 1;
	ll sl = get_sum(lb, m, 2*ind+1, l, r);
	ll sr = get_sum(m + 1, rb, 2*ind+2, l, r);
	return sl + sr;
}
 
void set(int ind) {
	if (ind == 0) {
		return;
	}
	ind = (ind - 1) / 2;
	tree[ind] = tree[ind * 2 + 1] + tree[ind * 2 + 2];
	set(ind);
	return;
}
 
int main() {
	//freopen("input.txt", "r", stdin);
	//freopen("output.txt", "w", stdout);
 
	int n;
	cin >> n;
	while (k < n) {
		k *= 2;
	}
 
	tree.resize(2 * k - 1, 0);
	for (int i = 0; i < n; i++) {
		cin >> tree[k - 1 + i];
	}
 
	build_tree(0);
 
	string s;
	ll i, j;
	while (cin >> s) {
		cin >> i >> j;
		if (s == "sum") {
			cout << get_sum(0, k - 1, 0, i - 1, j - 1) << endl;
		}
		else {
			tree[k - 2 + i] = j;
			set(k - 2 + i);
		}
	}
 
	return 0;
}