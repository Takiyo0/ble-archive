#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_LIFEPOINTS 3

char *getCard(const int i) {
    switch (i) {
        case 3:
            return "♥";
        case 4:
            return "♦";
        case 5:
            return "♣";
        case 6:
            return "♠";
        default:
            return "";
    }
}

void clearCli() {
#ifdef _WIN32
    system("cls");
#else
        system("clear");
#endif
}

void printCards(int cards[4][4]) {
    printf("Y\n");
    printf("↑ ╔═══╦═══╦═══╦═══╗");
    for (int i = 0; i < 4; i++) {
        if (i != 0) printf("\n  ╠═══╬═══╬═══╬═══╣");
        printf("\n%d ║ %s ║ %s ║ %s ║ %s ║", 4 - i, getCard(cards[i][0]), getCard(cards[i][1]), getCard(cards[i][2]),
               getCard(cards[i][3]));
        if (i == 3) printf("\n  ╚═══╩═══╩═══╩═══╝\n");
    }
    printf("    1   2   3   4 → X\n");
}

void printCardsWithWhatToShow(int cards[4][4], int whatToShow[4][4], const int lifepoints) {
    printf("Y\n");
    printf("↑ ╔═══╦═══╦═══╦═══╗");
    for (int i = 0; i < 4; i++) {
        if (i != 0) printf("\n  ╠═══╬═══╬═══╬═══╣");
        printf("\n%d ║ %s ║ %s ║ %s ║ %s ║", 4 - i,
               whatToShow[i][0] == 2 ? "x" : whatToShow[i][0] ? getCard(cards[i][0]) : " ",
               whatToShow[i][1] == 2 ? "x" : whatToShow[i][1] ? getCard(cards[i][1]) : " ",
               whatToShow[i][2] == 2 ? "x" : whatToShow[i][2] ? getCard(cards[i][2]) : " ",
               whatToShow[i][3] == 2 ? "x" : whatToShow[i][3] ? getCard(cards[i][3]) : " ");
        if (i == 3) printf("\n  ╚═══╩═══╩═══╩═══╝\n");
    }
    printf("    1   2   3   4 → X\n\n");
    printf("Lifepoints: ");
    for (int i = 0; i < MAX_LIFEPOINTS; i++) {
        if (i < lifepoints) printf("♥ ");
        else printf("♡ ");
    }
    printf(" (%d/%d) \n\n", lifepoints, MAX_LIFEPOINTS);
}

int getRandomNumberBetween(int min, int max) {
    return rand() % (max - min + 1) + min;
}

int howManyCards(int cards[4][4], int card) {
    int count = 0;
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            if (cards[i][j] == card) count++;
        }
    }
    return count;
}

int isWinner(int whatToShow[4][4]) {
    for (int i = 0; i < 4; i++) {
        if (whatToShow[i][0] == 1 && whatToShow[i][1] == 1 && whatToShow[i][2] == 1 && whatToShow[i][3] == 1) {
            return 1;
        }
    }
    return 0;
}

int main(void) {
    srand(time(0));

    int cards[4][4];
    int whatToShow[4][4] = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    int lifepoints = MAX_LIFEPOINTS;
    int hidePrompts = 0;

    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            while (1) {
                int targetNumber = getRandomNumberBetween(3, 6);
                if (howManyCards(cards, targetNumber) < 4) {
                    cards[i][j] = targetNumber;
                    break;
                }
            }
        }
    }

    while (1) {
        clearCli();
        char choice;
        printf(
            "==============================================================\n"
            "|| .------..------..------..------..------..------..------. ||\n"
            "|| |N.--. ||E.--. ||L.--. ||C.--. ||A.--. ||R.--. ||D.--. | ||\n"
            "|| | :(): || (\\/) || :/\\: || :/\\: || (\\/) || :(): || :/\\: | ||\n"
            "|| | ()() || :\\/: || (__) || :\\/: || :\\/: || ()() || (__) | ||\n"
            "|| | '--'N|| '--'E|| '--'L|| '--'C|| '--'A|| '--'R|| '--'D| ||\n"
            "|| `------'`------'`------'`------'`------'`------'`------' ||\n"
            "||                                                          ||\n"
            "||                  Welcome to NeLcard Game!                ||\n"
            "||                                                          ||\n"
            "||  Game Rules:                                             ||\n"
            "||  1. You have %d lifepoints.                               ||\n"
            "||  2. You have to remember the card's position.            ||\n"
            "||  3. Enjoy the game!!.                                    ||\n"
            "||                                                          ||\n"
            "||                                                          ||\n"
            "===================|| Choose an option ||=====================\n"
            "\n\n"
            "1. Play Game\n"
            "2. Toggle retry/continue prompts (%s)\n"
            "3. Exit\n"
            ">> ", MAX_LIFEPOINTS, hidePrompts ? "hidden" : "visible");

        scanf("%c", &choice);
        if (strcmp(&choice, "1") == 0) break;
        if (strcmp(&choice, "2") == 0) hidePrompts = !hidePrompts;
        if (strcmp(&choice, "3") == 0) exit(0);
        printf("Invalid choice!\n");
    }
    clearCli();
    printCards(cards);
    printf("\nRemember the card's position. Press Enter to begin the game...\n");
    getchar();
    getchar();
    clearCli();

    int state = 0; // 0 = doing first card, 1 = doing second card
    int selectedFirstCard[2] = {-1, -1};

    while (1) {
        clearCli();
        int x, y;
        printCardsWithWhatToShow(cards, whatToShow, lifepoints);
        printf("Input the %s card's position (x y): ", state == 0 ? "first" : "second");
        scanf("%d %d", &x, &y);
        y = 5 - y;
        if (x < 1 || x > 4 || y < 1 || y > 4) {
            if (hidePrompts) continue;
            printf("Invalid position!\n");
            printf("Press any key to retry...\n");
            getchar();
            getchar();
            continue;
        }
        if (whatToShow[y - 1][x - 1] != 0) {
            if (hidePrompts) continue;
            printf("Card already shown!\n");
            printf("Press any key to retry...\n");
            getchar();
            getchar();
            continue;
        }
        if (cards[y - 1][x - 1] == 0) {
            if (hidePrompts) continue;
            printf("Card not found!\n");
            printf("Press any key to retry...\n");
            getchar();
            getchar();
            continue;
        }

        if (whatToShow[y - 1][x - 1] == 1) {
            if (hidePrompts) continue;
            printf("Card already selected!\n");
            printf("Press any key to retry...\n");
            getchar();
            getchar();
            continue;
        }

        if (state == 0) {
            whatToShow[y - 1][x - 1] = cards[y - 1][x - 1];
            selectedFirstCard[0] = y - 1;
            selectedFirstCard[1] = x - 1;
            state = 1;
        } else {
            if (cards[y - 1][x - 1] == cards[selectedFirstCard[0]][selectedFirstCard[1]]) {
                whatToShow[y - 1][x - 1] = 2;
                whatToShow[selectedFirstCard[0]][selectedFirstCard[1]] = 2;
                state = 0;
                selectedFirstCard[0] = -1;
                selectedFirstCard[1] = -1;

                const int isWin = isWinner(whatToShow);
                if (isWin) {
                    printf("You won! Thank you for playing!\n");
                    return 0;
                }

                if (hidePrompts) continue;
                clearCli();
                printCardsWithWhatToShow(cards, whatToShow, lifepoints);
                printf("Cards are matched. Press any key to continue...\n");
                getchar();
                getchar();
            } else {
                whatToShow[y - 1][x - 1] = 0;
                whatToShow[selectedFirstCard[0]][selectedFirstCard[1]] = 0;
                lifepoints--;
                state = 0;
                if (lifepoints == 0) {
                    printf("Game Over! Thank you for playing!\n");
                    return 0;
                }

                if (hidePrompts) continue;
                clearCli();
                printCardsWithWhatToShow(cards, whatToShow, lifepoints);
                printf("Cards are not matched. You lost 1 lifepoint.\n");
                printf("Press any key to continue...\n");
                getchar();
                getchar();
            }
        }
    }
}
