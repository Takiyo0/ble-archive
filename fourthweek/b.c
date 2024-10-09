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
        int full = anything[i][0];
        int ex = anything[i][1];

        int tot = full;
        int empty = full;

        while (empty >= ex) {
            int newCups = empty / ex;
            tot += newCups;
            empty = empty % ex + newCups;
        }

        printf("Case #%d: %d\n", i + 1, tot);
    }

    return 0;
}
