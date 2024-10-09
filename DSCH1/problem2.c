#include <stdio.h>

int main(void) {
    int n, m; // 5 3
    scanf("%d %d", &n, &m);
    int nums[m][n];

    for (int j = 0; j < n; j++) {
        int num;
        scanf("%d", &num);
        for (int i = 0; i < m; i++) {
            nums[i][j] = num;
        }
    }
    int final[m];
    for (int i = m - 1; i >= 0; i--) {
        int lastIndex = n - i;
        int firstIndex = lastIndex - m;
        int highest = 0;
        // printf("Getting the highest number between index %d and %d: ", firstIndex, lastIndex);
        for (int j = firstIndex; j < lastIndex; j++) {
            if (nums[i][j] > highest) {
                highest = nums[i][j];
            }
        }
        // printf("\n Got the highest number: ");
        final[i] = highest;
    }
    for (int i = m - 1; i >= 0; i--)
        printf("%d ", final[i]);
    printf("\n");

    return 0;
}
