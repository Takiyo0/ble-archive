//
// Created by takiyo on 9/19/2024.
//
#include <stdio.h>
#ifdef WIN32
#include <windows.h>
#else
#include <unistd.h>
#endif

# define BERSIHIN getchar(); getchar(); getchar();

int main() {
  int first, second;
  scanf("%d", &first);
  BERSIHIN; BERSIHIN; BERSIHIN;
  scanf("%d", &second);

  printf("%d\n", first + second);
}
