#define _CRT_SECURE_NO_WARNINGS

#include<iostream>
#include <vector>
#include <string>
#include <map>

using namespace std;

vector<int> a;
vector<int> len;
vector<int> experience;
map <int, vector<int>> myClan;

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

		myClan[y].insert(myClan[y].end(), myClan[x].begin(), 
			myClan[x].end());

		myClan.erase(x);
	}
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(0);

	int n, m, x, y;
	cin >> n >> m;
	a.resize(n);
	len.resize(n, 1);
	experience.resize(n, 0);

	for (int i = 0; i < n; i++) {
		a[i] = i;
		myClan[i].push_back(i);
	}

	string s;
	for (int i = 0; i < m; i++) {
		cin >> s;
		if (s == "join") {
			cin >> x >> y;
			unite(x - 1, y - 1);
		}
		else if (s == "add") {
			cin >> x >> y;
			for (int i : myClan[find(x - 1)])
				experience[i] += y;
		}
		else {
			cin >> x;
			cout << experience[x - 1] << endl;
		}
	}

	cin >> n;
	return 0;
}