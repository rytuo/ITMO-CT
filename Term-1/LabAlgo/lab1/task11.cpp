#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <iomanip>

using namespace std;

double c[100000];

int split(int l, int r, double x) {
	int i = l, j = r;
	while (i <= j) {
		while (c[i] > x)
			i++;
		while (c[j] < x)
			j--;
		if (i >= j)
			break;
		swap(c[i], c[j]);
		i++;
		j--;
	}
	return j;
}

double kth(int l, int r, int k) {
	double x = c[(l + r) / 2];
	int m = split(l, r, x);
	if (m - l + 1 == k) {
		double min = 1000000;
		for (int i = l; i <= m; i++) {
			if (c[i] < min) min = c[i];
		}
		return min;
	} 
	else if (m + 1 >= k)
		return kth(l, m, k);
	else
		return kth(m + 1, r, k - m - 1);
}

int main()
{
	int n, k;
	double eps = 10e-5;
	cin >> n >> k;

	int a[100000][2] = { 0 };
	for (int i = 0; i < n; i++) {
		cin >> a[i][0] >> a[i][1];
		c[i] = ((double)a[i][0]) / ((double)a[i][1]);
	}
	
	double m = kth(0, n-1, k);
	double t;

	for (int i = 0; i < n && k > 0; i++) {
		t = (double)a[i][0] / (double)a[i][1];
		if (t > m || abs(t - m) < eps) {
			cout << i + 1 << "\n";
			k--;
		}
	}

	cin >> n;
	return 0;
}