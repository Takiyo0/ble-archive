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

    for (int i = 0; i < t; i++) {
        int n = anything[i][0];
        int x = anything[i][1];

        int frontFlips = x / 2;
        int backFlips = (n / 2) - frontFlips;

        int minFlips = (frontFlips < backFlips) ? frontFlips : backFlips;
        printf("Case #%d: %d\n", i + 1, minFlips);
    }

    return 0;
}
