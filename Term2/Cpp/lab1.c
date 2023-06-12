#include <stdio.h>

typedef unsigned char uchar;
typedef unsigned int uint;

// y >= 0 Lshift, y < 0 Rshift
uint shift(uint x, int y) {
    if (y < 0) {
        return (x >> -y);
    } else {
        return (x << y);
    }
}

// fully refresh buff, delete remaining chars, returns number of read symbols
int refresh_buff(FILE *file, size_t *num_read, uchar *buff, size_t *current) {
    *num_read = fread(buff, sizeof(char), 100, file);
    *current = 0;
    return *num_read;
}

// if there is a next symbol in buff, refreshes buff if needed
int has_next(FILE *file, size_t *num_read, uchar *buff, size_t *current) {
    if (*current + 1 <= *num_read) {
        return 1;
    } else {
        return refresh_buff(file, num_read, buff, current);
    }
}

// get next symbol from buff
uchar next(FILE *file, size_t *num_read, uchar *buff, size_t *current) {
    return buff[(*current)++];
}

// read BOM from file
int file_input_mode(size_t num_read, uchar *bom, size_t *current) {
    if (num_read >= 4) {
        if (bom[0] == 0xFF && bom[1] == 0xFE &&
            bom[2] == 0x00 && bom[3] == 0x00) {
            (*current) += 4;
            return 4; //32LE
        }
        if (bom[0] == 0x00 && bom[1] == 0x00 &&
            bom[2] == 0xFE && bom[3] == 0xFF) {
            (*current) += 4;
            return 5; //32BE
        }
    }
    if (num_read >= 3) {
        if (bom[0] == 0xEF && bom[1] == 0xBB && bom[2] == 0xBF) {
            (*current) += 3;
            return 1; // utf8 with bom
        }
    }
    if (num_read >= 2) {
        if (bom[0] == 0xFF && bom[1] == 0xFE) {
            (*current) += 2;
            return 2; //16LE
        }
        if (bom[0] == 0xFE && bom[1] == 0xFF) {
            (*current) += 2;
            return 3; //16BE
        }
    }
    return 0; //8 without BOM
}

// print BOM to file
void print_bom(FILE *file, int mode) {
    uchar res[4];
    switch (mode) {
        case 1: // utf8 BOM
            res[0] = 0xEF;
            res[1] = 0xBB;
            res[2] = 0xBF;
            fwrite(res, 3 * sizeof(char), 1, file);
            break;
        case 2: // utf16 LE
            res[0] = 0xFF;
            res[1] = 0xFE;
            fwrite(res, 2 * sizeof(char), 1, file);
            break;
        case 3: // utf16 BE
            res[0] = 0xFE;
            res[1] = 0xFF;
            fwrite(res, 2 * sizeof(char), 1, file);
            break;
        case 4: // utf32 LE
            res[0] = 0xFF;
            res[1] = 0xFE;
            res[2] = 0x00;
            res[3] = 0x00;
            fwrite(res, 4 * sizeof(char), 1, file);
            break;
        case 5: // utf32 BE
            res[0] = 0x00;
            res[1] = 0x00;
            res[2] = 0xFE;
            res[3] = 0xFF;
            fwrite(res, 4 * sizeof(char), 1, file);
            break;
        default: // utf8 no BOM
            break;
    }
    return;
}

// convert char* to int
int get_mode(char *mode) {
    if (mode[1] != '\0') {
        return 7;
    }
    return mode[0] - 48;
}

// return number of printed bytes || 0 if error = no printed bytes
int print_symbol(FILE *file, int mode, uint sym) {
    uchar res[6] = {0, 0, 0, 0, 0, 0};
    int r = 0;
    switch (mode) {
        case 2: // utf16 LE
            {
                if (sym < 0x10000) {
                    res[0] = sym & 255;
                    res[1] = (sym >> 8) & 255;
                    r = fwrite(res, sizeof(char), 2, file);
                } else {
                    sym -= 0x10000;
                    res[1] = 0xD8 | ((sym >> 18) & 3);
                    res[0] = (sym >> 10) & 255;
                    res[3] = 0xDC | ((sym >> 8) & 3);
                    res[2] = sym & 255;
                    r = fwrite(res, sizeof(char), 4, file);
                }
            }
            break;
        case 3: // utf16 BE
            {
                if (sym < 0x10000) {
                    res[1] = sym & 255;
                    res[0] = (sym >> 8) & 255;
                    r = fwrite(res, sizeof(char), 2, file);
                } else {
                    sym -= 0x10000;
                    res[0] = 0xD8 | ((sym >> 18) & 3);
                    res[1] = (sym >> 10) & 255;
                    res[2] = 0xDC | ((sym >> 8) & 3);
                    res[3] = sym & 255;
                    r = fwrite(res, sizeof(char), 4, file);
                }
            }
            break;
        case 4: // utf32 LE
            {
                // reverse bytes
                for (int i = 0; i < 4; i++) {
                    res[i] = (sym >> 8 * i) & 255;
                }
                r = fwrite(res, sizeof(char), 4, file);
            }
            break;
        case 5: // utf32 BE
            {
                for (int i = 0; i < 4; i++) {
                    res[3 - i] = (sym >> 8 * i) & 255;
                }
                r = fwrite(res, sizeof(char), 4, file);
            }
            break;
        default: // utf8
            {
                if (sym >= 0xDC80 && sym <= 0xDCFF) { // broken bytes decode as before
                    res[0] = sym - 0xDC00;
                    r = fwrite(res, sizeof(char), 1, file);
                } else {
                    // check how many bits in symbol and encode it
                    int i;
                    for (i = 32; i--;)
                        if ((sym >> i) % 2 == 1) {
                            break;
                        }
                    if (i < 7) {
                        res[0] = sym;
                        r = fwrite(res, sizeof(char), 1, file);
                    } else {
                        i = (i + 4) / 5 - 1; // num of add bits
                        res[0] = 0;
                        for (uint j = 0; j <= i; j++) {
                            res[0] += (1 << (7 - j));
                        }
                        res[0] += (sym >> 6*i) % (1 << (6 - i));

                        for (int j = 1; j <= i; j++) {
                            res[j] = 128 + ((sym >> 6*(i - j)) & 63);
                        }
                        r = fwrite(res, sizeof(char), i + 1, file);
                    }
                }
            }
            break;
    }
    return r;
}

// return number of printed bytes || 0 if error = no printed bytes
int next_symbol(FILE *fi, FILE *fo, int mode_in, int mode_out, uchar *buff, size_t *num_read, size_t *current) {
    uint sym[6] = {0, 0, 0, 0, 0, 0};
    int r = 0;
    switch (mode_in) {
        case 2: // utf16 LE
            {
                sym[1] = next(fi, num_read, buff, current);
                if (has_next(fi, num_read, buff, current) == 0) {
                    sym[0] = 0xff;
                    sym[1] = 0xfd;
                } else
                    sym[0] = next(fi, num_read, buff, current);
                if (has_next(fi, num_read, buff, current) == 0 || ((sym[0] & 0xfc) != 0xD8)) {
                    r = print_symbol(fo, mode_out, shift(sym[0], 8) + sym[1]);
                } else {
                    sym[3] = next(fi, num_read, buff, current);
                    if (has_next(fi, num_read, buff, current) == 0) {
                        sym[2] = 0xff;
                        sym[3] = 0xfd;
                    } else
                        sym[2] = next(fi, num_read, buff, current);
                    if ((sym[2] & 0xFC) == 0xDC) { // surrogate pair
                        r = print_symbol(fo, mode_out, shift(sym[0] & 3, 18) + shift(sym[1], 10) +
                                                       shift(sym[2] & 3, 8) + sym[3] + 0x10000);
                    } else { // error -> print as different bytes
                        r = print_symbol(fo, mode_out, shift(sym[0], 8) + sym[1]) +
                                print_symbol(fo, mode_out, shift(sym[2], 8) + sym[3]);
                    }
                }
            }
            break;
        case 3: // utf16 BE
            {
                sym[0] = next(fi, num_read, buff, current);
                if (has_next(fi, num_read, buff, current) == 0) {
                    sym[0] = 0xff;
                    sym[1] = 0xfd;
                } else
                    sym[1] = next(fi, num_read, buff, current);
                if (has_next(fi, num_read, buff, current) == 0 || ((sym[0] & 0xfc) != 0xD8)) {
                    r = print_symbol(fo, mode_out, shift(sym[0], 8) + sym[1]);
                } else {
                    sym[2] = next(fi, num_read, buff, current);
                    if (has_next(fi, num_read, buff, current) == 0) {
                        sym[2] = 0xff;
                        sym[3] = 0xfd;
                    } else
                        sym[3] = next(fi, num_read, buff, current);

                    if ((sym[2] & 0xFC) == 0xDC) { // surrogate pair
                        r = print_symbol(fo, mode_out, shift(sym[0] & 3, 18) +
                        shift(sym[1], 10) + shift(sym[2] & 3, 8) + sym[3] + 0x10000);
                    } else { // error -> print as different bytes
                        r = print_symbol(fo, mode_out, shift(sym[0], 8) + sym[1]) +
                                print_symbol(fo, mode_out, shift(sym[2], 8) + sym[3]);
                    }
                }
            }
            break;
        case 4: // utf32 LE
            {
                for (int i = 0; i < 4; i++) {
                    if (has_next(fi, num_read, buff, current) == 0) {
                        sym[0] = 0x00;
                        sym[1] = 0x00;
                        sym[2] = 0xff;
                        sym[3] = 0xfd;
                    } else
                        sym[3 - i] = next(fi, num_read, buff, current);
                }
                r = print_symbol(fo, mode_out, shift(sym[0], 24) + shift(sym[1], 16) +
                                 shift(sym[2], 8)  + sym[3]);
            }
            break;
        case 5: // utf32 BE
            {
                for (int i = 0; i < 4; i++) {
                    if (has_next(fi, num_read, buff, current) == 0) {
                        sym[0] = 0x00;
                        sym[1] = 0x00;
                        sym[2] = 0xff;
                        sym[3] = 0xfd;
                    } else
                        sym[i] = next(fi, num_read, buff, current);
                }
                r = print_symbol(fo, mode_out, shift(sym[0], 24) + shift(sym[1], 16) +
                                 shift(sym[2], 8)  + sym[3]);
            }
            break;
        default: // utf8
            {
                int i, error, go_next = 1;
                sym[0] = next(fi, num_read, buff, current);
                error:
                    error = 0; // check for error

                    for (i = 8; i--;) {
                        if (((sym[0] >> i) & 1) == 0)
                            break;
                    }
                    i = 7 - i; // number of 1 before 0 in sym[0]
                    if (i == 0) {
                        r += print_symbol(fo, mode_out, sym[0]);
                    } else if (i == 1 || i > 6) { // error -> 0xDC80 .. 0xDCFF
                        r += print_symbol(fo, mode_out, 0xDC + sym[0]);
                    } else {
                        for (int j = 1; j < i; j++) {
                            if (has_next(fi, num_read, buff, current) == 0) {
                                error = j;
                                go_next = 0;
                                break;
                            } else {
                                sym[j] = next(fi, num_read, buff, current);
                                if (((sym[j] >> 7) & 1) == 0 || ((sym[j] >> 6) & 1) == 1) {
                                    error = j;
                                    break;
                                }
                            }
                        }

                        if (error) { // print broken bytes as symbols 0xDC80..0xDCFF and start new byte
                            for (int j = 0; j < error; j++) {
                                r += print_symbol(fo, mode_out, 0xDC00 + sym[j]);
                            }
                            if (go_next) {
                                sym[0] = sym[error];
                                goto error;
                            }
                        } else { // decode right bytes
                            for (int j = 0; j < i; j++)
                                sym[0] -= (1 << (7 - j));
                            uint res = (sym[0] << 6*(i - 1));

                            for (int j = 1; j < i; j++) {
                                res += ((sym[j] - 128) << 6*(i - 1 - j));
                            }

                            r += print_symbol(fo, mode_out, res);
                        }
                    }
            }
            break;
    }
    return r;
}

int main(int argc, char **argv) {
    switch (argc) {
        case 1:
            printf("%s", "Input file missing");
            return 1;
        case 2:
            printf("%s", "Output file missing");
            return 2;
        case 3:
            printf("%s", "Output file format missing");
            return 3;
        case 4:
            {
                FILE *fi = fopen(argv[1], "rb");
                if (fi) {
                    FILE *fo = fopen(argv[2], "wb");
                    if (fo) {
                        uchar buff[100];
                        size_t current = 0, num_read = 0;
                        refresh_buff(fi, &num_read, buff, &current);
                        int mode1 = file_input_mode(num_read, buff, &current);
                        int mode2 = get_mode(argv[3]);
                        if (mode2 < 0 || 5 < mode2) {
                            printf("%s", "Wrong mode, expected int: 0-5");
                            return 3;
                        }

                        print_bom(fo, mode2);
                        while(has_next(fi, &num_read, buff, &current)) {
                            if (next_symbol(fi, fo, mode1, mode2, buff, &num_read, &current) == 0) {
                                printf("%s", "Can't print symbol");
                                return 5;
                            }
                        }

                        fclose(fo);
                    } else {
                        printf("%s", "Can't open output file");
                        return 2;
                    }
                    fclose(fi);
                    printf("%s", "\tDone!");
                    return 0;
                } else {
                    printf("%s", "Can't open input file");
                    return 1;
                }
            }
        default:
            printf("%s", "Too much arguments, please enter: <input_file> <output_file> <output_file_mode>");
            return 5;
    }
}