#include <stdio.h>

int main(void) {
    int matrix[4][4] = {
        {1, 2, 3, 4},
        {2, 3, 4, 3},
        {3, 4, 3, 2},
        {4, 3, 2, 1}
    };

    int inputs[3][4];

    for (int i = 0; i < 4; i++)scanf("%d", &inputs[0][i]); getchar();
    for (int i = 0; i < 4; i++) scanf("%d", &inputs[1][i]); getchar();
    for (int i = 0; i < 4; i++) scanf("%d", &inputs[2][i]); getchar();


    int indexSets[4][4] = {
        {0, 1, 2, 3},
        {1, 2, 3, 2},
        {2, 3, 2, 1},
        {3, 2, 1, 0}
    };

    for (int n = 0; n < 3; n++) {
        float sum = 0;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                int target = inputs[n][indexSets[y][x]];
                sum += (float) target / matrix[x][y];
            }
        }
        printf("%.2f\n", sum);
    }

    return 0;
}
