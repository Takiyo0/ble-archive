//
// Created by takiyo on 9/19/2024.
//
#include <stdio.h>

int main(void) {
    char name[100];
    scanf("%[^\n]", name);
    printf("\"Hello %s!  Welcome to C Programming!\"\n", name);
    return 0;
}