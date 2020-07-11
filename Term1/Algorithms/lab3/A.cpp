#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <vector>
#include <string>


using namespace std;

int main()
{
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);

	int n, k;
	cin >> n >> k;
	vector<int> a(n, 0);
	vector<int> b(n, 0);
	vector<int> ans;

	for (int i = 1; i < n; i++) {
		cin >> a[i];
	}

	for (int i = 1; i < n; i++) {
		int max = b[i - 1];
		for (int j = 2; j <= k && i - j >= 0; j++) {
			if (b[i - j] > max)
				max = b[i - j];
		}
		b[i] = a[i] + max;
	}

	int hops = 0;
	ans.push_back(n);
	for (int i = n - 1; i > 0;) {
		int max = b[i - 1];
		int index = i - 1;
		for (int j = 2; j <= k && i - j >= 0; j++) {
			if (b[i - j] > max) {
				max = b[i - j];
				index = i - j;
			}
		}
		ans.push_back(index + 1);
		hops++;
		i = index;
	}

	cout << b[n - 1] << endl;
	cout << hops << endl;
	for (int i = ans.size() - 1; i >= 0; i--) {
		cout << ans[i] << " ";
	}
	return 0;
}