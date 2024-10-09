//
// Created by takiyo on 9/26/2024.
//
#include <stdio.h>

// int main(void) {
//     int t;
//     scanf("%d", &t);
//     getchar();
//     int data[t][2];
//     for (int i = 0; i < t; i++) {
//         scanf("%d %d", &data[i][0], &data[i][1]);
//     }
//
//     for (int i = 0; i < t; i++) {
//         int degree = data[i][0];
//         int seconds = data[i][1];
//         float degreeTotal = degree * seconds;
//         float countMade = (degreeTotal) / 360;
//         printf("%.2f\n", countMade);
//     }
//
//     return 0;
// }
#include <stdio.h>

int main(void) {
    int t;
    scanf("%d", &t); getchar();

    for (int i = 0; i < t; i++) {
        int c;
        scanf("%d", &c); getchar();
        int data[c][c];
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < c; j++) {
                scanf("%d", &data[i][j]);
            }
            getchar();
        }

        int sum[c];
        for (int i = 0; i < c; i++) {
            int sum = 0;
            for (int j = 0; j < c; j++) {
                sum += data[j][i];
            }
            printf("%d ", sum);
        }
    }
}