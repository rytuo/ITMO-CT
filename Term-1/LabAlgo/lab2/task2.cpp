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
	
	int n;
	cin >> n;
	vector<int> a(n);

	for (int i = 0; i < n; i++)
		cin >> a[i];

	int i = 1, s = 0, k = 0;

	while (i < a.size()) {
		if (a[i] == a[i - 1]) k++;
		else if (k > 1) {
			a.erase(a.begin() + i - k - 1, a.begin() + i);
			s += k + 1;
			k = 0;
			i = 0;
		}
		else {
			k = 0;
		}
		i++;
	}

	if (k > 1) {
		s += k + 1;
	}

	cout << s;
	return 0;
}