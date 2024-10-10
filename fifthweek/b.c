//
// Created by takiyo on 10/10/2024.
//
#include <stdio.h>
#include <math.h>

int howMuchFactors(int n) {
    int count = 0;
    for (int i = 1; i * i <= n; i++) {
        if (n % i == 0) {
            count++;
            if (i != n / i) {
                count++;
            }
        }
    }
    return count;
}

int main() {
    int n;
    scanf("%d", &n);

    for (int i = 0; i < n; i++) {
        int a;
        scanf("%d", &a);
        printf("Case #%d: %d\n", i + 1, howMuchFactors(a));
    }
    return 0;
}
