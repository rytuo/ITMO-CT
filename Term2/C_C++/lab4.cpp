#include <iostream>
#include <string>
#include <cstdint>
#include <fstream>

typedef int32_t uint;

class BigNumber {
private:
    const int high_bound = 1000000000;
    const int unit_size = 9;
    char sign = 1;
    size_t size = 0;
    uint* val = nullptr;
    bool isNaN = false;
public:
    void del_zero() {
        size_t i = size;
        while (i > 1 && val[i - 1] == 0) { i--; }
        if (i != size) {
            size = i;
            uint* t = new uint[size];
            for (size_t i = 0; i < size; i++) {
                t[i] = val[i];
            }
            delete[] val;
            val = t;
        }
    }
    std::string to_string() {
        if (isNaN) {
            return "NaN";
        }
        std::string str;
        if (sign == -1) {
            str += "-";
        }
        str += std::to_string(val[size - 1]);
        str.reserve(unit_size * (size - 1));
        for (int i = size - 1; i--;) {
            std::string cur = std::to_string(val[i]);
            for (size_t j = 0; j < unit_size - cur.size(); j++) {
                str += '0';
            }
            str += cur;
        }
        return str;
    }

    // constructors
    BigNumber() {
        isNaN = true;
    }
    BigNumber(const BigNumber& a) {
        if (a.isNaN) {
            *this = BigNumber();
            return;
        }
        sign = a.sign;
        size = a.size;
        val = new uint[size];
        for (size_t i = 0; i < size; i++) {
            val[i] = a.val[i];
        }
    }
    explicit BigNumber(int a) {
        sign = a < 0 ? -1 : 1;
        val = new uint[1];
        val[0] = abs(a);
        size = 1;
    }
    explicit BigNumber(const std::string& a) {
        int l = 0;
        if (a[0] == '-') {
            sign = -1;
            l++;
        }
        size = (a.size() - l + unit_size - 1) / unit_size;
        val = new uint[size];
        int ind = 0;
        for (int i = a.size(); i > l; i -= unit_size) {
            if (i < unit_size + l) {
                val[ind] = stoi(a.substr(l, i - l));
            }
            else {
                val[ind] = stoi(a.substr(i - unit_size, unit_size));
            }
            ind++;
        }
        del_zero();
    }
    explicit BigNumber(char s, size_t len, uint* v) {
        sign = s;
        size = len;
        val = v;
        del_zero();
    }
    ~BigNumber() {
        if (!isNaN) {
            delete[](val);
        }
    }

    // compare functions
    int absCompare(const BigNumber& x) {
        if (isNaN || x.isNaN) {
            return (isNaN && x.isNaN);
        }
        if (size > x.size) {
            return 1;
        }
        if (size < x.size) {
            return -1;
        }
        for (int i = size; i--;) {
            if (val[i] > x.val[i]) {
                return 1;
            }
            if (val[i] < x.val[i]) {
                return -1;
            }
        }
        return 0;
    }
    int compare(const BigNumber& x) {
        if (isNaN || x.isNaN) {
            return (isNaN && x.isNaN);
        }
        if (sign + x.sign == 0) {
            return sign;
        }
        return sign * absCompare(x);
    }

    int operator>(const BigNumber& x) {
        return (compare(x) == 1 ? 1 : 0);
    }
    int operator<(const BigNumber& x) {
        return (compare(x) == -1 ? 1 : 0);
    }
    int operator>=(const BigNumber& x) {
        return (compare(x) != -1 ? 1 : 0);
    }
    int operator<=(const BigNumber& x) {
        return (compare(x) != 1 ? 1 : 0);
    }
    int operator==(const BigNumber& x) {
        return (compare(x) == 0 ? 1 : 0);
    }
    int operator!=(const BigNumber& x) {
        return (compare(x) != 0 ? 1 : 0);
    }

    // change current => no copy
    BigNumber& operator=(const BigNumber& x) {
        if (&x != this) {
            sign = x.sign;
            size = x.size;
            isNaN = x.isNaN;

            if (!isNaN) {
                delete[](val);
            }
            if (!x.isNaN) {
                val = new uint[size];
                for (size_t i = 0; i < size; i++) {
                    val[i] = x.val[i];
                }
            }
            else {
                val = nullptr;
            }
        }
        return *this;
    }

    BigNumber& add(const BigNumber& x) {
        if (isNaN || x.isNaN) {
            return (*this = BigNumber());
        }

        if (sign == x.sign) { // val add
            size_t n_size = (size > x.size ? size : x.size);
            uint* res = new uint[n_size];
            int next = 0;
            for (size_t i = 0; i < n_size; i++) {
                uint sum = (i < size ? val[i] : 0) + (i < x.size ? x.val[i] : 0) + next;
                next = sum / high_bound;
                res[i] = sum - next * high_bound;
            }
            delete[] val;
            size = n_size;
            if (next) {
                val = new uint[++size];
                for (size_t i = 0; i < size - 1; i++) {
                    val[i] = res[i];
                }
                val[size - 1] = next;
                delete[] res;
            }
            else {
                val = res;
            }
        }
        else {
            sign *= -1;
            sub(x);
            sign *= -1;
            if (size == 1 && val[0] == 0) {
                sign = 1;
            }
        }
        return *this;
    }
    BigNumber& sub(const BigNumber& x) {
        if (isNaN || x.isNaN) {
            return (*this = BigNumber());
        }

        if (sign == x.sign) { // val sub
            int mode = absCompare(x);
            if (mode == 0) {
                return (*this = BigNumber(0));
            }

            size_t n_size = (size > x.size ? size : x.size);
            uint* res = new uint[n_size];
            int next = 0;
            for (size_t i = 0; i < n_size; i++) {
                uint sum = high_bound - next +
                    mode * ((i < size ? val[i] : 0) -
                        (i < x.size ? x.val[i] : 0));
                next = sum / high_bound;
                res[i] = sum - next * high_bound;
                next = 1 - next;
            }
            delete[] val;
            val = res;
            size = n_size;
            sign = mode * sign;
            del_zero();
        }
        else {
            sign *= -1;
            add(x);
            sign *= -1;
        }
        return *this;
    }
    BigNumber& mul(const BigNumber& x) {
        if (isNaN || x.isNaN) {
            return (*this = BigNumber());
        }

        size_t n_size = size + x.size;
        uint* res = new uint[n_size];
        for (size_t i = 0; i < n_size; i++) { res[i] = 0; }
        for (size_t i = 0; i < size; i++) {
            for (size_t j = 0; j < x.size; j++) {
                long long p = (long long)val[i] * (long long)x.val[j];
                res[i + j] += p % high_bound;
                res[i + j + 1] += (uint)(p / high_bound + res[i + j] / high_bound);
                res[i + j] %= high_bound;
            }
        }
        sign *= x.sign;
        size = n_size;
        delete[] val;
        val = res;
        del_zero();
        return *this;
    }
    BigNumber& div(const BigNumber& x) {
        if (isNaN || x.isNaN || BigNumber(0) == x) {
            return (*this = BigNumber());
        }
        if (absCompare(x) == -1) {
            return (*this = BigNumber(0));
        }

        BigNumber xs = x;
        xs.sign = 1;

        std::string str1 = to_string(), str2 = xs.to_string();
        if (str1[0] == '-') {
            str1 = str1.substr(1);
        }


        BigNumber temp(str1.substr(0, str2.size()));
        int i = str2.size() - 1;
        if (temp < xs) {
            temp = (temp * BigNumber(10)) + BigNumber(str1[++i] - '0');
        }


        size_t n_size = (str1.size() - i + unit_size - 1) / unit_size;
        uint* res = new uint[n_size];
        BigNumber l, m, r;
        for (int t = n_size; t--;) {
            res[t] = 0;
            for (; i < str1.size() - t * unit_size; i++) {
                res[t] *= 10;
                l = BigNumber(0);
                r = BigNumber(10);
                while (l + BigNumber(1) < r) {
                    m = BigNumber((l.val[0] + r.val[0]) / 2);
                    if ((m * xs) > temp) {
                        r = m;
                    }
                    else {
                        l = m;
                    }
                }
                res[t] += l.val[0];
                temp -= (l * xs);
                if (i < str1.size()) {
                    temp = (temp * BigNumber(10)) + BigNumber(str1[i + 1] - '0');
                }
            }
        }

        delete[] val;
        val = res;
        size = n_size;
        sign *= x.sign;
        del_zero();
        return *this;
    }
    BigNumber& mod(const BigNumber& x) {
        return sub((*this / x).mul(x));
    }

    BigNumber& operator+=(const BigNumber& x) {
        return add(x);
    }
    BigNumber& operator-=(const BigNumber& x) {
        return sub(x);
    }
    BigNumber& operator*=(const BigNumber& x) {
        return mul(x);
    }
    BigNumber& operator/=(const BigNumber& x) {
        return div(x);
    }
    BigNumber& operator%=(const BigNumber& x) {
        return mod(x);
    }

    // make a new object with result
    BigNumber operator+(const BigNumber& x) {
        BigNumber res = *this;
        return res.add(x);
    }
    BigNumber operator-(const BigNumber& x) {
        BigNumber res = *this;
        return res.sub(x);
    }
    BigNumber operator*(const BigNumber& x) {
        BigNumber res = *this;
        return res.mul(x);
    }
    BigNumber operator/(const BigNumber& x) {
        BigNumber res = *this;
        return res.div(x);
    }
    BigNumber operator%(const BigNumber& x) {
        BigNumber res = *this;
        return res.mod(x);
    }

    BigNumber sqrt() {
        if (isNaN) {
            return *this;
        }
        BigNumber TWO(2), t;
        BigNumber l = *this, r = BigNumber(1).add(*this).div(TWO);
        while (r < l) {
            l = r;
            t = *this;
            t /= r;
            r.add(t).div(TWO);
        }
        return l;
    }
};

std::ofstream& operator<<(std::ofstream& ofs, BigNumber x) {
    ofs << x.to_string();
    return ofs;
}

int main(int argc, char** argv) {
    switch (argc) {
    case 3:
    {
        std::ifstream ifs(argv[1]);
        if (ifs) {
            std::ofstream ofs(argv[2]);
            if (ofs) {
                std::string l, sign;
                ifs >> l >> sign;
                BigNumber f(l);
                if (sign == "#") {
                    ofs << f.sqrt();
                }
                else {
                    std::string r;
                    ifs >> r;
                    BigNumber s(r);
                    if (sign.size() == 1) {
                        switch (sign[0]) {
                        case '+':
                            ofs << f + s;
                            break;
                        case '-':
                            ofs << f - s;
                            break;
                        case '*':
                            ofs << f * s;
                            break;
                        case '/':
                            ofs << f / s;
                            break;
                        case '%':
                            ofs << f % s;
                            break;
                        case '<':
                            ofs << (f < s);
                            break;
                        case '>':
                            ofs << (f > s);
                            break;
                        }
                    }
                    else {
                        switch (sign[0]) {
                        case '<':
                            ofs << (f <= s);
                            break;
                        case '>':
                            ofs << (f >= s);
                            break;
                        case '=':
                            ofs << (f == s);
                            break;
                        case '!':
                            ofs << (f != s);
                            break;
                        }
                    }
                }
                ifs.close();
                ofs.close();
                return 0;
            }
            else {
                printf("%s", "Can't open input file");
                ifs.close();
                return 3;
            }
        }
        else {
            printf("%s", "Can't open input file");
            return 2;
        }
    }
    default:
        printf("%s", "Expected arguments: <input_file> <output_file>");
        return 1;
    }
}