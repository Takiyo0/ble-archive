//
// Created by takiyo on 10/10/2024.
//
#include <stdio.h>

long long reverseNumber(long long n) {
    long long reversed = 0;
    while (n > 0) {
        reversed = reversed * 10 + (n % 10);
        n /= 10;
    }
    return reversed;
}

int main() {
    int t;
    scanf("%d", &t);

    for (int i = 0; i < t; i++) {
        long long n;
        scanf("%lld", &n);

        long long reversedN = reverseNumber(n);
        long long result = n + reversedN;

        printf("Case #%d: %lld\n", i + 1, result);
    }

    return 0;
}
