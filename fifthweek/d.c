//
// Created by takiyo on 10/10/2024.
//
#include <stdio.h>

int main() {
    int n;
    scanf("%d", &n);

    for (int i = 0; i < n; i++) {
        int c;
        scanf("%d", &c);

        printf("Case #%d:\n", i + 1);
        for (int a = 1; a <= c; a++) {
            if ((a % 3 == 0 || a % 5 == 0) && a % 15 != 0) {
                printf("%d Jojo\n", a);
            } else printf("%d Lili\n", a);
        }
    }
    return 0;
}
