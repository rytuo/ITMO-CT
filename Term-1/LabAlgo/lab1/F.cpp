#include <iostream>

using namespace std;

int main()
{
	int n, k;
	int a[100000];
	cin >> n >> k;

	for (int i = 0; i < n; i++)
		cin >> a[i];

	int l, r, m, t;
	for (int i = 0; i < k; i++) {
		cin >> t;
		if (t <= a[0]) {
			cout << a[0] << "\n";
		}
		else if (t >= a[n - 1])
		{
			cout << a[n - 1] << "\n";
		}
		else {
			l = 0;
			r = n;
			while (r - l > 1) {
				m = (l + r) / 2;
				if (a[m] < t)
					l = m;
				else r = m;
			}
			if ((t - a[r-1]) <= (a[r] - t))
				cout << a[r - 1] << "\n";
			else
				cout << a[r] << "\n";
		}
	}

	return 0;
}