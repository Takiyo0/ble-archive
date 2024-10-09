//
// Created by takiyo on 9/13/2024.
//
#include <stdio.h>

int main(void) {
    int fl = 6;
    int sum = 0; scanf("%d", &sum);

    while (fl > 0) {
        int n; scanf("%d", &n);
        if (n < 0) n *= -1;
        sum += n;
        fl--;
    }

    printf("%d", sum);
}