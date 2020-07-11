#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <string>
#include <vector>

using namespace std;

long long p = 0;

void Merge(vector<int> &a, int l, int m, int r) {
	vector<int> c(r - l);

	int k = l, t = m;
	while (k < m || t < r) {
		if (k < m && (t == r || a[k] <= a[t])) {
			c[k + t - l - m] = a[k];
			k++;
		} else {
			c[k + t - l - m] = a[t];
			p += m-k;
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
	int n;
	cin >> n;
	vector<int> a(n);
	for (int i = 0; i < n; i++)
		cin >> a[i];

	MergeSort(a, 0, a.size());

	cout << p;
	return 0;
}