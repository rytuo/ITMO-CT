#include <iostream>
#include <iomanip>

using namespace std;

int main()
{
	double c;
	cin >> c;
	double l = 0, r = 100000, m;
	
	for (int i = 0; i < 100; i++) {
		m = (l + r) / 2;
		if (m * m + sqrt(m) < c)
			l = m;
		else r = m;
	}

	cout << setprecision(6);
	cout << fixed;
	cout << (l + r) / 2;
	cin >> c;
	return 0;
}