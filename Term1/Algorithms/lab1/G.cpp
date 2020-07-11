#include <iostream>

using namespace std;

int main()
{
	int n, x, y;
	cin >> n >> x >> y;
	
	int t = 0;
	if (x <= y) t += x;
	else t += y;
	
	int a = x * (n - 1) - x * ((x*(n-1)) / (x + y));
	int b = y * (x*(n-1) / (x + y));
	int c = a - x;
	int d = b + y;

	if (abs(c - d) < abs(a - b)) {
		a = c;
		b = d;
	}
	
	if (a >= b) t += a;
	else t += b;
	cout << t;

	return 0;
}