#include <iostream>
#include <iomanip>

using namespace std;

double v1, v2, a;
double eps = 10e-5;

double f(double x) {
	double k = ((sqrt((1 - a)*(1 - a) + x * x) / v1) + (sqrt(a*a + (1 - x)*(1 - x)) / v2));
	return k;
}

int main()
{
	cin >> v1 >> v2 >> a;

	double l = 0, r = 1, m, m1, m2;

	for (int i = 0; i < 50; i++) {
		m1 = (2 * l + r) / 3;
		m2 = (l + 2 * r) / 3;
		if (f(m1) < f(m2))
			r = m2;
		else
			l = m1;
	}

	m = (l + r) / 2;
	if (abs(1 - m) < eps)
		cout << "1.0000";
	else if (m < eps)
		cout << "0.0000";
	else {
		cout << setprecision(5);
		cout << fixed;
		cout << m;
	}

	return 0;
}