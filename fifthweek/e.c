//
// Created by takiyo on 10/10/2024.
//
#include <stdio.h>

void swap(char *a, char *b) {
    char temp = *a;
    *a = *b;
    *b = temp;
}

int main() {
    char x1, x2, x3;
    scanf(" %c %c %c", &x1, &x2, &x3);

    char blocks[3] = {x1, x2, x3};
    int indices[3] = {1, 2, 3};

    for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 2 - i; j++) {
            if (blocks[j] > blocks[j + 1]) {
                swap(&blocks[j], &blocks[j + 1]);
                int temp = indices[j];
                indices[j] = indices[j + 1];
                indices[j + 1] = temp;
            }
        }
    }

    printf("%d %d %d\n", indices[0], indices[1], indices[2]);

    return 0;
}
