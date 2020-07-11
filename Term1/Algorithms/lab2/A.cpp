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
	int n, c;
	vector<int> stek;
	vector<int> min;
	cin >> n;

	for (int i = 0; i < n; i++) {
		cin >> c;
		if (c == 1) {
			cin >> c;
			stek.push_back(c);
			if (min.size() > 0 && min[min.size()-1] < c) {
				min.push_back(min[min.size()-1]);
			}
			else min.push_back(c);
		}
		else if (c == 2) {
			stek.pop_back();
			min.pop_back();
		}
		else {
			cout << min[min.size() - 1] << "\n";
		}
	}

	cin >> n;
	return 0;
}