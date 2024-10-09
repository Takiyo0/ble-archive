//
// Created by takiyo on 9/13/2024.
//
#include <stdio.h>
#include <stdlib.h>

int main(void) {
    int n; scanf("%d", &n);
    char *name = (char *)malloc((n + 1) * sizeof(char));
    int a = 0;
    for (int i = 0; i < n; i++) {
        char c; scanf(" %c", &c);
        if (i == 0 || name[a - 1] != c) name[a++] = c;
    }
    name[a] = '\0';
    printf("%s\n", name);
    free(name);
    return 0;
}
