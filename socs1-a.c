//
// Created by takiyo on 9/19/2024.
//
#include <stdio.h>
#ifdef WIN32
    #include <windows.h>
#else
#include <unistd.h>
#endif

# define BERSIHIN getchar();

int main() {
    printf("Binus FTW!\n");
    return 0;
}
