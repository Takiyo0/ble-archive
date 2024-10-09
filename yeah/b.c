//
// Created by takiyo on 9/26/2024.
//
#include <stdio.h>
#include <math.h>

int main() {
    double l, b, h;

    scanf("%lf %lf %lf", &l, &b, &h);

    double la = 3 * b * l;
    double ba = (sqrt(3) / 4) * b * b;
    double t = la + 2 * ba;

    printf("%.3lf\n", t);

    return 0;
}
