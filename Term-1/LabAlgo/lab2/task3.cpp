#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <string>
#include <vector>

using namespace std;

int main()
{
	ios_base::sync_with_stdio(false);
	cin.tie(0);
	
	int n, c;
	vector<int> a;
	cin >> n;
	for (int i = 0; i < n; i++) {
		cin >> c;
		if (c == 1) {
			cin >> c;
			a.push_back(c);
		} else if (c == 2) {
			a.erase(a.begin());
		}
		else if(c == 3) {
			a.pop_back();
		}
		else if (c == 4) {
			cin >> c;
			for (int j = 0; j < a.size(); j++)
				if (a[j] == c) {
					cout << j << "\n";
					break;
				}
		}
		else {
			cout << a[0] << "\n";
		}
	}

	return 0;
}