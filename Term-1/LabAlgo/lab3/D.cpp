#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <vector>
#include <string>

using namespace std;

int main()
{
	//freopen("input.txt", "r", stdin);
	//freopen("output.txt", "w", stdout);

	long long int n, s = 0;
	cin >> n;
	vector<long long int> nums(10, 1);
	vector<long long int> prev(10, 1);
	
	for (int i = n - 1; i > 0; i--) {
		nums[0] = (prev[4] + prev[6]) % 1000000000;
		nums[1] = (prev[8] + prev[6]) % 1000000000;
		nums[2] = (prev[7] + prev[9]) % 1000000000;
		nums[3] = nums[1];
		nums[4] = (prev[3] + prev[9] + prev[0]) % 1000000000;
		nums[6] = nums[4];
		nums[7] = (prev[2] + prev[6]) % 1000000000;
		nums[8] = (prev[1] + prev[3]) % 1000000000;
		nums[9] = nums[7];
		nums[0] = (prev[4] + prev[6]) % 1000000000;
		for (int j = 0; j < 10; j++)
			prev[j] = nums[j];
	}
	if (n == 1)
		s += 1;

	for (int i = 0; i < 10; i++) {
		if (i != 0 && i != 8 && i != 5) {
			s = (s + prev[i]) % 1000000000;
		}
	}
	
	cout << s;

	return 0;
}