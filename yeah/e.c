//
// Created by takiyo on 9/26/2024.
//
#include <stdio.h>

int main(void) {
    // Lili is playing a game called Wosu. Wosu is a music game where Lili must click buttons
    // based on music rhythm. Button that is successfully pressed has value of 100 point and 0
    // bonus point (initially). Each time Lili successfully pressed a button, bonus point increased
    // by 50. Lili wants to know the total point after N button(s) clicked.
    // if input 1, output 100. if input 2, output 250

    int n;
    scanf("%d", &n);

    int totalClicked = 0;
    long long int sum = 0;
    for (int i = 0; i < n; i++) {
        sum += 100 + totalClicked * 50;
        totalClicked++;
    }


    printf("%lld\n", sum);

    return 0;
}
