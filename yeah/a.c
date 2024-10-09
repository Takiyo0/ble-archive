//
// Created by takiyo on 9/26/2024.
//
#include <stdio.h>

int main() {
    int N;
    double P;
    double original_prices[4];

    for (int i = 0; i < 4; i++) {
        scanf("%d %lf", &N, &P);
        original_prices[i] = P / (1 - (N / 100.0));
    }

    for (int i = 0; i < 4; i++) {
        printf("$%.2lf\n", original_prices[i]);
    }

    return 0;
}
