#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <vector>
#include <string>

using namespace std;

int main()
{
	string s1, s2;
	cin >> s1 >> s2;
	vector<vector<int>> a(s1.size() + 1, vector<int>(s2.size() + 1));
	a[0][0] = 0;

	string t1, t2;
	int temp;
	for (int i = 0; i <= s1.size(); i++) {
		for (int j = 0; j <= s2.size(); j++) {
			temp = 1000000;
			if (i == 0)
				t1 = "";
			else
				t1 = s1[i - 1];
			if (j == 0)
				t2 = "";
			else
				t2 = s2[j - 1];
			if (i > 0 && j > 0) {
				if (t1 == t2 && temp > a[i-1][j-1]) {
					temp = a[i - 1][j - 1];
				}
				if (temp > a[i - 1][j - 1] + 1)
					temp = a[i - 1][j - 1] + 1;
			}
			if (i > 0 && temp > a[i - 1][j] + 1)
				temp = a[i - 1][j] + 1;
			if (j > 0 && temp > a[i][j - 1] + 1)
				temp = a[i][j - 1] + 1;
			if (i > 0 || j > 0)
				a[i][j] = temp;
		}
	}

	cout << a[s1.size()][s2.size()];

	return 0;
}