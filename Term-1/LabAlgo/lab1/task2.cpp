#define _CRT_SECURE_NO_WARNINGS

#include <iostream>

using namespace std;

int main()
{
	int c;
	int num[101] = { 0 };

	while (cin >> c)
		num[c]++;

	for (int i = 0; i < 101; i++)
		if (num[i] > 0)
			for (int j = 0; j < num[i]; j++)
				cout << i << " ";

	return 0;
}