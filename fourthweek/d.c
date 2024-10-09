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
        int x = anything[i][0];
        int y = anything[i][1];

        int bit = (x >> y) & 1;
        printf("%d\n", bit);
    }

    return 0;
}
