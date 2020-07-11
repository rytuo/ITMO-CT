#define _CRT_SECURE_NO_WARNINGS

#include <iostream>

using namespace std;

int a[100000] = { 0 };
long l = 0;

void insert(int x) {
	a[++l] = x;
	int k = l;
	while (k > 0 && a[k] > a[(k - 1) / 2]) {
		swap(a[k], a[(k - 1) / 2]);
		k = (k - 1) / 2;
	}
}

void extract() {
	cout << a[0] << "\n";
	swap(a[0], a[l]);
	a[l--] = 0;
	int k = 0;
	while (2 * k + 1 <= l) {
		if ((2*k+2 <= l) && (a[2*k+2] > a[2*k+1]) && (a[2*k+2] > a[k])) {
			swap(a[k], a[2 * k + 2]);
			k = 2 * k + 2;
		}
		else {
			if (a[2 * k + 1] > a[k]) {
				swap(a[k], a[2 * k + 1]);
				k = 2 * k + 1;
			}
			else break;
		}
	}
}

int main()
{
	int n, c;
	cin >> n;
	for (int i = 0; i < n; i++) {
		cin >> c;
		if (c == 0) {
			cin >> c;
			insert(c);
		}
		else {
			extract();
		}
	}
	cin >> n;
	return 0;
}