#include <stdio.h>
#include <math.h>

void getPrimeFactorFrom(int num, int *factors, int *size) {
    int index = 0;

    while (num % 2 == 0) {
        factors[index++] = 2;
        num /= 2;
    }

    for (int i = 3; i <= sqrt(num); i += 2) {
        while (num % i == 0) {
            factors[index++] = i;
            num /= i;
        }
    }

    if (num > 2) {
        factors[index++] = num;
    }

    *size = index;
}

int main() {
    int n;
    scanf("%d", &n);
    getchar();

    int mainFactorial[100];
    int mainSize = 0;
    getPrimeFactorFrom(n, mainFactorial, &mainSize);

    int factorials[n][100];

    int total = 0;
    for (int i = 2; i < n; i++) {
        int size = 0;
        getPrimeFactorFrom(i, factorials[i], &size);

        int hasSameNeko = 0;

        for (int j = 0; j < size; j++) {
            for (int h = 0; h < mainSize; h++) {
                if (factorials[i][j] == mainFactorial[h]) {
                    hasSameNeko = 1;
                }
            }
        }

        if (!hasSameNeko) {
            total++;
        }
    }
    printf("%d\n", total);

    return 0;
}
