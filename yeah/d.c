//
// Created by takiyo on 9/26/2024.
//
#include <stdio.h>

int main(void) {
    char s[3];
    for (int i = 0; i < 3; i++) {
        char t[3];
        scanf("%s", t);
        s[i] = t[1];
    }

    for (int i = 0; i < 3; i++) {
        int c = s[i] - '0';
        printf("%d\n", c);
    }

    return 0;
}
