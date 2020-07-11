#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <string>
#include <vector>
#include <stdexcept>

using namespace std;

vector<string> a;

string cnt() {
	if (a[a.size() - 1][0] == '-') {
		return to_string(stoi(a[a.size() - 3]) - stoi(a[a.size() - 2]));
	} else if (a[a.size() - 1][0] == '*') {
		return to_string(stoi(a[a.size() - 3]) * stoi(a[a.size() - 2]));;
	} else if (a[a.size() - 1][0] == '+') {
		return to_string(stoi(a[a.size() - 3]) + stoi(a[a.size() - 2]));;
	}
}

int main()
{
	ios_base::sync_with_stdio(false);
	cin.tie(0);
	
	string x, k;
	while (cin >> x) {
		a.push_back(x);
		if (a.size() > 2) {
			if ((a[a.size() - 1][0] == '-' && a[a.size() - 1].length() == 1) || a[a.size() - 1][0] == '+' || a[a.size() - 1][0] == '*')
				if ((a[a.size() - 2][0] >= '0' && a[a.size() - 2][0] <= '9') || a[a.size() - 2].length() > 1)
					if ((a[a.size() - 3][0] >= '0' && a[a.size() - 3][0] <= '9') || a[a.size() - 3].length() > 1) {
						a.push_back(cnt());
						a.erase(a.end() - 4, a.end() - 1);
					}
		}
	}

	cout << a[0];

	return 0;
}