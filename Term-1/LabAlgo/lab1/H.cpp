#define _CRT_SECURE_NO_WARNINGS

#include <iostream>

using namespace std;

int main()
{
	int n, w, h;
	cin >> w >> h >> n;
	unsigned long long l = 1, r = 10e17, m;

	while (l + 1 < r) {
		m = (l + r) / 2;
		if ((m/h)*(m/w) < n)
			l = m;
		else r = m;
	}


	if (w*h*n == 1) cout << "1";
	else cout << r;
	return 0;
}