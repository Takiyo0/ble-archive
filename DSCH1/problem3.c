#include <stdio.h>

#define MOD 1000000007
#define lli long long int

void sort(lli arr[], const int n) {
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (arr[i] > arr[j]) {
                const lli temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
    }
}

int main(void) {
    int n, l, r;
    scanf("%d", &n);

    lli m[n];
    for (int i = 0; i < n; i++)
        scanf("%lld", &m[i]);
    scanf("%d %d", &l, &r);

    lli res[n * (n - 1) / 2];
    int range = 2;
    int step = 0;
    int index = 0;

    while (1) {
        const int firstIndex = step;
        const int lastIndex = step + range - 1;
        if ((range - 1) >= n) {
            break;
        }
        if (lastIndex >= n) {
            range++;
            step = 0;
            continue;
        }

        lli sum = 0;

        for (int i = firstIndex; i <= lastIndex; i++) {
            sum = (sum + m[i]) % MOD;
        }
        res[index] = sum;
        // printf("Getting data with %d range on step %d between %d and %d\n", range, step, firstIndex, lastIndex);
        step++;
        index++;
    }

    const int combinedSize = n + (n * (n - 1) / 2);
    lli combined[combinedSize];
    for (int i = 0; i < n; i++) {
        combined[i] = m[i];
    }
    for (int i = 0; i < n * (n - 1) / 2; i++) {
        combined[n + i] = res[i];
    }
    sort(combined, combinedSize);
    lli finalSum = 0;
    for (int i = l; i <= r; i++)
        finalSum = (finalSum + combined[i]) % MOD;
    printf("%lld\n", finalSum);
}
