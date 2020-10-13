#include <iostream>
#include <vector>
#include <string>
#include <math.h>
 
using namespace std;
 
typedef struct node {
	int num = 0;
	int len = 0;
	int R = 0;
	int L = 0;
};
 
int k = 1;
vector<node> line;
vector<int> change;
 
void prop(int ind, int l, int r) {
	if (r - l <= 0)
		return;
	if (change[ind] == -1)
		return;
	if (change[ind] == 1) {
		change[2 * ind + 1] = 1;
		change[2 * ind + 2] = 1;
		line[2 * ind + 1].num = 1;
		line[2 * ind + 1].len = (r - l + 1) / 2;
		line[2 * ind + 1].R = line[2 * ind + 1].len;
		line[2 * ind + 1].L = line[2 * ind + 1].len;
		line[2 * ind + 2].num = 1;
		line[2 * ind + 2].len = (r - l + 1) / 2;
		line[2 * ind + 2].R = line[2 * ind + 2].len;
		line[2 * ind + 2].L = line[2 * ind + 2].len;
	}
	else if (change[ind] == 0) {
		change[2 * ind + 1] = 0;
		change[2 * ind + 2] = 0;
		line[2 * ind + 1].num = 0;
		line[2 * ind + 1].len = 0;
		line[2 * ind + 1].R = 0;
		line[2 * ind + 1].L = 0;
		line[2 * ind + 2].num = 0;
		line[2 * ind + 2].len = 0;
		line[2 * ind + 2].R = 0;
		line[2 * ind + 2].L = 0;
	}
	change[ind] = -1;
}
 
void upd(int l, int r, int val, int ind, int lx, int rx) {
	prop(ind, l, r);
	if (l > rx || r < lx) {
		return;
	}
	if (lx <= l && r <= rx) {
		if (val == 1) {
			change[ind] = 1;
			line[ind].num = 1;
			line[ind].len = r - l + 1;
			line[ind].R = line[ind].len;
			line[ind].L = line[ind].len;
		}
		else {
			change[ind] = 0;
			line[ind].num = 0;
			line[ind].len = 0;
			line[ind].R = line[ind].len;
			line[ind].L = line[ind].len;
		}
		return;
	}
	int m = (l + r + 1) / 2 - 1;
	upd(l, m, val, 2 * ind + 1, lx, rx);
	upd(m+1, r, val, 2 * ind + 2, lx, rx);
 
	line[ind].num = line[2 * ind + 1].num + line[2 * ind + 2].num;
	if (line[2*ind+1].R != 0 && line[2 * ind + 2].L != 0) {
		line[ind].num -= 1;
	}
 
	line[ind].len = line[2 * ind + 1].len + line[2 * ind + 2].len;
 
	if (line[2 * ind + 1].L == (r - l + 1) / 2) {
		line[ind].L = line[2 * ind + 1].L + line[2 * ind + 2].L;
	}
	else {
		line[ind].L = line[2 * ind + 1].L;
	}
 
	if (line[2 * ind + 2].R == (r - l + 1) / 2) {
		line[ind].R = line[2 * ind + 2].R + line[2 * ind + 1].R;
	}
	else {
		line[ind].R = line[2 * ind + 2].R;
	}
}
 
int main() {
	//freopen("input.txt", "r", stdin);
	//freopen("output.txt", "w", stdout);
	ios::sync_with_stdio(false);
	cin.tie();
 
	int n;
	cin >> n;
	k = pow(2, 20);
	line.resize(2 * k - 1);
	change.resize(2 * k - 1, -1);
 
	char s;
	int a, b;
	for (int i = 0; i < n; i++) {
		cin >> s >> a >> b;
		a += 500000;
		if (s == 'W') {
			upd(0, k - 1, 0, 0, a, a + b - 1);
		}
		else {
			upd(0, k - 1, 1, 0, a, a + b - 1);
		}
		printf("%i %i\n", line[0].num, line[0].len);
	}
 
	return 0;
}