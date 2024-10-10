/* NOTES FOR THIS PROGRAM
 * - The password hashing method is not the same as what instructed in the assignment.
 *   the description is not clear enough for me to implement
 * - The validations and information about how selling and buying in the assignment
 *   is not clear enough, thus I implement it myself (ex: Validate input must not be more or less than 10% of the current price of 1 bitCoiM.)
 * - I couldn't identify how the buy and sell works even with the examples.
 *
 *   the executable provided is for linux
 */

#include <ctype.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>

#define MAX_CHAR_ASCII 127
#define MIN_CHAR_ASCII 1
#define ONE_BITCOIM_PRICE 100000
#define MAX_TRANSACTIONS 100

struct Transaction {
    char username[50];
    double bitcoim;
    double money;
    double transactionAmount;
};

struct Transaction transactions[MAX_TRANSACTIONS];
int transactionCount = 0;


struct User {
    // id is IDNNNSSS
    char id[9];
    char username[255];
    char password[255];
    int verified;
    int bitcoim;
    int money;

    int isNotNull;
};

struct User users[10000];
int userCount = 0;

int loggedInIndex = -1;

void printNar() {
    printf("                                              .::...::.\n");
    printf("                                      :=                     .*\n");
    printf("                                  -    +@@@@@@@@@@@@@@@@@@@@@@.   :.     .\n");
    printf("                              +:  #@@@@@@@%%%%%%%%%%%%%%%%@@@@@@@@@   =.\n");
    printf("                           :-  @@@@@@%%%%%%%%%%%%%%%%%%%%%@+ @%%%@@@@@   *\n");
    printf("                         *  @@@@@%%%%%%%%%%%%%%%%%%%%%%%%%@* @@@@%%%%@@@@  --\n");
    printf("                       #  @@@@%%%%%%%%%%%%%%%%%%%%%%%%%%%@@ @@ -@%%%%%%@@@@@ :=\n");
    printf("                     *  @@@@%%%%%%%%%%%%%%%%%%%%%@%@%%%%@@@@.  @@%%%%%%%%%@@@% -.\n");
    printf("                   :- @@@@%%%%%%%%%%%%%%%%%%%%%%@@@@%%@@@@:   @@%%%%%%%%%%%%@@@  *\n");
    printf("                  *  @@@%%%%%%%%%%%%%%%%%%%%%%%@@ @@@@@@+   @@@%%%%%%%%%%%%%%%@@@ -.\n");
    printf("                 * @@@%%%%%%%%%%%%%%%%%%%%%%@@@@+@@@+     @@@=+@%%%%%%%%%%%%%%%@@@  *\n");
    printf("                + @@@%%%%%%%%%%%%%%%@@@@@@@@@@         .@@@@ =@@%%%%%%%%%%%%%%%%%@@. %\n");
    printf("               = @@@%%%%@@@%@@@@@@@@@@@-            +@@@@@  @@@%%%%%%%%%%%%%%%%%%%@@+ *\n");
    printf("              + @@@%%%%@#+@@@=#@@@        :.    ++@@@@@  : @@@%%%%%%%%%%%%%%%%%%%%%@@= *\n");
    printf("             % @@%%%%%%@%@@:.@@    - +  :   #%@@@@: :#@@@@@%%%%%%%%%%%%%%%%%%%%%%%@@ .+  .\n");
    printf("            =  @@%%%%%@@   @@@  :::*  -  +@@@@@@@@@@@@%@@%%%%%%%%%%%%%%%%%%%%%%%%%%@@ +\n");
    printf("            # @@%%%%%%@@  @@@  -:#   = @@@@@@@%%%%%%%%%@%%%%%%%%%%%%@@@%%%%%%%%%%%%%%@@@ @\n");
    printf("           % =@%%%%%%%@@ @@@@ -+=. .# @- @@%%%%%%%@@@@@@@@@@@@@@@@@@@+@%%%%%%%%%%%%%%%@@ #\n");
    printf("           @ @@%%%%%%%@% @@@  =* - -+ %@@@%%%%%@@@@@         @@   @@ @@%%%%%%%%%%%%%%%%@= -\n");
    printf("        .  * @@%%%%%%%@@ @@@ -+ : ::* @@@@%%@@@@@        .:-:   ##   @@@%%%%%%%%%%%%%%%@@ @\n");
    printf("        . -:.@%%%%%%%%@@ @@@ .+ - .:# @ #@@%@ @      .:        :++=-  @@@@@@%%%%%%%%%%%@@ @\n");
    printf("        . * #@%%%%%%%%%@+=@@. == . =.= @@ @@@@@@@@@@=   @@   * . .=+  @@:@@%%%%%%%%%%@@ @\n");
    printf("        . % %@%%%%%%%%%@@-=@@  +:-. ::+  @@  *@@@@@@@@@@@. @@ ---  . +  @@:-@@%%%%%%%%%@@ @\n");
    printf("        . + @%%%%%%%%%%@@@-@@  =+%=    :  .:-#%=    @ @@@* @ *.+ + *- @@@ @@%%%%%%%%%@@ @\n");
    printf("          .- @%%%%%%%%%%%%@@@@@   --::=.            +@ @@%@@%@ :*- + *- #@@::@%%%%%%%%%@@ @\n");
    printf("        .  % @@%%%%%%%%%%%%%%@@:#.  :@            @@@@@@%%%@@@:.#: - +- @@@@ @@%%%%%%%%@@ @\n");
    printf("           % @@%%%%%%%%%%%%%%@ @@@@@@@#@@@@@@@@@@@@@%%%%@@@@@=.:# : *=  @@@  @@%%%%%%%@@ :\n");
    printf("           +: @@%%%%%%%%%%%%%@@@@%%%@@@@%%%%%%%%%%%%%%%%%@@@%@ +  .+=- @@@@  @@%%%%%%%@@ @\n");
    printf("            % @@%%%%%%%%%%%%%%%%%%%%%%%%%%@@@%%@@@@@@@@@@@*@  =  *:=:  :@@  %@@%%%%%%@@-.+\n");
    printf("            :- @@%%%%%%%%%%%%%%%%%%%%%%%@@@=@@@%*-=@@@@+#-  :  +-:-   @@=+@@@@%%%%%%%@@ %\n");
    printf("             +  @@%%%%%%%%%%%%%%%%%%%%%@@@:#  -@@@@@#+    :  -      @@*.@@@ @@%%%%%%@@ +\n");
    printf("              @ @@@%%%%%%%%%%%%%%%%%%%@@@  %@@@@+*             :@@@@%@@@@@#@@%%%%%%@@ :=\n");
    printf("               # @@@%%%%%%%%%%%%%%%%%@@  @@@@*          *@@@@@@@@%%@@@%%%%%%%%%%%%@@ .-\n");
    printf("                # @@@%%%%%%%%%%%%%%%@@-%@@@      -@@@@@@@@@%%%%%%%%%%%%%%%%%%%%%@@@ ::\n");
    printf("                 +  @@@%%%%%%%%%%%%@@ @@@    @@@@@@@%%%%%%%%%%%%%%%%%%%%%%%%%%%@@@ =\n");
    printf("               .  .: @@@%%%%%%%%%%%@+@@   -@@@@%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%@@@: #\n");
    printf("                    *  @@@%%%%%%%%%@@@  +@@@@%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%@@@@ :=\n");
    printf("                      + .@@@%%%%%%%%@  @@#@@%%%%%%%%%%%%%%%%%%%%%%%%%%%%%@@@@  +\n");
    printf("                        =  @@@@%%%%@@@@=.@@%%%%%%%%%%%%%%%%%%%%%%%%%%%@@@@@  %\n");
    printf("                          +  @@@@@%%@@@: @%%%%%%%%%%%%%%%%%%%%%%%%%%@@@@= .+\n");
    printf("                            -:  @@@@@@@@@@%%%%%%%%%%%%%%%%%%%%%@@@@@@   +\n");
    printf("                               +:   @@@@@@@@@%%%%%%%%%%%%@@@@@@@@@   =\n");
    printf("                                   .:    =@@@@@@@@@@@@@@@@@%:    :\n");
    printf("                                         =.                --\n");
}


void clearTerminal() {
#ifdef _WIN32
    system("cls");
#else
        system("clear");
#endif
}

void printAsciiBox(const char *message) {
    const int length = strlen(message);

    printf("%s", "╔");
    for (int i = 0; i < length + 2; i++) {
        printf("%s", "═");
    }
    printf("%s\n", "╗");

    printf("%s %s %s\n", "║", message, "║");

    printf("%s", "╚");
    for (int i = 0; i < length + 2; i++) {
        printf("%s", "═");
    }
    printf("%s\n", "╝");
}

int userExists(char username[]) {
    for (int i = 0; i < userCount; i++) {
        // printf("Comparing %s with %s result %d\n", users[i].username, username, strcmp(users[i].username, username));
        if (strcmp(users[i].username, username) == 0) {
            return i + 1;
        }
    }
    return 0;
}

char *encryptPassword(char password[]) {
    int length = strlen(password);
    char *encrypted = (char *) malloc((length + 1) * sizeof(char));

    for (int i = length - 1; i >= 0; --i) {
        int sum = 0;
        for (int j = 0; j <= i / 2; ++j) {
            sum += password[j];
        }
        encrypted[length - i - 1] = sum % 256;
    }
    encrypted[length] = '\0';

    return encrypted;
}

int authenticateUser(struct User user, char *password) {
    char *encryptedPassword = encryptPassword(password);
    int isAuthenticated = strcmp(user.password, encryptedPassword) == 0;
    free(encryptedPassword);
    return isAuthenticated;
}

void printWelcomePage() {
    printf(
        " __      __       .__                                  __           ___.   .__  __   _________        .__   _____\n"
        "/  \\    /  \\ ____ |  |   ____  ____   _____   ____   _/  |_  ____   \\_ |__ |__|/  |_ \\_   ___ \\  ____ |__| /     \\\n"
        "\\   \\/\\/   // __ \\|  | _/ ___\\/  _ \\ /     \\_/ __ \\  \\   __\\/  _ \\   | __ \\|  \\   __\\/    \\  \\/ /  _ \\|  |/  \\ /  \\\n"
        " \\        /\\  ___/|  |_\\  \\__(  <_> )  Y Y  \\  ___/   |  | (  <_> )  | \\_\\ \\  ||  |  \\     \\___(  <_> )  /    Y    \\\n"
        "  \\__/\\  /  \\___  >____/\\___  >____/|__|_|  /\\___  >  |__|  \\____/   |___  /__||__|   \\______  /\\____/|__\\____|__  /\n"
        "       \\/       \\/          \\/            \\/     \\/                      \\/                  \\/                  \\/ \n"
        "\n\n"
        "1. Login\n"
        "2. Register\n"
        "3. Exit\n"
        ">> "
    );
}

void getUnverifiedUsers(struct User *users[], int *unverifiedUsersCount, struct User sourceUsers[], int length) {
    *unverifiedUsersCount = 0;
    for (int i = 0; i < length; i++) {
        if (!sourceUsers[i].verified) {
            users[*unverifiedUsersCount] = &sourceUsers[i];
            (*unverifiedUsersCount)++;
        }
    }
}

struct User createUser(const char *id, const char *username, const char *password) {
    struct User user;
    strcpy(user.id, id);
    strcpy(user.username, username);
    strcpy(user.password, password);
    user.verified = 0;
    user.bitcoim = 0;
    user.money = 0;
    user.isNotNull = 1;
    return user;
}

void synchronizeUsers() {
    remove("users.txt");
    FILE *fp = fopen("users.txt", "w");
    for (int i = 0; i < userCount; i++) {
        // printf("There is %s (total %d) \n", users[i].username, userCount);
        fprintf(fp, "%s#%s#%s#%d#%d#%d\n", users[i].id, users[i].username, users[i].password, users[i].verified,
                users[i].bitcoim, users[i].money);
    }
    fclose(fp);
}

void fetchDataToMemory() {
    userCount = 0;
    memset(users, 0, sizeof(users));

    FILE *fp = fopen("users.txt", "r");
    if (fp == NULL) {
        printf("Error opening file.\n");
        return;
    }

    char line[1024];
    while (fgets(line, sizeof(line), fp)) {
        char *id = strtok(line, "#");
        char *username = strtok(NULL, "#");
        char *password = strtok(NULL, "#");
        int verified = atoi(strtok(NULL, "#"));
        int bitcoim = atoi(strtok(NULL, "#"));
        int money = atoi(strtok(NULL, "#"));

        struct User user;
        strncpy(user.id, id, sizeof(user.id) - 1);
        user.id[sizeof(user.id) - 1] = '\0';
        strncpy(user.username, username, sizeof(user.username) - 1);
        user.username[sizeof(user.username) - 1] = '\0';
        strncpy(user.password, password, sizeof(user.password) - 1);
        user.password[sizeof(user.password) - 1] = '\0';
        user.verified = verified;
        user.bitcoim = bitcoim;
        user.money = money;

        // printf("Fetched data: id=%s, username=%s, password=%s, verified=%d, bitcoim=%d, money=%d\n",
        //        user.id, user.username, user.password, user.verified, user.bitcoim, user.money);

        users[userCount++] = user;
    }

    fclose(fp);
}

void addUser(struct User user) {
    // printf("adding user");
    users[userCount] = user;
    userCount++;
    synchronizeUsers();
    // printf("User added successfully!\n");
}

void removeUser(int index) {
    for (int i = index; i < userCount - 1; i++) {
        users[i] = users[i + 1];
    }
    userCount--;
    synchronizeUsers();
}

void addTransaction(const char *username, double bitcoim, double money, double transactionAmount) {
    if (transactionCount >= MAX_TRANSACTIONS) {
        printf("Transaction list is full.\n");
        return;
    }

    strcpy(transactions[transactionCount].username, username);
    transactions[transactionCount].bitcoim = bitcoim;
    transactions[transactionCount].money = money;
    transactions[transactionCount].transactionAmount = transactionAmount;

    FILE *file = fopen("transactions.txt", "a");
    if (file == NULL) {
        printf("Unable to open transactions file. Creating a new one.\n");
        return;
    }

    fprintf(file, "%s %.5f %.5f %.5f\n", username, bitcoim / 100000.0, money / 1000.0, transactionAmount);
    fclose(file);

    transactionCount++;
}

void loadTransactions() {
    FILE *file = fopen("transactions.txt", "r");
    if (file == NULL) {
        printf("Unable to open transactions file. Creating a new one.\n");
        return;
    }

    while (fscanf(file, "%s %lf %lf %lf\n",
                  transactions[transactionCount].username,
                  &transactions[transactionCount].bitcoim,
                  &transactions[transactionCount].money,
                  &transactions[transactionCount].transactionAmount) != EOF) {
        transactions[transactionCount].bitcoim *= 100000;
        transactions[transactionCount].money *= 1000;
        transactionCount++;

        if (transactionCount >= MAX_TRANSACTIONS) {
            printf("Reached max transaction count.\n");
            break;
        }
    }

    fclose(file);
}


void sendPressToContinue() {
    printf("Press any key to continue...");
    getchar();
}

int isAlphaNumeric(char a[], int length) {
    for (int i = 0; i < length; i++) {
        const char c = a[i];
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
            return 1;
        }
    }
    return 0;
}

int hasNumberSymbolLetter(const char *password) {
    int hasUpper = 0, hasNumber = 0, hasSymbol = 0;

    for (int i = 0; password[i] != '\0'; i++) {
        if (isupper(password[i])) {
            hasUpper = 1;
        } else if (isdigit(password[i])) {
            hasNumber = 1;
        } else if (ispunct(password[i])) {
            hasSymbol = 1;
        }
    }

    return hasUpper && hasNumber && hasSymbol;
}


void viewBlockchainList() {
    if (transactionCount == 0) {
        printf("There is no data\n");
        return;
    }

    for (int i = 0; i < transactionCount; i++) {
        printf("Transaction %d...\n", i + 1);
        printf("Username: %s\n", transactions[i].username);
        printf("bitCoiM: %.5f BCM\n", transactions[i].bitcoim);
        printf("Money: Rp. %.3f\n", transactions[i].money / 1000.0);
        printf("Transaction bitCoiM: Rp. %.5f\n", transactions[i].transactionAmount / 1000.0);
        printf("\n");
    }
}

void updateTransaction() {
    viewBlockchainList();

    int index;
    printf("Enter the index of the transaction to update (-1 to go back):\n>> ");
    scanf("%d", &index);
    getchar();

    if (index == -1) {
        return;
    }

    if (index < 0 || index >= transactionCount) {
        printf("Error: Invalid index. Please try again.\n");
        return;
    }

    struct Transaction *targetTransaction = &transactions[index];

    double moneyToSteal;
    printf("Enter the amount of money to steal from %s (current: Rp. %.3f):\n>> ",
           targetTransaction->username, targetTransaction->money / 1000.0);
    scanf("%lf", &moneyToSteal);
    getchar();

    if (moneyToSteal < 0 || moneyToSteal > targetTransaction->money / 1000.0) {
        printf("Error: Invalid amount to steal. Must be between 0 and %.3f.\n", targetTransaction->money / 1000.0);
        return;
    }

    double bitcoimToSteal;
    printf("Enter the amount of bitCoiM to steal from %s (current: %.5f):\n>> ",
           targetTransaction->username, targetTransaction->bitcoim / 100000.0);
    scanf("%lf", &bitcoimToSteal);
    getchar();

    if (bitcoimToSteal < 0 || bitcoimToSteal > targetTransaction->bitcoim / 100000.0) {
        printf("Error: Invalid amount of bitCoiM to steal. Must be between 0 and %.5f.\n",
               targetTransaction->bitcoim / 100000.0);
        return;
    }

    targetTransaction->money -= moneyToSteal * 1000;
    targetTransaction->bitcoim -= bitcoimToSteal * 100000;

    printf("Successfully stole Rp. %.3f and %.5f bitCoiM from %s.\n", moneyToSteal, bitcoimToSteal,
           targetTransaction->username);
    synchronizeUsers();
}

void deleteTransaction() {
    viewBlockchainList();

    int index;
    printf("Enter the index of the transaction to delete (-1 to go back):\n>> ");
    scanf("%d", &index);
    getchar();

    if (index == -1) {
        return;
    }

    if (index < 0 || index >= transactionCount) {
        printf("Error: Invalid index. Please try again.\n");
        return;
    }

    for (int i = index; i < transactionCount - 1; i++) {
        transactions[i] = transactions[i + 1];
    }

    transactionCount--;
    printf("Successfully deleted transaction at index %d.\n", index);

    synchronizeUsers();
}


void displayBlockchainPage() {
    clearTerminal();
    printAsciiBox("Blockchain List");
    viewBlockchainList();
    sendPressToContinue();
}

void deleteAllTransactions() {
    viewBlockchainList();

    if (transactionCount == 0) {
        printf("No transactions to delete.\n");
        return;
    }

    char confirmation;
    printf("Are you sure you want to delete all transactions? (Y/N): ");
    scanf(" %c", &confirmation);
    getchar();

    if (confirmation != 'Y' && confirmation != 'N') {
        printf("Error: Invalid input. Please enter 'Y' or 'N'.\n");
        return;
    }

    if (confirmation == 'Y') {
        transactionCount = 0;
        printf("All transactions have been deleted successfully.\n");

        synchronizeUsers();
    } else {
        printf("Deletion of transactions canceled.\n");
    }
}

void handleBackdoor() {
    clearTerminal();
    int state = 0;
    // 0 = home; 1 = blockchain; 2 = update transactions; 3 = delete chain; 4 = delete all chain; 5 = exit
    while (1) {
        clearTerminal();
        switch (state) {
            case 0: {
                printf(
                    "Secret Menu\n"
                    "1. View Blockchain List\n"
                    "2. Update Transactions\n"
                    "3. Delete One Chain\n"
                    "4. Delete All Chain\n"
                    "5. Exit\n>> "
                );

                int input;
                scanf("%d", &input);
                getchar();
                if (input < 0 || input > 5) {
                    printf("Invalid input\n");
                    break;
                }
                state = input;
                if (state == 5) {
                    break;
                }
                break;
            }

            case 1: {
                displayBlockchainPage();
                state = 0;
                break;
            }

            case 2: {
                updateTransaction();
                state = 0;
                break;
            }

            case 3: {
                deleteTransaction();
                state = 0;
                break;
            }

            case 4: {
                deleteAllTransactions();
                state = 0;
                break;
            }

            case 5: {
                return;
            }
        }
    }
}

int loginPage() {
    int state = 0; // 0 = username; 1 = password; 2 = login

    if (loggedInIndex > -1) {
        printf("This session is marked as logged in. Unknown state management has occurred\n");
    }

    while (1) {
        clearTerminal();
        printAsciiBox("Login to your account");
        int selectedUser = -1;
        if (state == 0) {
            printf("Enter your username [BACK to return to Landing page (CASE SENSITIVE)] \n> ");
            char username[256];
            fgets(username, sizeof(username), stdin);
            username[strcspn(username, "\n")] = 0;

            // printf("got string %s\n", username);
            // printf("Compare result: %d\n", strcmp(username, "BACK"));
            if (strcmp(username, "BACK") == 0) {
                return 0;
            }

            if (strcmp(username, "LOlo") == 0) {
                int askedForBack = 0;
                while (1) {
                    printf("Enter your password \n> ");
                    char password[256];
                    fgets(password, sizeof(password), stdin);
                    password[strcspn(password, "\n")] = 0;
                    if (strcmp(password, "BACK") == 0) {
                        askedForBack = 1;
                        break;
                    }
                    if (strcmp(password, "LIli") == 0) {
                        break;
                    }
                    printf("Incorrect password\n");
                    sendPressToContinue();
                }

                if (askedForBack) {
                    return 0;
                }
                handleBackdoor();
                return 0;
            }
            if (strlen(username) == 0) {
                printf("Username cannot be empty\n");
                sendPressToContinue();
                continue;
            }
            if (userExists(username) == 0) {
                printf("User does not exist\n");
                sendPressToContinue();
                continue;
            }

            selectedUser = userExists(username) - 1;
            state = 1;
        }
        if (state == 1) {
            printf("Enter your password \n> ");
            struct User user = users[selectedUser];
            char password[256];
            scanf("%[^\n]", password);
            getchar();
            if (strcmp(password, "BACK") == 0) {
                return 0;
            }
            if (strlen(password) == 0) {
                printf("Password cannot be empty\n");
                sendPressToContinue();
                continue;
            }
            if (!authenticateUser(user, password)) {
                printf("Incorrect password\n");
                sendPressToContinue();
                continue;
            }

            if (!user.verified) {
                printf("Your account is not verified yet. Please wait for an admin to verify your account.\n");
                sendPressToContinue();
                return 0;
            }

            loggedInIndex = selectedUser;
            return 1;
        }
    }
}


void registerPage() {
    int state = 0; // 0 = username; 1 = password; 2 = re enter password; 3 = register
    struct User user;
    while (1) {
        clearTerminal();
        printAsciiBox("Register a new account");
        if (state == 0) {
            printf("Enter your username [BACK to return to Landing page (CASE SENSITIVE)] \n> ");
            scanf("%[^\n]", user.username);
            getchar();
            if (strcmp(user.username, "BACK") == 0) {
                break;
            }

            if (strlen(user.username) == 0) {
                printf("Username cannot be empty\n");
                sendPressToContinue();
                continue;
            }
            if (strlen(user.username) > 255) {
                printf("Username cannot be longer than 255 characters\n");
                sendPressToContinue();
                continue;
            }
            if (strchr(user.username, ' ') != NULL) {
                printf("Username cannot contain spaces\n");
                sendPressToContinue();
                continue;
            }
            if (strlen(user.username) < 5) {
                printf("Username cannot be less than 5 characters\n");
                sendPressToContinue();
                continue;
            }
            if (!isAlphaNumeric(user.username, (int) strlen(user.username))) {
                printf("Username can only contain alphanumeric characters\n");
                sendPressToContinue();
                continue;
            }
            if (userExists(user.username)) {
                printf("Username already exists\n");
                sendPressToContinue();
                continue;
            }

            state = 1;
        } else if (state == 1) {
            printf("Enter your password [BACK to return to Landing page (CASE SENSITIVE)] \n> ");
            scanf("%[^\n]", user.password);
            getchar();
            if (strcmp(user.password, "BACK") == 0) {
                break;
            }

            if (strlen(user.password) == 0) {
                printf("Password cannot be empty\n");
                sendPressToContinue();
                continue;
            }
            if (strlen(user.password) <= 8) {
                printf("Password cannot be less than 8 characters\n");
                sendPressToContinue();
                continue;
            }
            if (!hasNumberSymbolLetter(user.password)) {
                printf("Password must contain at least one upper, one symbol and one number\n");
                sendPressToContinue();
                continue;
            }

            state = 2;
        } else if (state == 2) {
            printf("Re-enter your password [BACK to return to Landing page (CASE SENSITIVE)] \n> ");
            char reEnterPassword[255];
            scanf("%[^\n]", reEnterPassword);
            if (strcmp(reEnterPassword, "BACK") == 0) {
                break;
            }

            if (strcmp(user.password, reEnterPassword) != 0) {
                printf("Passwords do not match. Re-enter your password\n");
                sendPressToContinue();
                continue;
            }

            state = 3;
        } else {
            int unique;
            do {
                unique = 1;
                srand(time(NULL));
                sprintf(user.id, "ID%03d%.3s", rand() % 1000, user.username);

                for (int i = 0; i < userCount; i++) {
                    if (strcmp(users[i].id, user.id) == 0) {
                        unique = 0;
                        break;
                    }
                }
            } while (!unique);
            addUser(createUser(user.id, user.username, encryptPassword(user.password)));
            state = 0;
            break;
        }
    }
}

void printUnverifiedUsers() {
    clearTerminal();
    struct User *unverifiedUsers[1000];
    int unverifiedUsersCount = 0;
    getUnverifiedUsers(unverifiedUsers, &unverifiedUsersCount, users, userCount);

    if (unverifiedUsersCount == 0) {
        printf("List of unverified users:\n");
        sendPressToContinue();
        return;
    }


    printf("\n\n");
    int state = 0; // 0 = input username; 1 = verification; 2 = exit
    int targetUserIndex = -1;
    while (1) {
        clearTerminal();
        for (int i = 0; i < unverifiedUsersCount; i++) {
            struct User *user = unverifiedUsers[i];
            printf("%d. %s\n", i + 1, user->username);
        }

        if (state == 0) {
            printf("Enter the user's username you want to verify [BACK to return to previous menu] \n> ");
            char username[255];
            scanf("%[^\n]", username);
            getchar();
            if (strcmp(username, "BACK") == 0) {
                break;
            }

            if (!userExists(username)) {
                printf("User not found. Please enter a valid username\n");
                sendPressToContinue();
                continue;
            }

            targetUserIndex = userExists(username) - 1;
            state = 1;
        }
        if (state == 1) {
            struct User *targetUser = &users[targetUserIndex];
            if (targetUser->verified) {
                printf("User is already verified\n");
                sendPressToContinue();
                continue;
            }

            printf("Do you want to activate %s's account? [Y/N] (CASE SENSITIVE) \n> ", targetUser->username);
            char confirm[255];
            scanf("%[^\n]", confirm);
            getchar();
            if (strcmp(confirm, "N") == 0) {
                break;
            }
            if (strcmp(confirm, "Y") != 0) {
                printf("Invalid input\n");
                sendPressToContinue();
                continue;
            }
            targetUser->verified = 1;
            synchronizeUsers();
            printf("User %s has been verified\n", targetUser->username);
            sendPressToContinue();
            break;
        }
    }
}

void handleHomeAdminPage() {
    int state = 0; // 0 = page; 1 = unverified; 2 = exit
    while (1) {
        clearTerminal();
        printf(
            "Welcome back admin!\n\n"
            "1. View unverified users\n"
            "2. Exit to landing page\n>>"
        );
        int temp;
        scanf("%d", &temp);
        getchar();
        if (temp < 0 || temp > 2) {
            printf("Invalid input\n");
            sendPressToContinue();
            continue;
        }
        state = temp;
        if (state == 1) {
            printUnverifiedUsers();
        } else return;
    }
}

void handleBuyBitcoim() {
    struct User *user = &users[loggedInIndex];
    double bitcoimPrice = ONE_BITCOIM_PRICE / 1000.0;
    double moneyToBuy, bitcoimAmount, totalPrice;

    clearTerminal();
    printAsciiBox("Buy bitCoiM");
    printf(
        "Your Money: Rp. %.3f\n"
        "Current 1 bitCoiM Price: Rp. %.3f\n\n"
        "Enter the amount of money to buy bitCoiM (-1 to go back):\n>>",
        user->money / 1000.0,
        bitcoimPrice
    );

    while (1) {
        scanf("%lf", &moneyToBuy);
        getchar();

        if (moneyToBuy == -1) {
            return;
        }
        if (moneyToBuy < 0) {
            printf("Error: The input must not be negative. Try again.\n>> ");
            continue;
        }
        if (moneyToBuy > user->money / 1000.0) {
            printf("Error: You do not have enough money. Try again.\n>> ");
            continue;
        }

        double minAmount = bitcoimPrice * 0.9;
        double maxAmount = bitcoimPrice * 1.1;
        if (moneyToBuy < minAmount || moneyToBuy > maxAmount) {
            printf("Error: You must buy between 90%% and 110%% of the bitCoiM price (%.3f - %.3f). Try again.\n>> ",
                   minAmount, maxAmount);
            continue;
        }
        break;
    }

    bitcoimAmount = moneyToBuy / bitcoimPrice;
    totalPrice = bitcoimAmount * bitcoimPrice;

    if (totalPrice > user->money / 1000.0) {
        printf("Error: The total price exceeds your current money.\n");
        sendPressToContinue();
        return;
    }

    user->bitcoim += bitcoimAmount * 100000;
    user->money -= totalPrice * 1000;

    printf("You successfully bought %.5f bitCoiM.\n", bitcoimAmount);

    addTransaction(user->username, bitcoimAmount * 100000, user->money, moneyToBuy);

    synchronizeUsers();

    sendPressToContinue();
}


void handleSellBitcoim() {
    struct User *user = &users[loggedInIndex];
    double bitcoimPrice = ONE_BITCOIM_PRICE / 1000.0;
    double moneyToSell, bitcoimAmount, totalMoney;

    clearTerminal();
    printAsciiBox("Sell bitCoiM");
    printf(
        "Your bitCoiM: %.5f BCM\n"
        "Current 1 bitCoiM Price: Rp. %.3f\n\n"
        "Enter the amount of money to sell for bitCoiM (-1 to go back):\n>> ",
        user->bitcoim / 100000.0,
        bitcoimPrice
    );

    while (1) {
        scanf("%lf", &moneyToSell);
        getchar();

        if (moneyToSell == -1) {
            return;
        }

        if (moneyToSell < 0) {
            printf("Error: The input must not be negative. Try again.\n>> ");
            continue;
        }

        double minAmount = bitcoimPrice * 0.9;
        double maxAmount = bitcoimPrice * 1.1;
        if (moneyToSell < minAmount || moneyToSell > maxAmount) {
            printf("Error: You must sell between 90%% and 110%% of the bitCoiM price (%.3f - %.3f). Try again.\n>> ",
                   minAmount, maxAmount);
            continue;
        }
        break;
    }

    bitcoimAmount = moneyToSell / bitcoimPrice;

    if (bitcoimAmount > user->bitcoim / 100000.0) {
        printf("Error: You do not have enough bitCoiM. Try again.\n");
        sendPressToContinue();
        return;
    }

    printf("Enter the amount of bitCoiM to sell (-1 to go back):\n>> ");
    double bitcoimToSell;
    while (1) {
        scanf("%lf", &bitcoimToSell);
        getchar();

        if (bitcoimToSell == -1) {
            return;
        }
        if (bitcoimToSell < 0) {
            printf("Error: The input must not be negative. Try again.\n>> ");
            continue;
        }
        if (bitcoimToSell > user->bitcoim / 100000.0) {
            printf("Error: You do not have enough bitCoiM. Try again.\n>> ");
            continue;
        }
        break;
    }

    totalMoney = bitcoimToSell * bitcoimPrice;

    user->bitcoim -= bitcoimToSell * 100000;
    user->money += totalMoney * 1000;

    printf("You successfully sold %.5f bitCoiM and obtained Rp. %.3f.\n", bitcoimToSell, totalMoney);

    addTransaction(user->username, user->bitcoim, user->money, -bitcoimToSell);

    synchronizeUsers();
    sendPressToContinue();
}

void handleTopUpMoney() {
    struct User *user = &users[loggedInIndex];
    double topUpAmount;

    clearTerminal();
    printAsciiBox("Top Up Money");
    printf("Your current balance: Rp. %.3f\n\n", user->money / 1000.0);
    printf("Enter the amount of money to top up (-1 to go back):\n>> ");

    while (1) {
        scanf("%lf", &topUpAmount);
        getchar();

        if (topUpAmount == -1) {
            return;
        }
        if (topUpAmount < 0) {
            printf("Error: The input must not be negative. Try again.\n>> ");
            continue;
        }
        if (topUpAmount > 1000) {
            printf("Error: The maximum top-up amount is Rp. 1000. Try again.\n>> ");
            continue;
        }
        break;
    }

    user->money += topUpAmount * 1000;
    printf("Top up successful! Your new balance is: Rp. %.3f\n", user->money / 1000.0);

    addTransaction(user->username, user->bitcoim, user->money, 0);

    synchronizeUsers();
    sendPressToContinue();
}

void handleRetrieveMoney() {
    struct User *user = &users[loggedInIndex];
    double retrieveAmount;

    clearTerminal();
    printAsciiBox("Retrieve Money");
    printf("Your current balance: Rp. %.3f\n\n", user->money / 1000.0);
    printf("Enter the amount of money to retrieve (-1 to go back):\n>> ");

    while (1) {
        scanf("%lf", &retrieveAmount);
        getchar();

        if (retrieveAmount == -1) {
            return;
        }

        if (retrieveAmount < 0) {
            printf("Error: The input must not be negative. Try again.\n>> ");
            continue;
        }

        if (retrieveAmount * 1000 > user->money) {
            printf("Error: You cannot retrieve more than your current balance. Try again.\n>> ");
            continue;
        }

        break;
    }

    user->money -= retrieveAmount * 1000;
    printf("Money retrieval successful! Your new balance is: Rp. %.3f\n", user->money / 1000.0);

    addTransaction(user->username, user->bitcoim, user->money, 0);

    synchronizeUsers();
    sendPressToContinue();
}

void handleHomeUserPage() {
    if (loggedInIndex == -1) {
        printf("Please login first\n");
        sendPressToContinue();
        return;
    }

    struct User *user = &users[loggedInIndex];

    while (1) {
        clearTerminal();

        char text[1000];
        sprintf(text, "Welcome, %s!", user->username);
        printAsciiBox(text);
        printf(
            "\nbitCoiM: %.5f BCM\n"
            "Money: Rp. %.3f\n\n"
            "Current 1 bitCoiM Price: Rp. %.3f\n\n"
            "1. Buy bitCoiM\n"
            "2. Sell bitCoiM\n"
            "3. Top Up Money\n"
            "4. Retrieve Money\n"
            "5. Exit\n>> ",
            user->username,
            user->bitcoim / 100000.0,
            user->money / 1000.0,
            ONE_BITCOIM_PRICE / 1000.0
        );
        int input;

        if (scanf("%d", &input) != 1) {
            printf("Invalid input. Please enter a number.\n");
            sendPressToContinue();
            while (getchar() != '\n');
            continue;
        }

        getchar();

        if (input == -1) {
            printf("Exiting...\n");
            break;
        }
        if (input < 1 || input > 5) {
            printf("Invalid choice. Please enter a number between 1 and 5.\n");
            sendPressToContinue();
            continue;
        }

        switch (input) {
            case 1:
                handleBuyBitcoim();
                break;
            case 2:
                handleSellBitcoim();
                break;
            case 3:
                handleTopUpMoney();
                break;
            case 4:
                handleRetrieveMoney();
                break;
            case 5:
                printf("Exiting...\n");
                return;
        }
    }
}

void handlePage() {
    if (loggedInIndex == -1) {
        printf("Please login first\n");
        sendPressToContinue();
        return;
    }
    struct User user = users[loggedInIndex];
    int isAdmin = strcmp(user.username, "admin") == 0;

    if (isAdmin) {
        handleHomeAdminPage();
    } else {
        handleHomeUserPage();
    }
}

int main(void) {
    int state = 0; // 0 = welcome page; 1 = login page; 2 = register page; 3 = exit
    while (1) {
        clearTerminal();
        userCount = 0;
        memset(users, 0, sizeof(users));
        // struct User admin = createUser("ID123ADM", "admin", encryptPassword("admin123"));
        // admin.verified = 1;
        // addUser(admin);
        fetchDataToMemory();
        loadTransactions();

        switch (state) {
            case 0: {
                printWelcomePage();
                int input;
                scanf("%d", &input);
                getchar();
                if (input < 0 || input > 3) {
                    printf("Invalid input\n");
                    break;
                }
                if (input == 3) {
                    printNar();
                    return 0;
                }
                state = input;
                break;
            }

            case 1: {
                int i = loginPage();
                if (i == 0) {
                    state = 0;
                } else {
                    state = 3;
                }
                break;
            }

            case 2: {
                registerPage();
                state = 0;
                break;
            }

            case 3: {
                handlePage();
                state = 0;
                break;
            }
        }
    }
}
