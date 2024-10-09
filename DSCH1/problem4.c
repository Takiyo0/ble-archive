#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <tgmath.h>

int isLowerCase(const char c) {
    return islower(c);
}

int main(void) {
    int n;
    scanf("%d", &n);
    char chars[n];

    for (int i = 0; i < n; i++) {
        getchar();
        scanf("%c", &chars[i]);
    }

    int firstLowerGroupIndex = -1;
    int lastLowerGroupIndex = -1;
    int maxGroupSize = 0;
    int currentGroupStart = -1;
    int currentGroupSize = 0;

    for (int i = 0; i < n; i++) {
        if (isLowerCase(chars[i])) {
            if (currentGroupSize == 0) {
                currentGroupStart = i;
            }
            currentGroupSize++;
        } else {
            if (currentGroupSize > maxGroupSize) {
                maxGroupSize = currentGroupSize;
                firstLowerGroupIndex = currentGroupStart;
                lastLowerGroupIndex = i - 1;
            }
            currentGroupSize = 0;
        }
    }

    if (currentGroupSize > maxGroupSize) {
        maxGroupSize = currentGroupSize;
        firstLowerGroupIndex = currentGroupStart;
        lastLowerGroupIndex = n - 1;
    }

    int totalSteps = 0;

    for (int i = 0; i < n; i++) {
        if (chars[i] == 'a') {
            if (i < firstLowerGroupIndex || i > lastLowerGroupIndex) {
                int stepsToNearestEnd = fmin(abs(i - firstLowerGroupIndex), abs(i - lastLowerGroupIndex));
                totalSteps += stepsToNearestEnd;
            }
        }
    }

    printf("%d", totalSteps);

    return 0;
}
