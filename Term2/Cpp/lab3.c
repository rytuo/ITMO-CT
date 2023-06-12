#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define eps 1e-12

int solve(int n, double *sl[n], double val[n]) {
    double maxInRow[n];
    for (int i = 0; i < n; i++) {
        maxInRow[i] = fabs(sl[i][0]);
        for (int j = 1; j < n + 1; j++) {
            if (fabs(sl[i][j]) - maxInRow[i] > eps) {
                maxInRow[i] = fabs(sl[i][j]);
            }
        }
    }
    for(int i = 0; i < n; i++) {
        int num = i;
        double k = fabs(sl[i][i]) / maxInRow[i];
        for (int j = i + 1; j < n; j++) {
            if ((fabs(sl[j][i]) / maxInRow[j]) - k > eps) {
                num = j;
                k = fabs(sl[j][i]) / maxInRow[j];
            }
        }

        double *c = sl[num];
        sl[num] = sl[i];
        sl[i] = c;
        double p = maxInRow[num];
        maxInRow[num] = maxInRow[i];
        maxInRow[i] = p;

        if (fabs(sl[i][i]) > eps) {
            k = sl[i][i];
            for (int j = i; j < n + 1; j++) {
                sl[i][j] /= k;
            }
            for (int j = i + 1; j < n; j++) {
                k = sl[j][i];
                for (int l = i; l < n + 1; l++) {
                    sl[j][l] -= k * sl[i][l];
                }
                maxInRow[j] = fabs(sl[j][i + 1]);
                for (int l = i + 2; l < n + 1; l++) {
                    if (fabs(sl[j][l]) - maxInRow[j] > eps) {
                        maxInRow[j] = fabs(sl[j][l]);
                    }
                }
            }
        }
    }

    int single = 1;
    for (int i = n; i--;) {
        if (fabs(sl[i][i]) > eps) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += sl[i][j] * val[j];
            }
            val[i] = sl[i][n] - sum;
        } else {
            val[i] = 0;
            single = 0;
        }
    }

    if (single) {
        return 1;
    } else {
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += sl[i][j] * val[j];
            }
            if (fabs(sum - sl[i][n]) > eps) {
                return 0;
            }
        }

        return -1;
    }
}

int main(int argc, char **argv) {
    if (argc == 3) {
        FILE *fi = fopen(argv[1], "r");
        if (fi) {
            FILE *fo = fopen(argv[2], "w");
            if (fo) {
                int n;
                fscanf(fi, "%i", &n);

                double *sl[n];
                double val[n];
                for(int i = 0; i < n; i++) {
                    sl[i] = malloc((n + 1) * sizeof(double));
                    if (sl[i] ==  NULL) {
                        for (int j = 0; j < i; j++) {
                            free(sl[i]);
                        }
                        fclose(fi);
                        fclose(fo);
                        return 4;
                    }
                    for(int j = 0; j < n + 1; j++) {
                        fscanf(fi, "%lf", sl[i] + j);
                    }
                }

                switch(solve(n, sl, val)) {
                    case 1:
                        for (int i = 0; i < n; i++) {
                            fprintf(fo, "%lf\n", val[i]);
                        }
                        break;
                    case -1:
                        fprintf(fo, "%s", "many solutions");
                        break;
                    default:
                        fprintf(fo, "%s", "no solution");
                        break;
                }

                for (int i = 0; i < n; i++) {
                    free(sl[i]);
                }
                fclose(fi);
                fclose(fo);
                return 0;
            } else {
                printf("%s", "Can't open output file");
                fclose(fi);
                return 3;
            }
        } else {
            printf("%s", "Can't open input file");
            return 2;
        }
    } else {
        printf("%s", "Expected arguments: <input_file_name> <output_file_name>");
        return 1;
    }
}