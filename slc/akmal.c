#include <stdio.h>
#include <stdlib.h>
#include <time.h>

char _heartSymbol[] = "\u2665";
char _spadeSymbol[] = "\u2660";
char _diamondSymbol[] = "\u2666";
char _clubSymbol[] = "\u2663";

char heartSymbol = 'H';
char spadeSymbol = 'S';
char diamondSymbol = 'D';
char clubSymbol = 'C';


int lifepoint = 3;
int guessCount = 0;
char board[4][4] = {
    {'H', 'S', 'D', 'C'},
    {'H', 'S', 'D', 'C'},
    {'H', 'S', 'D', 'C'},
    {'H', 'S', 'D', 'C'}
};

int hasGuess[4][4];

void clear(){
    #ifdef _WIN32
        system("cls");
    #else
        system("clear");
    #endif
}

char randomCard(){
    char cards[] = {heartSymbol, spadeSymbol, diamondSymbol, clubSymbol};
    return cards[rand() % 4];
}

void printCard(char symbol){
    switch(symbol){
        case 'H':
            printf("%s", _heartSymbol);
        break;
        case 'S':
            printf("%s", _spadeSymbol);
        break;
        case 'D':
            printf("%s", _diamondSymbol);
        break;
        case 'C':
            printf("%s", _clubSymbol);
            break;
    }
}

void printBoard(){
    printf("Y\n-------------\n");
    for(int i = 4; i > 0; i--){
        printf("%d  ", i);
        for(int j = 4; j > 0; j--){
            printCard(board[i-1][j-1]);
            printf(" ");
        }
        printf("\n");
    }
    printf("-------------");
    printf("\n   1 2 3 4 X\n\n");
}

void printHidden(){
    printf("Y\n-------------\n");
    for(int i = 4; i > 0; i--){
        printf("%d  ", i);
        for(int j = 4; j > 0; j--){
            if(hasGuess[i-1][j-1]){
                printf("x ");
            }else {
                printf("? ");
            }
        }
        printf("\n");
    }
    printf("-------------");
    printf("\n   1 2 3 4 X\n\n");
}

void printExcept(int x1, int y1, int x2, int y2){
    printf("Y\n-------------\n");
    for(int i = 4; i > 0; i--){
        printf("%d  ", i);
        for(int j = 4; j > 0; j--){
            if(hasGuess[i-1][j-1]){
                printf("x ");
            } else if((i == y1 && j == x1) || (i == y2 && j == x2)){
                printCard(board[i-1][j-1]);
                printf(" ");
            }else{
                printf("? ");
            }
        }
        printf("\n");
    }
    printf("-------------");
    printf("\n   1 2 3 4 X\n\n");
}

int isValidCord(int x){
    if(x < 1 || x > 4){
        return 0;
    }
    return 1;
}

void shuffleBoard(){
    char deck[16];
    for (int i = 0; i < 4; i++) {
        deck[i] = heartSymbol;
        deck[i+4] = spadeSymbol;
        deck[i+8] = diamondSymbol;
        deck[i+12] = clubSymbol;
    }
    for (int i = 15; i > 0; i--) {
        int j = rand() % (i + 1);
        char temp = deck[i];
        deck[i] = deck[j];
        deck[j] = temp;
    }

    int index = 0;
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            board[i][j] = deck[index++];
        }
    }
}

void playGame(){
    clear();

    int heartsCount = 0;
    int spadesCount = 0;
    int diamondsCount = 0;
    int clubsCount = 0;

    shuffleBoard();

    printBoard();
    printf("Press ENTER to start playing..");
    getchar();
    clear();

    while(1){
        int xCord = 1, yCord = 1, xCord2 = 1, yCord2 = 1;
        printHidden();

        printf("Lifepoint: %d\n", lifepoint);

        while(1){
            while(1){
                printf("Input the x coordinate of first card: ");
                scanf("%d", &xCord);
                getchar();
                if(isValidCord(xCord)){
                    break;
                }
                printf("Invalid X, please input a number between 1 and 4\n");
            }

            while(1){
                printf("Input the y coordinate of first card: ");
                scanf("%d", &yCord);
                getchar();
                if(isValidCord(yCord)){
                    break;
                }
                printf("Invalid Y, please input a number between 1 and 4\n");
            }
            xCord = 5 - xCord;
            if(hasGuess[yCord-1][xCord-1] == 1){
                printf("Please choose different cards!\n");
                continue;
            }
            break;
        }

        clear();
        printExcept(xCord, yCord, -1, -1);

        while(1){
            while(1){
                printf("Input the x coordinate of second card: ");
                scanf("%d", &xCord2);
                getchar();
                if(isValidCord(xCord2)){
                    break;
                }
                printf("Invalid X, please input a number between 1 and 4\n");
            }

            while(1){
                printf("Input the y coordinate of second card: ");
                scanf("%d", &yCord2);
                getchar();
                if(isValidCord(yCord2)){
                    break;
                }
                printf("Invalid Y, please input a number between 1 and 4\n");
            }

            xCord2 = 5 - xCord2;
            if((xCord == xCord2 && yCord == yCord2) || hasGuess[yCord2-1][xCord2-1] == 1){
                printf("Please choose different cards!\n");
                continue;
            }
            break;
        }

        clear();
        printExcept(xCord, yCord, xCord2, yCord2);

        hasGuess[yCord-1][xCord-1] = 1;
        hasGuess[yCord2-1][xCord2-1] = 1;
        guessCount++;

        if(board[yCord-1][xCord-1] == board[yCord2-1][xCord2-1] || guessCount >= 8){
            printf("Cards are matched!\n");
            if(guessCount >= 8){
                printf("You Win!\n");
            }
            printf("Press ENTER to continue..");
            getchar();
            if(guessCount >= 8){
                return;
            }
            
        }else{
            printf("Cards are not matched! Lifepoint -1...\n");
            lifepoint--;
            if(lifepoint <= 0){
                printf("Game Over!\n");
            }
            printf("Press ENTER to continue..");
            getchar();
            if(lifepoint <= 0){
                return;
            }

        }
        clear();
    }
}

int main(){
    srand(time(NULL));
    int menu;

    printf("1. Play Game\n");
    printf("2. Exit\n");

    printf(">> ");

    scanf("%d", &menu);
    getchar();

    switch(menu){
        case 1:
            playGame();
        break;
        case 2:
            printf("Exiting...\n");
            return 0;
    }
    return 0;
}
