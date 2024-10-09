//
// Created by takiyo on 10/3/2024.
//
#include <stdio.h>

int main() {
    int t;
    scanf("%d", &t);
    int anything[t][2];

    for (int i = 0; i < t; i++) {
        scanf("%d %d", &anything[i][0], &anything[i][1]);
    }
    for (int i = 1; i <= t; i++) {
        int acq = anything[i - 1][0] * anything[i - 1][1];

        printf("Case #%d: ", i);
        if (acq % 2 == 0) {
            printf("Party time!\n");
        } else {
            printf("Need more frogs\n");
        }
    }

    return 0;
}
