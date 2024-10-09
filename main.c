#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(void) {
    int n = 6;
    char *o = malloc(1e+9);
    while (n--) {
        char y[100] = "";
        scanf("%s", y);
        // check if it has \n in the end
        bool has_nl = strchr(y, '\n') == NULL;
        if (has_nl) strcat(y, "zsz\n");
        else strcat(y, "\n");
        strcat(o, y);
        if (n == 0) strcat(o, "\0");
    }

    printf("%s", o);

    free(o);

    return 0;
}
