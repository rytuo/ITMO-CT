#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <vector>
#include <string>

using namespace std;

int main()
{
	//freopen("input.txt", "r", stdin);
	//freopen("output.txt", "w", stdout);

	int n, max = 1, index = 0;
	cin >> n;
	vector<int> a(n);
	vector<int> b(n, 1);
	vector<int> ans;

	for (int i = 0; i < n; i++)
		cin >> a[i];

	for (int i = 1; i < n; i++) {
		for (int j = 0; j < i; j++) {
			if (a[j] < a[i] && b[j] >= b[i]) {
				b[i] = b[j] + 1;
				if (b[i] > max) {
					max = b[i];
					index = i;
				}
			}
		}
	}

	ans.push_back(a[index]);
	for (int i = max - 1; i > 0; i--) {
		for (int j = 0; j < index; j++) {
			if (a[j] < a[index] && b[j] == i) {
				index = j;
				ans.push_back(a[j]);
				break;
			}
		}
	}

	cout << ans.size() << endl;
	for (int i = ans.size() - 1; i >= 0; i--) {
		cout << ans[i] << " ";
	}

	return 0;
}