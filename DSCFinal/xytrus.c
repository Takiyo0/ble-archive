#include <stdio.h>
#include <stdlib.h>
#include <conio.h>  // For getch() on Windows
#include <time.h>
#include <windows.h> // For Sleep()
#include <unistd.h>

#define ROWS 6
#define COLS 6
#define NUM_TILE_TYPES 4
#define MATCH_COUNT 3
#define CONSOLE_WIDTH 80
#define MAX_LENGTH 50

#define MAX_

char grid[ROWS][COLS];
char tileTypes[] = {'A', 'B', 'C', 'D'};

int cursorX = 0, cursorY = 0;
int isFirstTileSelected = 0;
int selectedX, selectedY;

void clearTerminal() {
#ifdef _WIN32
    system("cls");
#else
    system("clear");
#endif
}

typedef struct Player {
    char username[100];
    char hashedPassword[100];
    int highScore;
    int currentScore;
    int movesLeft;
    struct Player *prev;
    struct Player *next;
} Player;

Player *head = NULL;
Player *tail = NULL;
int point = 0;

void printAnimatedLine(const char *line, int delay) {
    while (*line) {
        putchar(*line++);
        fflush(stdout);
        usleep(delay);
    }
    putchar('\n');
}

void renderExit() {
    const char *art[] = {
        "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#SS%?***++++++++++***?%SS#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
        "@@@@@@@@@@@@@@@@@@@@@@@@@@@#%?*+++**??%?+;;;;;;;+++++++++++*?%#@@@@@@@@@@@@@@@@@@@@@@@@@@@",
        "@@@@@@@@@@@@@@@@@@@@@@@#%*+++*?%S#####S;yy+*?%SSS#######S%?*+++*%#@@@@@@@@@@@@@@@@@@@@@@@",
        "@@@@@@@@@@@@@@@@@@@@@S?*+*?%S##########*:y?S###################S%?*++?S@@@@@@@@@@@@@@@@@@@",
        "@@@@@@@@@@@@@@@@@@S?+yy:+?%############**S##############%S#########S%*++?S@@@@@@@@@@@@@@@@",
        "@@@@@@@@@@@@@@@%*++*???**+*?%S####%S##S###############S%##SS#########S%*+*%@@@@@@@@@@@@@@@",
        "@@@@@@@@@@@@@%++*S#########SSSS###SS##################SSS?*#############S*++%@@@@@@@@@@@@@",
        "@@@@@@@@@@@%++?S#####################################S%?+*S###############S?++%@@@@@@@@@@@",
        "@@@@@@@@@S*;*S######################################%*;;?##S################S*;*S@@@@@@@@@",
        "@@@@@@@@?++%################yyy##################S?+;;+%#S%###################%++?@@@@@@@@",
        "@@@@@@#*;*#################################SS%?*+;;;*%##%%#####################S*;*#@@@@@@",
        "@@@@@S++%#SS##########k#############SS%?**++;;;;+*?S##%?%########################?++S@@@@@",
        "@@@@S+;yy*?S####k####kk#####SS%?**++;;;;;;;++*?%S##S%*+%##########################%++S@@@@",
        "@@@S+;;+%######kk###kkk#S%*++;;;;;;;;;++**?%SS##SSSSSSSS%##########################%++S@@@",
        "@@#*;+%########kkSkkS##?+;;;;;;;;;;++*?%SS##SSSSSSSS%##############################%++#@@@",
        "@@?;*#########Skyk%##S+;;;;;;:;;++*%SS#############S###########################S%kk%kk+?@@",
        "@S++S#########%yk?##S+;+;;;::;++?%SS##############################################S*k;++S@",
        "@*;?##########?yk###*;++;:::;++?%%S#################################################%k+*@",
        "S++S##########yy%##S+;+;::::+;*%S###########S%%?*******?%%?**?SS%S###################S+++S",
        "?+*###########%y%##%;++;::,:;;+S##########%*+;;;;;;;;;;;;;+*++;++%####################%;+?",
        "*;?###########Skk##S;++;::,:;++S%S####SSS*;++++++++;;;;;:::;;++;;+?####################*+*",
        "+;%############ykS##+;+;::,::;;+%??S##S#SSS%%%%%?????*+;;;:,,:;;;;;?##kS###############%++",
        "+;%#############Sk##?;;;;;:,,:;;+*???%%SS#########S%????+;;::,::;+;;%##kk##############%++",
        "+;%##############SS##?;;;;;;:,::;;;+***?????????S#S##S??%++;::,::;+;*##SkS#############%++",
        "*;?###################?+;;+++;;;;;;;;;;;;;;;;;+%#SS####SS%;+;:,::;+;+###kk#############?+*",
        "?++S###################?**++***++;;;;;;;;;++?%###########S++;:,::;+;+###kkS############*+?",
        "S+;*##################%%###S%?%SS%%%??%%%SS#############S%++;:::;++;*###kkS###########S++S",
        "@*+;y################################################SS%?*+;:::;;+;+S##%ykS#####yy+y##?+*@",
        "@S+;;yS######################yy######################S%?++;::;;;+;;*##SkykS#######;:*%++S@",
        "@@?++yyyS###########################SS#####SS#####S%?*++;;:;;;;;;+%#Skyyk?########?;+++?@@",
        "@@#*+++yyy%########################%%S%%%SS###S%%?*++;;;;;;;;;;+?S#kyy#ykk#########*;++#@@",
        "@@@S++%##########################S**?%SS##S%??*++;;;;;;;;++**?S###yk###yk#########*;++#@@@",
        "@@@@S++%########################S**%###S?*++;;;;;;++**?%%S##############k########S+++S@@@@",
        "@@@@@S+;?######################S?%##S?++;;;+**?%%S###############################*;+S@@@@@",
        "@@@@@@#*;*S###################S%#S?*;;;+?%S###############yyy##################S*;*#@@@@@@",
        "@@@@@@@?++%#################SS#S*;;*%########################################%++?@@@@@@@@@",
        "@@@@@@@@@S*;*S#################%;+*%########################################S*;*S@@@@@@@@@@",
        "@@@@@@@@@@@%++?S##############?+?%S###########################y##########S%*++%@@@@@@@@@@@@",
        "@@@@@@@@@@@@%++*S###########%?SS%S##################S########yy?yy++++*++;+%@@@@@@@@@@@@@@",
        "@@@@@@@@@@@@@@@%*+*%S###########%%###################yy###########yy?*+;+*%@@@@@@@@@@@@@@@@",
        "@@@@@@@@@@@@@@@@@@S?++*%S########SS##############S#####S###########y*++?S@@@@@@@@@@@@@@@@@@",
        "@@@@@@@@@@@@@@@@@@@@@S?++*?%S##################S*+#############y?*++?S@@@@@@@@@@@@@@@@@@@@@",
        "@@@@@@@@@@@@@@@@@@@@@@@@#%*+++*?%S#########S%?+::?########yy?*+++*%#@@@@@@@@@@@@@@@@@@@@@@@",
        "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#%?*+++******+++;;;+?y%???**+++*?%#@@@@@@@@@@@@@@@@@@@@@@@@@@@",
        "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#SS%?***++++++++++***?%SS#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
        "                      Relentlessly Move Forward and Achieve Our Dream",
        "                                        24-1"
    };
    for (int i = 0; i < sizeof(art) / sizeof(art[0]); i++) {
        printAnimatedLine(art[i], 100);
    }
}

Player *player_get(const char *username) {
    Player *current = head;

    if (current == NULL) {
        printf("The player list is empty.\n");
        return NULL;
    }

    printf("Starting search for user: %s\n", username);

    while (current != NULL) {
        if (current->username != NULL) {
            printf("Checking player: %s\n", current->username);
            if (strcmp(current->username, username) == 0) {
                printf("Player found: %s\n", current->username);
                return current;
            }
        }
        current = current->next;
    }

    printf("Player not found: %s\n", username);
    return NULL;
}

Player *player_create(const char *username, const char *hashedPassword) {
    Player *newPlayer = (Player *) malloc(sizeof(Player));
    strcpy(newPlayer->username, username);
    strcpy(newPlayer->hashedPassword, hashedPassword);
    newPlayer->highScore = 0;
    newPlayer->prev = NULL;
    newPlayer->next = NULL;
    return newPlayer;
}

void player_save() {
    FILE *file = fopen("players.txt", "w");
    if (!file) {
        perror("Could not open players file");
        return;
    }
    Player *current = head;
    while (current != NULL) {
        fprintf(file, "%s#%s#%d\n", current->username, current->hashedPassword, current->highScore);
        current = current->next;
    }
    fclose(file);
}

Player *player_push_last(Player *player, int dontSave) {
    printf("Pushing");
    if (head == NULL) {
        head = player;
        tail = player;
    } else {
        tail->next = player;
        player->prev = tail;
        tail = player;
    }
    if (!dontSave) {
        player_save();
    }
    return player;
}

Player *player_restore() {
    FILE *file = fopen("players.txt", "r");
    if (!file) {
        perror("Could not open players file");
        return NULL;
    }

    char line[256];
    while (fgets(line, sizeof(line), file)) {
        Player *newPlayer = player_create("", "");

        char *token = strtok(line, "#");
        strcpy(newPlayer->username, token);
        token = strtok(NULL, "#");
        strcpy(newPlayer->hashedPassword, token);
        token = strtok(NULL, "#");
        newPlayer->highScore = atoi(token);
        newPlayer->next = NULL;
        printf("Saving %s with pw %s and high score of %d", newPlayer->username, newPlayer->hashedPassword,
               newPlayer->highScore);
        player_push_last(newPlayer, 1);
    }

    fclose(file);
    return head;
}

const char *hashPassword(char *password) {
    char *hashedPassword = (char *) malloc(strlen(password) + 1);
    int i;
    for (i = 0; i < strlen(password); i++) {
        if (password[i] >= 'a' && password[i] <= 'z') {
            hashedPassword[i] = password[i] + 10;
            if (hashedPassword[i] > 'z') {
                hashedPassword[i] -= 26;
            }
        }
    }
    hashedPassword[i] = '\0';
    return hashedPassword;
}

void waitForKey() {
    printf("\n\033[1;34mPress any key to continue...\033[0m");
    getch();
}

int isAuthenticated(const char *inputPassword, const char *storedHashedPassword) {
    char *hashedInput = strdup(inputPassword);
    hashPassword(hashedInput);
    const int result = strcmp(hashedInput, storedHashedPassword) == 0;
    free(hashedInput);
    return result;
}

void generateGrid() {
    srand(time(0));
    for (int i = 0; i < ROWS; i++) {
        for (int j = 0; j < COLS; j++) {
            grid[i][j] = tileTypes[rand() % NUM_TILE_TYPES];
        }
    }
}

void displayGrid(Player *player) {
    clearTerminal();
    printf("\033[1;34m");
    printf("  *************************************\n");
    printf("  *            %s's Grid            *\n", player->username);
    printf("  *************************************\n");
    printf("\033[0m");
    printf("  High Score: \033[1;33m%d\033[0m | Current Score: \033[1;34m%d\033[0m | Moves Left: \033[1;35m%d\033[0m\n",
           player->highScore, player->currentScore, player->movesLeft);
    printf("\033[1;32m");
    printf("  -------------------------------------\n");
    printf("\033[0m");
    for (int i = 0; i < ROWS; i++) {
        for (int j = 0; j < COLS; j++) {
            if (i == cursorY && j == cursorX) {
                printf("\033[1;33m[%c]\033[0m", grid[i][j]);
            } else if (isFirstTileSelected && i == selectedY && j == selectedX) {
                printf("\033[1;32m[%c]\033[0m", grid[i][j]);
            } else {
                printf(" %c ", grid[i][j]);
            }
        }
        printf("\n");
    }
    printf("\033[1;32m");
    printf("  -------------------------------------\n");
    printf("\033[0m");
}

void swapTiles(int r1, int c1, int r2, int c2) {
    char temp = grid[r1][c1];
    grid[r1][c1] = grid[r2][c2];
    grid[r2][c2] = temp;
}

int checkAndBreakMatches(Player *player) {
    int matchFound = 0;

    for (int i = 0; i < ROWS; i++) {
        for (int j = 0; j <= COLS - MATCH_COUNT; j++) {
            if (grid[i][j] == grid[i][j + 1] && grid[i][j] == grid[i][j + 2]) {
                grid[i][j] = grid[i][j + 1] = grid[i][j + 2] = ' ';
                matchFound = 1;
                player->currentScore += 10;
            }
        }
    }

    for (int j = 0; j < COLS; j++) {
        for (int i = 0; i <= ROWS - MATCH_COUNT; i++) {
            if (grid[i][j] == grid[i + 1][j] && grid[i][j] == grid[i + 2][j]) {
                grid[i][j] = grid[i + 1][j] = grid[i + 2][j] = ' ';
                matchFound = 1;
                player->currentScore += 10;
            }
        }
    }

    return matchFound;
}


void dropTilesWithAnimation(Player *player) {
    int hasDropped = 1;

    while (hasDropped) {
        hasDropped = 0;

        for (int j = 0; j < COLS; j++) {
            for (int i = ROWS - 1; i > 0; i--) {
                if (grid[i][j] == ' ' && grid[i - 1][j] != ' ') {
                    grid[i][j] = grid[i - 1][j];
                    grid[i - 1][j] = ' ';
                    hasDropped = 1;
                }
            }
        }
        displayGrid(player);
        Sleep(200);
    }

    for (int j = 0; j < COLS; j++) {
        for (int i = 0; i < ROWS; i++) {
            if (grid[i][j] == ' ') {
                grid[i][j] = tileTypes[rand() % NUM_TILE_TYPES];
            }
        }
    }

    displayGrid(player);
}

void processSwap(Player *player) {
    if (abs(cursorX - selectedX) + abs(cursorY - selectedY) == 1) {
        swapTiles(selectedY, selectedX, cursorY, cursorX);
        displayGrid(player);
        Sleep(500);

        if (checkAndBreakMatches(player)) {
            dropTilesWithAnimation(player);
        } else {
            swapTiles(selectedY, selectedX, cursorY, cursorX);
            printf("No match found. Reverting swap.\n");
            Sleep(500);
            displayGrid(player);
        }
    } else {
        printf("Tiles are not adjacent. Select again.\n");
        Sleep(500);
    }
}

void playGame(Player *player) {
    player->currentScore = 0;
    player->movesLeft = 15;
    generateGrid();
    displayGrid(player);

    while (player->movesLeft > 0) {
        char input = getch();

        if (input == 27) {
            printf("Bye! Press enter to go back\n");
            return;
        }

        switch (input) {
            case 'w':
                if (cursorY > 0) cursorY--;
                break;
            case 's':
                if (cursorY < ROWS - 1) cursorY++;
                break;
            case 'a':
                if (cursorX > 0) cursorX--;
                break;
            case 'd':
                if (cursorX < COLS - 1) cursorX++;
                break;
            case '\r':
                if (!isFirstTileSelected) {
                    selectedX = cursorX;
                    selectedY = cursorY;
                    isFirstTileSelected = 1;
                } else {
                    processSwap(player);
                    isFirstTileSelected = 0;
                    player->movesLeft--;
                }
                break;
            default:
                break;
        }

        displayGrid(player);
    }
    printf("\nGame Over! Your score: %d\n", player->currentScore);
    if (player->currentScore > player->highScore) {
        player->highScore = player->currentScore;
        printf("New high score: %d\n", player->highScore);
        player_save();
    }
    waitForKey();
}

void renderCenteredText(const char *text) {
    int textLen = strlen(text);
    int padding = (CONSOLE_WIDTH - textLen) / 2;
    printf("%*s%s\n", padding, "", text);
}

void displayLoginScreen(const char *username, const char *password, const char *errorMsg) {
    clearTerminal();
    printf("Please Fill In Your Credentials\n");
    printf("Input Your Username : %s\n", username);
    printf("Password : ");
    for (int i = 0; i < strlen(password); i++) {
        printf("*");
    }
    printf("\n\n");
    printf("Press esc to go back\n");

    if (errorMsg != NULL) {
        printf("\033[0;31m%s\033[0m\nPress enter to retry...", errorMsg);
        getch();
    }
}

void login(Player **player) {
    char username[MAX_LENGTH] = "";
    char password[MAX_LENGTH] = "";
    char ch;
    int i = 0, j = 0;
    int valid = 0;

    while (!valid) {
        displayLoginScreen(username, password, NULL);

        while (1) {
            ch = _getch();
            if (ch == 27) {
                return;
            }
            if (ch == '\r') {
                break;
            } else if (ch == '\b') {
                if (i > 0) username[--i] = '\0';
            } else if (i < MAX_LENGTH - 1) {
                username[i++] = ch;
                username[i] = '\0';
            }
            displayLoginScreen(username, password, NULL);
        }

        while (1) {
            ch = _getch();
            if (ch == 27) {
                return;
            }
            if (ch == '\r') {
                break;
            } else if (ch == '\b') {
                if (j > 0) password[--j] = '\0';
            } else if (j < MAX_LENGTH - 1) {
                password[j++] = ch;
                password[j] = '\0';
            }
            displayLoginScreen(username, password, NULL);
        }

        if (strlen(username) == 0 || strlen(password) == 0) {
            displayLoginScreen(username, password, "Both fields must be filled.");
        } else {
            valid = 1;
        }
    }
    *player = player_get(username);
    if (player == NULL) {
        displayLoginScreen("", "", "Username not found.");
    } else if (strcmp(hashPassword(password), (*player)->hashedPassword) == 1) {
        displayLoginScreen("", "", "Wrong password.");
    }
}

int isAlphanumeric(const char *str) {
    for (int i = 0; str[i] != '\0'; i++) {
        if (!((str[i] >= 'a' && str[i] <= 'z') || (str[i] >= 'A' && str[i] <= 'Z') || (
                  str[i] >= '0' && str[i] <= '9'))) {
            return 0;
        }
    }
    return 1;
}

void displayRegisterScreen(const char *username, const char *password, const char *errorMsg) {
    clearTerminal();
    printf("Please Register a New Account\n");
    printf("Input Your Username : %s\n", username);
    printf("Password : ");
    for (int i = 0; i < strlen(password); i++) {
        printf("*");
    }
    printf("\n\n");
    printf("Press esc to go back\n");

    if (errorMsg != NULL) {
        printf("\033[0;31m%s\033[0m\nPress enter to retry...", errorMsg);
        getch();
    }
}

void registerUser() {
    char username[MAX_LENGTH] = "";
    char password[MAX_LENGTH] = "";
    char ch;
    int i = 0, j = 0;

    while (1) {
        printf("Please Register a New Account\n");
        printf("Input Your Username: %s\n", username);
        printf("Password: ");
        for (int k = 0; k < strlen(password); k++) {
            printf("*");
        }
        printf("\n\nPress esc to go back\n");

        while (1) {
            ch = _getch();
            if (ch == 27) {
                return;
            }
            if (ch == '\r') {
                break;
            } else if (ch == '\b') {
                if (i > 0) username[--i] = '\0';
            } else if (i < MAX_LENGTH - 1 && isalnum(ch)) {
                username[i++] = ch;
                username[i] = '\0';
            }
            displayRegisterScreen(username, password, NULL);
        }

        while (1) {
            ch = _getch();
            if (ch == 27) {
                return;
            }
            if (ch == '\r') {
                break;
            } else if (ch == '\b') {
                if (j > 0) password[--j] = '\0';
            } else if (j < MAX_LENGTH - 1) {
                password[j++] = ch;
                password[j] = '\0';
            }
            displayRegisterScreen(username, password, NULL);
        }

        if (strlen(username) == 0 || strlen(password) == 0) {
            displayRegisterScreen("", "", "Both fields must be filled.");
        } else if (!isAlphanumeric(username)) {
            displayRegisterScreen("", "", "Username must be alphanumeric.");
        } else if (player_get(username)) {
            displayRegisterScreen("", "", "Username already exists.");
        } else {
            const char *hashedPassword = hashPassword(password);
            Player *player = player_create(username, hashedPassword);
            player_push_last(player, 0);
            printf("Registration successful! Welcome, %s!\n", username);
            Sleep(2000);
            free((char *) hashedPassword);
            break;
        }
    }
}

Player *sort_leaderboard(Player *head) {
    if (head == NULL || head->next == NULL) return head;

    Player *sorted = NULL;
    Player *current = head;

    while (current != NULL) {
        Player *next = current->next;
        if (sorted == NULL || current->highScore > sorted->highScore) {
            current->next = sorted;
            sorted = current;
        } else {
            Player *tmp = sorted;
            while (tmp->next != NULL && tmp->next->highScore > current->highScore) {
                tmp = tmp->next;
            }
            current->next = tmp->next;
            tmp->next = current;
        }
        current = next;
    }

    return sorted;
}

void displayLeaderboard() {
    Player *current = head;
    int rank = 1;
    printf("\033[1;34mHall of Fame\033[0m\n");
    printf("\033[1;32m=========================\033[0m\n");
    printf("\033[1;33mRank\033[0m\t\033[1;35mPlayer\033[0m\t\033[1;36mHigh Score\033[0m\n");
    while (current != NULL) {
        printf("\033[1;33m%d\033[0m\t\033[1;35m%s\033[0m\t\033[1;36m%d\033[0m\n", rank, current->username,
               current->highScore);
        current = current->next;
        rank++;
    }
    printf("\033[1;32m=========================\033[0m\n");
    waitForKey();
}

void loggedIn(Player *player) {
    int selected = 0;
    int state = 0; // 0 = main menu; 1 = play game; 2 = hall of fame; 3 = log out; 4 = exit;

    const char *menuItems[] = {
        "Play Game",
        "Hall of Fame",
        "Log Out",
        "Exit"
    };

    while (1) {
        clearTerminal();
        switch (state) {
            case 0: {
                char *helo = malloc(1024);
                char temp[1024];
                snprintf(temp, sizeof(temp), "%s", player->username);
                snprintf(helo, 1024, "\033[1;34mHello, \033[0m \033[1;32m%s\033[0m\n", temp);
                renderCenteredText(helo);
                free(helo);

                for (int i = 0; i < 4; i++) {
                    int itemLen = strlen(menuItems[i]);
                    int arrowLen = selected == i ? 6 : 0;
                    int totalLen = itemLen + arrowLen;
                    int padding = (CONSOLE_WIDTH - totalLen) / 2;

                    printf("%*s", padding, "");

                    if (selected == i) {
                        printf("\033[0;32m>> %s << \033[0m\n", menuItems[i]);
                    } else {
                        printf("%s\n", menuItems[i]);
                    }
                }

                char input = getch();
                if (input == 'w' && selected > 0) {
                    selected--;
                } else if (input == 's' && selected < 3) {
                    selected++;
                } else if (input == '\r' || input == '\n') {
                    state = selected + 1;
                }
                break;
            }

            case 1: {
                playGame(player);
                state = 0;
                break;
            }

            case 2: {
                displayLeaderboard();
                state = 0;
                break;
            }

            case 3: {
                return;
            }

            case 4: {
                renderExit();
                exit(0);
            }
        }
    }
}

int main() {
    player_restore();
    int state = 0; // 0 = home; 1 = login; 2 = register; 3 = exit;
    int selected = 0;
    while (1) {
        clearTerminal();
        switch (state) {
            case 0: {
                renderCenteredText("Welcome to XYtrus!\n");
                const char *menuItems[] = {
                    "Login",
                    "Register",
                    "Exit"
                };
                int menuItemCount = 3;

                for (int i = 0; i < menuItemCount; i++) {
                    int itemLen = strlen(menuItems[i]);
                    int arrowLen = selected == i ? 6 : 0;
                    int totalLen = itemLen + arrowLen;
                    int padding = (CONSOLE_WIDTH - totalLen) / 2;

                    printf("%*s", padding, "");

                    if (selected == i) {
                        printf("\033[0;32m>> %s << \033[0m\n", menuItems[i]);
                    } else {
                        printf("%s\n", menuItems[i]);
                    }
                }

                char input = getch();
                printf("%c\n", input);
                if (input == 'w' && selected > 0) {
                    selected--;
                } else if (input == 's' && selected < menuItemCount - 1) {
                    selected++;
                } else if (input == '\r') {
                    if (selected == 0) {
                        state = 1;
                    } else if (selected == 1) {
                        state = 2;
                    } else if (selected == 2) {
                        state = 3;
                    }
                }
                break;
            }

            case 1: {
                Player *player = NULL;
                login(&player);
                if (player != NULL) {
                    loggedIn(player);
                }
                state = 0;
                break;
            }
            case 2: {
                registerUser();
                state = 0;
                break;
            }
            case 3: {
                renderExit();
                return 0;
            }
        }
    }
}
