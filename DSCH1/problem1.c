#include <stdio.h>
#include <stdlib.h>

int main() {
    int ns[3];
    scanf("%d %d %d", &ns[0], &ns[1], &ns[2]);

    char sns[3][7];
    for (int i = 0; i < 3; i++) {
        sprintf(sns[i], "%06d", ns[i]);
    }

    char res[7];
    for (int i = 0; i < 6; i++) {
        res[i] = sns[0][i];
        if (sns[1][i] < res[i]) {
            res[i] = sns[1][i];
        }
        if (sns[2][i] < res[i]) {
            res[i] = sns[2][i];
        }
    }
    res[6] = '\0';
    printf("%d\n", atoi(res));
    return 0;
}
