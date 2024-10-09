#include <stdio.h>

void drawRight(int w, int h) {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < i; j++) {
            printf(" ");
        }

        if (i == 0 || i == h - 1) {
            for (int j = 0; j < w; j++) {
                printf("*");
            }
        } else {
            printf("*");
            for (int j = 0; j < w - 2; j++) {
                printf(" ");
            }
            printf("*");
        }

        printf("\n");
    }
}

void drawLeft(int w, int h) {
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < h - i - 1; j++) {
            printf(" ");
        }

        if (i == 0 || i == h - 1) {
            for (int j = 0; j < w; j++) {
                printf("*");
            }
        } else {
            printf("*");
            for (int j = 0; j < w - 2; j++) {
                printf(" ");
            }
            printf("*");
        }

        printf("\n");
    }
}

int main() {
    int w, h;
    char direction;

    scanf("%d %d", &w, &h); getchar();
    scanf("%c", &direction);

    if (direction == 'l')
        drawRight(w, h);
    else if (direction == 'r')
        drawLeft(w, h);
    else
        printf("Invalid direction!\n");

    return 0;
}
