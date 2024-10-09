#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

void printMenu() {
    printf(
        "\n\n"
        "1. Play\n"
        "2. Exit\n"
        "Select an option: ");
}

void printDifficulty() {
    printf(
        "\n\n"
        "Select difficulty: "
        "1. Easy\n"
        "2. Hard\n"
        "Select an option: ");
}

void clrs() {
#ifdef _WIN32
    system("cls");
#else
    system("clear");
#endif
}

void waitForEnter() {
    printf("\nPress Enter to continue...");
    getchar(); // Waits for Enter key press
}

void printBoard(int board[3][3]) {
    printf("\n");
    for (int y = 0; y < 3; y++) {
        for (int x = 0; x < 3; x++) {
            if (board[y][x] == 0) {
                printf("   ");
            } else if (board[y][x] == 1) {
                printf(" X ");
            } else if (board[y][x] == 2) {
                printf(" O ");
            }
            if (x < 2) {
                printf("|");
            }
        }
        printf("\n");
        if (y < 2) {
            printf("---+---+---\n");
        }
    }
    printf("\n");
}

int checkWin(int board[3][3]) {
    for (int i = 0; i < 3; i++) {
        if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0)
            return board[i][0];
        if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0)
            return board[0][i];
    }
    if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0)
        return board[0][0];
    if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0)
        return board[0][2];

    return 0;
}

int isBoardFull(int board[3][3]) {
    for (int y = 0; y < 3; y++) {
        for (int x = 0; x < 3; x++) {
            if (board[y][x] == 0)
                return 0;
        }
    }
    return 1;
}

int isMoveValid(int board[3][3], const int row, const int col) {
    return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == 0;
}

void botMoveEasy(int board[3][3]) {
    int row, col;
    do {
        srand(time(0));
        row = rand() % 3;
        col = rand() % 3;
    } while (!isMoveValid(board, row, col));
    board[row][col] = 2;
}

int fib(const int n) {
    if (n <= 1)
        return n;
    return fib(n - 1) + fib(n - 2);
}

void botMoveHard(int board[3][3]) {
    // is there any winning move?
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (isMoveValid(board, i, j)) {
                board[i][j] = 2;
                if (checkWin(board) == 2)
                    return;
                board[i][j] = 0;
            }
        }
    }

    // Check any blocking move
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (isMoveValid(board, i, j)) {
                board[i][j] = 1;
                if (checkWin(board) == 1) {
                    board[i][j] = 2;
                    return;
                }
                board[i][j] = 0;
            }
        }
    }

    // Check center
    if (isMoveValid(board, 1, 1)) {
        board[1][1] = 2;
        return;
    }

    // Check corner
    constexpr int corners[4][2] = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
    for (int i = 0; i < 4; i++) {
        const int x = corners[i][0];
        const int y = corners[i][1];
        if (isMoveValid(board, x, y)) {
            board[x][y] = 2;
            return;
        }
    }

    botMoveEasy(board);
}

void handleGaming(int diff) {
    int board[3][3] = {0}; // 0 for empty, 1 for X, 2 for O
    int currentPlayer = 1; // Player 1 starts

    while (1) {
        printBoard(board);
        int winner = checkWin(board);
        if (winner) {
            printf("Player %d wins!\n", winner);
            waitForEnter();
            break;
        }
        if (isBoardFull(board)) {
            printf("It's a draw!\n");
            waitForEnter();
            break;
        }

        if (currentPlayer == 1) {
            int row, col;
            printf("Player %d's turn. Enter column (1-3) and row (1-3): ", currentPlayer);
            scanf("%d %d", &col, &row);
            getchar();
            row--;
            col--;

            if (isMoveValid(board, row, col)) {
                board[row][col] = currentPlayer;
                currentPlayer = 2;
            } else {
                printf("Invalid move! Try again.\n");
                waitForEnter();
                continue;
            }
        } else {
            if (diff == 1) {
                botMoveEasy(board);
            } else if (diff == 2) {
                botMoveHard(board);
            }
            currentPlayer = 1;
        }

        clrs();
    }
}

int main(void) {
    int state = 0; // 0 menu; 1 difficulty; 2 game;
    int difficulty = 0; // 0 = easy; 1 = hard;

    while (1) {
        clrs();
        switch (state) {
            case 0: {
                int option = 0;
                printMenu();
                scanf("%d", &option);
                getchar();
                if (option == 1) {
                    state = 1;
                } else if (option == 2) {
                    return 0;
                }
                continue;
            }
            case 1: {
                printDifficulty();
                scanf("%d", &difficulty);
                getchar();
                if (difficulty == 1 || difficulty == 2) {
                    state = 2;
                }
                continue;
            }
            case 2: {
                handleGaming(difficulty);
                state = 0;
            }
            default: ;
        }
    }
}
