//
// Created by takiyo on 9/13/2024.
//
#include <stdio.h>

int main(void) {
    int day;
    scanf("%d", &day);
    int min = 1e+7, max = 1e-7, sum = 0;
    for (int i = 0; i < day; i++) {
        int a; scanf("%d", &a);
        sum += i + a;
        if (i == 0) {
            min = a; max = a;
        } else {
            min++; max++;
            if (a < min) min = a;
            if (a > max) max = a;
        }
        printf("%d %d %.4f\n", min, max, (double) sum / (i + 1));
    }
    return 0;
}
