//
// Created by takiyo on 10/10/2024.
//
#include <stdio.h>

int main() {
    int t, n;
    scanf("%d", &t);

    for (int i = 0; i < t; i++) {
        scanf("%d", &n);
        int data[n];
        int jojosum = 0, lilisum = 0;
        for (int j = 0; j < n; j++) {
            scanf("%d", &data[j]);
        }
        for (int j = 0; j < n; j++) {
            if (j % 2 == 0 && data[j] % 2 == 0) {
                jojosum++;
            } else if (j % 2 != 0 && data[j] % 2 != 0) {
                lilisum++;
            }
        }

        if (jojosum % 2 == 0 && lilisum % 2 == 0) {
            printf("Case #%d: :)\n", i + 1);
        } else if (jojosum % 2 == 1 && lilisum % 2 == 1) {
            printf("Case #%d: :(\n", i + 1);
        } else {
            printf("Case #%d: :|\n", i + 1);
        }
    }
    return 0;
}
