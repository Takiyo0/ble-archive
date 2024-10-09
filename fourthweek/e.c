//
// Created by takiyo on 10/3/2024.
//
#include <stdio.h>
#include <stdlib.h>

int main() {
    long long int t;
    scanf("%lld", &t);

    long long int (*anything)[2] = malloc(t * sizeof(*anything));
    if (anything == NULL) {
        printf("Memory allocation failed.\n");
        return 1;
    }

    for (int i = 0; i < t; i++) {
        scanf("%lld %lld", &anything[i][0], &anything[i][1]);
    }

    for (int i = 0; i < t; i++) {
        long long int degree = anything[i][0];
        long long int seconds = anything[i][1];
        double degreeTotal = degree * seconds;
        double countMade = degreeTotal / 360.0f;
        printf("%.2f\n", countMade);
    }

    free(anything);

    return 0;
}
