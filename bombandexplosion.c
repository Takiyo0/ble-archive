//
// Created by takiyo on 9/13/2024.
// NOT WORKING
//
#include <stdio.h>
#include <math.h>

int main(void) {
    int N, M, R; scanf("%d %d %d", &N, &M, &R);
    int c = 2 * R + 1;
    int bx = (int)ceil((double)M / c);
    int by = (int)ceil((double)N / c);
    int totalBombs = bx * by;
    printf("%d", totalBombs);
    return 0;
}

