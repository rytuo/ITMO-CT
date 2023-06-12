#include <stdio.h>
#include <stdlib.h>

// all char* end with '\0'
typedef struct {
    char name[21];
    char middle_name[21];
    char surname[21];
    char phone[12];
} person;

// vector without pop
typedef struct {
    size_t size;
    size_t capacity;
    person *arr;
} vector;

int push(vector *people, person last) {
    if (people -> size == people -> capacity) {
        people -> capacity *= 2;
        people -> arr = realloc(people -> arr, (people -> capacity) * sizeof(person));
    }
    (people -> arr)[(people -> size)++] = last;
    return people -> size;
}

// comparator:
//      left < right => -1
//      left > right =>  1
//      left = right =>  0
int compare_str(char *left, char *right) {
    int i = -1;
    do {
        i++;
        if (left[i] < right[i])
            return -1; // left is less
        if (left[i] > right[i])
            return 1; // right is less
    } while (left[i] != '\0' && right[i] != '\0');
    return 0; // equal
}

int compare_per(person left, person right) {
    switch (compare_str(left.surname, right.surname)) {
        case -1:
            return -1;
        case 1:
            return 1;
        default:
            switch (compare_str(left.name, right.name)) {
                case -1:
                    return -1;
                case 1:
                    return 1;
                default:
                    switch (compare_str(left.middle_name, right.middle_name)) {
                        case -1:
                            return -1;
                        case 1:
                            return 1;
                        default:
                            switch (compare_str(left.phone, right.phone)) {
                                case -1:
                                    return -1;
                                case 1:
                                    return 1;
                                default:
                                    return 0;
                            }
                    }
            }
    }
}

void swap(person *left, person *right) {
    person c = *left;
    *left = *right;
    *right = c;
}

// Insertion sort
void insertion_sort(person *arr, size_t size) {
    for (int i = 0; i < size; i++) {
        int j = i;
        while (j > 0 && compare_per(arr[j - 1], arr[j]) == 1) {
            swap(&arr[j], &arr[j - 1]);
            j--;
        }
    }
}

// QuickSort with only lesser branch recursion
void quick_sort(person *arr, size_t size) {
    if (size <= 1) {
        return;
    }
    person *i = arr, *j = arr + size - 1;
    person x = arr[size / 2];
    while (i <= j) {
        while (compare_per(*i, x) == -1) {
            i++;
        }
        while (compare_per(*j, x) == 1) {
            j--;
        }
        if (i >= j)
            break;
        swap(i++, j--);
    }
    // iterative on bigger part for recursion ~ log2(n)
    if (j - arr < arr + size - j) {
        insertion_sort(j, arr + size - j);
        quick_sort(arr, j - arr);
    } else {
        insertion_sort(arr, j - arr);
        quick_sort(j, arr + size - j);
    }
}

int main(int argc, char **argv) {
    switch(argc) {
        case 1:
            printf("%s", "Input file name missing");
            break;
        case 2:
            printf("%s", "Output file name missing");
            break;
        case 3:
            {
                FILE *fi = fopen(argv[1], "r");
                if (fi) {
                    FILE *fo = fopen(argv[2], "w");
                    if (fo) {
                        vector people = { 0, 10, malloc(10 * sizeof(person))};
                        person human = { "", "", "", ""};
                        while (fscanf(fi, "%s %s %s %s",
                                      human.surname, human.name, human.middle_name, human.phone) != EOF)
                        {
                            push(&people, human);
                        }
                        quick_sort(people.arr, people.size);
                        for(int i = 0; i < people.size; i++) {
                            fprintf(fo, "%s %s %s %s\n", people.arr[i].surname, people.arr[i].name,
                                    people.arr[i].middle_name, people.arr[i].phone);
                        }

                        free(people.arr);
                        printf("%s", "\tDone!");
                        fclose(fo);
                        return 0;
                    } else {
                        printf("%s", "Can't open output file");
                    }
                    fclose(fi);
                } else {
                    printf("%s", "Can't open input file");
                }
            }
			break;
        default:
            printf("%s", "Expected arguments: <input_file> <output_file>");
    }
}