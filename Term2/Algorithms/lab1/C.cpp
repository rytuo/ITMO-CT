#include <iostream>
#include <vector>
#include <string>
#include <math.h>
 
using namespace std;
typedef long long ll;
 
int n, k = 1;
const ll e = LLONG_MAX;
vector<ll> a;
vector<pair<ll, ll>> upd;
 
void build_tree(int ind) {
	if (ind > k - 2) {
		return;
	}
	build_tree(2 * ind + 1);
	build_tree(2 * ind + 2);
	ll lm = a[2 * ind + 1];
	ll rm = a[2 * ind + 2];
	a[ind] = __min(a[2 * ind + 1], a[2 * ind + 2]);
}
 
void prop(int ind, int l, int r) {
	if (r - l == 0)
		return;
	if (upd[ind].first == e && upd[ind].second == 0)
		return;
	if (upd[ind].first == e) {
		upd[2 * ind + 1].second += upd[ind].second;
		a[2 * ind + 1] += upd[ind].second;
		upd[2 * ind + 2].second += upd[ind].second;
		a[2 * ind + 2] += upd[ind].second;
	}
	else {
		upd[2 * ind + 1].first = upd[ind].first;
		upd[2 * ind + 1].second = upd[ind].second;
		a[2 * ind + 1] = upd[ind].first + upd[ind].second;
		upd[2 * ind + 2].first = upd[ind].first;
		upd[2 * ind + 2].second = upd[ind].second;
		a[2 * ind + 2] = upd[ind].first + upd[ind].second;
	}
	upd[ind].first = e;
	upd[ind].second = 0;
}
 
void op(int l, int r, pair<ll,ll> x, int ind, int lx, int rx) {
	prop(ind, l, r);
	if (l > rx || r < lx)
		return;
	if (lx <= l && r <= rx) {
		upd[ind] = x;
		if (x.first != e)
			a[ind] = x.first;
		a[ind] += x.second;
		return;
	}
	int m = (l + r + 1) / 2 - 1;
	op(l, m, x, 2 * ind + 1, lx, rx);
	op(m + 1, r, x, 2 * ind + 2, lx, rx);
	a[ind] = __min(a[2*ind+1], a[2*ind+2]);
}
 
ll get_min(int l, int r, int ind, int lx, int rx) {
	prop(ind, l, r);
	if (l > rx || r < lx)
		return e;
	if (lx <= l && r <= rx)
		return a[ind];
	int m = (l + r + 1) / 2 - 1;
	ll lm = get_min(l, m, 2*ind+1, lx, rx);
	ll rm = get_min(m+1, r, 2*ind+2, lx, rx);
	return __min(lm, rm);
}
 
int main() {
	//freopen("input.txt", "r", stdin);
	//freopen("output.txt", "w", stdout);
	ios::sync_with_stdio(false);
	cin.tie();
 
	cin >> n;
	while (k < n) {
		k *= 2;
	}
	a.resize(2 * k - 1, e);
	upd.resize(2 * k - 1, make_pair(e, 0));
	for (int i = 0; i < n; i++) {
		cin >> a[k - 1 + i];
	}
 
	build_tree(0);
 
	string s;
	int i, j;
	ll x;
	while (cin >> s) {
		if (s == "set") {
			cin >> i >> j >> x;
			op(0, k - 1, make_pair((ll)x, (ll)0), 0, i - 1, j - 1);
		}
		else if (s == "add") {
			cin >> i >> j >> x;
			op(0, k - 1, make_pair(e, (ll)x), 0, i - 1, j - 1);
		}
		else {
			cin >> i >> j;
			cout << get_min(0, k-1, 0, i-1, j-1) << "\n";
		}
	}
 
	return 0;
}