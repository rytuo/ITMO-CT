#include <iostream>
#include <vector>
#include <string>
#include <math.h>
 
using namespace std;
int n;
vector<vector<vector<int>>> sky;
 
int get_sum(int x, int y, int z) {
	int ans = 0;
	for (int i = x; i >= 0; i = (i&(i + 1)) - 1)
		for (int j = y; j >= 0; j = (j&(j + 1)) - 1)
			for (int k = z; k >= 0; k = (k&(k + 1)) - 1)
				ans += sky[i][j][k];
	return ans;
}
 
int check(int x1, int y1, int z1, int x2, int y2, int z2) {
	long long ans =  get_sum(x2, y2, z2)
					- get_sum(x1 - 1, y2, z2)
					- get_sum(x2, y1 - 1, z2)
					- get_sum(x2, y2, z1 - 1)
					+ get_sum(x1 - 1, y1 - 1, z2)
					+ get_sum(x1 - 1, y2, z1 - 1)
					+ get_sum(x2, y1 - 1, z1 - 1)
					- get_sum(x1 - 1, y1 - 1, z1 - 1);
	return ans;
}
 
void add(int x, int y, int z, int k) {
	for (int i = x; i < n; i = i | (i + 1))
		for (int j = y; j < n; j = j | (j + 1))
			for (int l = z; l < n; l = l | (l + 1))
				sky[i][j][l] += k;
	return;
}
 
int main() {
	//freopen("input.txt", "r", stdin);
	//freopen("output.txt", "w", stdout);
	ios::sync_with_stdio(false);
	cin.tie();
 
	cin >> n;
	sky.resize(n,
		vector<vector<int>>(n,
			vector<int>(n, 0)));
 
	int m;
	while (cin >> m) {
		switch (m) {
		case 1:
			int x, y, z, k;
			cin >> x >> y >> z >> k;
			add(x, y, z, k);
			break;
		case 2:
			int x1, y1, z1, x2, y2, z2;
			cin >> x1 >> y1 >> z1 >> x2 >> y2 >> z2;
			cout << check(x1, y1, z1,
							x2, y2, z2) << "\n";
			break;
		case 3:
			goto end;
		}
	}
	end:
 
	return 0;
}