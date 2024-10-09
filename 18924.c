#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(void) {
    char name[256], nim[11], gender[2], g[2] = "y";

    printf("                                       _ _     _           _       _\n"
           "  _ __   ___ _ __ __ _ _ __ ___   __ _| | |__ (_) ___   __| | __ _| |_ __ _\n"
           " | '_ \\ / _ | '__/ _` | '_ ` _ \\ / _` | | '_ \\| |/ _ \\ / _` |/ _` | __/ _` |\n"
           " | |_) |  __| | | (_| | | | | | | (_| | | |_) | | (_) | (_| | (_| | || (_| |\n"
           " | .__/ \\___|_|  \\__,_|_| |_| |_|\\__,_|_|_.__/|_|\\___/ \\__,_|\\__,_|\\__\\__,_|\n"
           " |_|\n\n");
    printf("\rWat is your name :"); scanf("%[^\n]", name);
    printf("\rWat is your nim :"); scanf("%s", nim);
    printf("\rWat is your gender? (P/L) :"); scanf("%s", gender);
    printf("\rAre you SMA? (y/n) :"); scanf("%s", g);

    printf("\n\n==============Hasil Ramalan Saya=================\n"
           "Kimi no namae wa %s\nKimi no NIM wa %s\nYour gender is %s\nSMA? %s\n",
           name, nim, strcmp(gender, "p") == 0 ? "pria" : "wanita", strcmp(g, "y") == 0 ? "yes" : "no");

    return 0;
}