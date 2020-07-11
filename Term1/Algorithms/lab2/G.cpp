#define _CRT_SECURE_NO_WARNINGS

#include<iostream>
#include <vector>
#include <cmath>
#include <string>

using namespace std;

vector<int> a;
vector<int> len;
vector<int> mx;
vector<int> mn;

int find(int set) {
	if (a[set] == set) return set;
	else {
		a[set] = find(a[set]);
		return a[set];
	}
}

void unite(int set1, int set2) {
	int x = find(set1);
	int y = find(set2);
	if (x != y) {
		if (len[x] > len[y])
			swap(x, y);
		a[x] = y;
		len[y] += len[x];
		if (mx[x] > mx[y]) mx[y] = mx[x];
		if (mn[x] < mn[y]) mn[y] = mn[x];
	}
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(0);

	int n, set1, set2, top;
	cin >> n;
	a.resize(n);
	len.resize(n, 1);
	mx.resize(n);
	mn.resize(n);

	for (int i = 0; i < n; i++) {
		a[i] = i;
		mx[i] = i;
		mn[i] = i;
	}

	string s;
	while (cin >> s) {
		if (s == "union") {
			cin >> set1 >> set2;
			unite(set1 - 1, set2 - 1);
		}
		else {
			cin >> set1;
			top = find(set1 - 1);
			cout << mn[top] + 1 << " " << mx[top] + 1
				<< " " << len[top] << endl;
		}
	}

	cin >> n;
	return 0;
}