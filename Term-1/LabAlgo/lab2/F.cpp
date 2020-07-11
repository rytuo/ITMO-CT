#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <string>
#include <vector>
#include <stdexcept>

using namespace std;

int main()
{
	ios_base::sync_with_stdio(false);
	cin.tie(0);

	int n, x, t = 0;
	cin >> n;
	vector<int> a;
	vector<int> b(n);
	vector<int> bsort;
	vector<string> ans;

	for (int i = 0; i < n; i++)
		cin >> b[i];

	for (int i = 0; i < n; i++) {
		while (a.size() > 0 && a[a.size() - 1] < b[i]) {
			bsort.push_back(a[a.size() - 1]);
			a.pop_back();
			ans.push_back("pop");
		}
		a.push_back(b[i]);
		ans.push_back("push");
	}

	for (int i = a.size() - 1; i >= 0; i--) {
		bsort.push_back(a[i]);
		ans.push_back("pop");
	}
	
	for (int i = 0; i < bsort.size() - 1; i++) {
		if (bsort[i] > bsort[i + 1]) t = 1;
	}

	if (t == 1) cout << "impossible";
	else for (string i : ans) cout << i << endl;

	cin >> n;
	return 0;
}