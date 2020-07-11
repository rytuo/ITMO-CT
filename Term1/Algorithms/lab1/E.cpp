#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <string>
#include <vector>

using namespace std;

int n;
vector<int> a;

int binl(int y) {
	int l = -1, r = n, m;
	while (r - l > 1) {
		m = (l + r) / 2;
		if (a[m] < y)
			l = m;
		else r = m;
	}
	return r;
}

int binh(int x) {
	return  binl(x + 1) - 1;
}

void Merge(vector<int> &a, int l, int m, int r) {
	vector<int> c(r - l);

	int k = l, t = m;
	while (k < m || t < r) {
		if (k < m && (t == r || a[k] <= a[t])) {
			c[k + t - l - m] = a[k];
			k++;
		} else {
			c[k + t - l - m] = a[t];
			t++;
		}
	}

	for (int i = l; i < r; i++)
		a[i] = c[i - l];
}

void MergeSort(vector<int> &a, int l, int r) {
	if (r - l <= 1) return;
	int m = (l + r) / 2;
	MergeSort(a, l, m);

	MergeSort(a, m, r);

	Merge(a,l,m,r);
}

int main()
{
	cin >> n;
	a.resize(n, 0);
	for (int i = 0; i < n; i++)
		cin >> a[i];

	MergeSort(a, 0, a.size());

	int k, x, y;
	cin >> k;
	for (int i = 0; i < k; i++) {
		cin >> x;
		cin >> y;
		if (x <= a[0]) {
			if (y >= a[n - 1])
				cout << n << "\n";
			else
				cout << binh(y) + 1 << "\n";
		}
		else {
			if (y >= a[n - 1])
				cout << n - binl(x) << "\n";
			else
				cout << binh(y) - binl(x) + 1 << "\n";
		}
	}

	return 0;
}