#include <stdio.h>
#define ll long long
#define FOR(i,n) for(int i = 0; i < n; i++)
#define W(i) while (i)

void factorization(int num) {
 int i = 2;
 while (i * i <= num) {
  int exp = 0;
  while (num % i == 0) {
   num /= i;
   exp++;
  }
  if (exp > 0) printf("%d^%d ", i, exp);
  i++;
 }
 if (num > 1) printf("%d^1 ", num);
}

int main() {
 int n;
 scanf("%d", &n);
 factorization(n);
 return 0;
}