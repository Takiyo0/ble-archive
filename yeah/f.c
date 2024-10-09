//
// Created by takiyo on 9/26/2024.
//
#include <stdio.h>

int is_armstrong(int num) {
    int temp = num;
    int sum = 0;
    int digit;

    while (temp != 0) {
        digit = temp % 10;
        sum += digit * digit * digit;
        temp /= 10;
    }

    return (sum == num);
}

int main(void) {
    int n;
    scanf("%d", &n);
    getchar();
    if (is_armstrong(n))
        printf("Woah! It's an Armstrong Number!\n");
    else
        printf("Uh Oh.  It's not an Armstrong Number.\n");
    return 0;
}

