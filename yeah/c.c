//
// Created by takiyo on 9/26/2024.
//
#include <stdio.h>

int main(void) {
    int n;
    scanf("%d", &n);
    getchar();
    int values[n];

    for (int i = 0; i < n; i++) {
        int a, b;
        scanf("%d %d", &a, &b);
        values[i] = (a / b) << b;
    }

    for (int i = 0; i < n; i++) {
        printf("%d\n", values[i]);
    }

    return 0;
}
