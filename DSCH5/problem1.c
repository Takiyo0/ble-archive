#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

typedef struct Player {
    char name[11];
    int gold;
    int point;

    struct Player *prev;
    struct Player *next;
} Player;

typedef struct Patient {
    char name[11];
    int age;
    int emergencyStatus;
    int gender; // 0 = male, 1 = female
    double height;
    double weight;

    struct Patient *prev;
    struct Patient *next;
} Patient;

Player *headPlayer = NULL;
Player *tailPlayer = NULL;
Patient *headPatient = NULL;
Patient *tailPatient = NULL;
char loggedInUser[11];

enum Gender {
    MALE = 0,
    FEMALE = 1
};

enum EmergencyStatus {
    CATASTROPHIC = 1,
    DANGER = 2,
    SICK = 3
};

void clearTerminal() {
#ifdef _WIN32
    system("cls");
#else
    system("clear");
#endif
}

char patients[1000][12];

void printLogo() {
    printf(
        "\033[34m\n"
        "                                 .--.-.         ,--.-.,-.\n"
        " _,..---._   .-.,.---.          /==/  /        /==/- |\  \n"
        "/==/,   -  \ /==/  `   \        \==\ -\        |==|_ `/_ /\n"
        "|==|   _   _\==|-, .=., |        \==\- \       |==| ,   /\n"
        "|==|  .=.   |==|   '='  /         `--`-'       |==|-  .|\n"
        "|==|,|   | -|==|- ,   .'                       |==| _ , \\\n"
        "|==|  '='   /==|_  . ,'.                       /==/  '\\  |\n"
        "|==|-,   _`//==/  /\ ,  )                      \==\\ /=/.\'\n"
        "`-.`.____.' `--`-`--`--'                        `--`\n"
        "\033[0m"
    );
}

void synchronizeToDb() {
    FILE *file = fopen("dataset.txt", "w");
    if (file == NULL) {
        return;
    }

    Player *current = headPlayer;
    while (current != NULL) {
        fprintf(file, "%s#%d#%d\n", current->name, current->gold, current->point);
        current = current->next;
    }

    fclose(file);
}

void loadPatientNames() {
    FILE *file = fopen("patient_name.txt", "r");
    if (file == NULL) {
        // printf("File patient_name.txt doesn't exists.");
        return;
    }

    int index = 0;
    char name[11];
    while (fscanf(file, "%[^\n]\n", name) != EOF) {
        strcpy(patients[index], name);
        index++;
    }

    // printf("Loaded %d patient names", index);
    fclose(file);
}

Player *player_new() {
    Player *newPlayer = (Player *) malloc(sizeof(struct Player));
    if (newPlayer == NULL) {
        printf("Memory allocation failed. Is the memory full?");
        exit(0);
    }
    newPlayer->gold = 0;
    newPlayer->point = 1;
    newPlayer->next = NULL;
    newPlayer->prev = NULL;
    return newPlayer;
}

void player_get_all(Player *players[], int *size) {
    Player *current = headPlayer;
    int index = 0;
    while (current != NULL) {
        // printf("Got player %s\n", current->name);
        players[index] = current;
        // printf("Set the player as %s\n", players[index]->name);
        strcpy(players[index]->name, current->name);
        index++;
        (*size)++;
        current = current->next;
    }
}

Player *player_add_last(Player *player, int dontsync) {
    if (headPlayer == NULL) {
        headPlayer = player;
        tailPlayer = player;
    } else {
        tailPlayer->next = player;
        player->prev = tailPlayer;
        tailPlayer = player;
    }
    if (dontsync) return player;
    synchronizeToDb();
    return player;
}

Player *player_remove(char name[]) {
    struct Player *current = headPlayer;

    while (current != NULL) {
        if (strcmp(current->name, name) == 0) {
            // if (current->gold == gold) {
            if (current->prev != NULL) {
                current->prev->next = current->next;
            } else {
                headPlayer = current->next;
            }
            if (current->next != NULL) {
                current->next->prev = current->prev;
            } else {
                tailPlayer = current->prev;
            }
            free(current);
            return current;
        }
        current = current->next;
    }

    synchronizeToDb();
    return NULL;
}

Player *player_get(char name[]) {
    Player *current = headPlayer;
    while (current != NULL) {
        if (strcmp(current->name, name) == 0) {
            return current;
        }
        current = current->next;
    }
    return NULL;
}

void loadPlayers() {
    FILE *file = fopen("dataset.txt", "r");
    if (file == NULL) {
        return;
    }

    char an[100], line[1024];
    int gold = 0, point = 0;
    // printf("yes");
    while (fgets(line, sizeof(line), file)) {
        // printf("getting");
        char *token = strtok(line, "#");
        if (token != NULL) strcpy(an, token);

        token = strtok(NULL, "#");
        if (token != NULL) gold = atoi(token);

        token = strtok(NULL, "#");
        if (token != NULL) point = atoi(token);

        Player *newPlayer = player_new();
        strcpy(newPlayer->name, an);
        newPlayer->gold = gold;
        newPlayer->point = point;
        player_add_last(newPlayer, 1);
    }
}

Patient *patient_new() {
    Patient *patient = (Patient *) malloc(sizeof(struct Patient));
    if (patient == NULL) {
        printf("Unable to allocate memory. Is your RAM full?");
        exit(0);
    }
    patient->age = 0;
    enum Gender gender = MALE;
    patient->gender = gender;
    patient->height = 0.0;
    patient->weight = 0.0;
    enum EmergencyStatus emergency = CATASTROPHIC;
    patient->emergencyStatus = emergency;

    return patient;
}

Patient *patient_add_last(Patient *patient) {
    if (headPatient == NULL) {
        headPatient = patient;
        tailPatient = patient;
    } else {
        tailPatient->next = patient;
        patient->prev = tailPatient;
        tailPatient = patient;
    }

    return patient;
}

void patient_delete(char name[]) {
    Patient *current = headPatient;
    while (current != NULL) {
        if (strcmp(current->name, name) == 0) {
            if (current->prev) {
                current->prev->next = current->next;
            } else {
                headPatient = current->next;
            }
            if (current->next) {
                current->next->prev = current->prev;
            } else {
                tailPatient = current->prev;
            }
            free(current);
            return;
        }
        current = current->next;
    }
}

Patient *patient_get(char name[]) {
    Patient *current = headPatient;
    while (current != NULL) {
        if (strcmp(current->name, name) == 0) {
            return current;
        }
        current = current->next;
    }
    return NULL;
}

int patient_count() {
    Patient *current = headPatient;
    int count = 0;
    while (current != NULL) {
        count++;
        current = current->next;
    }

    return count;
}

Patient *patient_swap(Patient *patient, int targetPosition) {
    Patient *current = headPatient;
    Patient *target = NULL;
    int index = 0;
    while (current != NULL) {
        if (index == targetPosition) {
            target = current;
        }
        index++;
        current = current->next;
    }

    if (target == NULL || patient == NULL || patient == target) return NULL;

    Patient *prevPatient = patient->prev;
    Patient *nextPatient = patient->next;
    Patient *prevTarget = target->prev;
    Patient *nextTarget = target->next;

    if (prevPatient) prevPatient->next = target;
    if (nextPatient) nextPatient->prev = target;

    if (prevTarget) prevTarget->next = patient;
    if (nextTarget) nextTarget->prev = patient;

    patient->prev = prevTarget;
    patient->next = nextTarget;
    target->prev = prevPatient;
    target->next = nextPatient;

    if (headPatient == patient)
        headPatient = target;
    else if (headPatient == target)
        headPatient = patient;

    if (tailPatient == patient)
        tailPatient = target;
    else if (tailPatient == target)
        tailPatient = patient;

    return target;
}

void waitForInput() {
    printf("Press any key to continue...");
    getchar();
}

void renderInsert() {
    int state = 0; // 0 = name; 1 = age; 2 = gender; 3 = emergency; 4 = height; 5 = weight;
    Patient *patient = patient_new();

    int cont = 1;
    while (cont) {
        clearTerminal();
        switch (state) {
            case 0: {
                printf("Enter patient's name [0 to exit; type \"random\" to generate patient, must be unique]:\n> ");
                char name[11];
                scanf("%[^\n]", name);
                if (strcmp(name, "0") == 0) {
                    cont = 0;
                    break;
                }

                if (strcmp(name, "random") == 0) {
                    int found = 1;
                    while (found == 1) {
                        srand(time(0));
                        int random = (rand() % (20 - 1)) + 0;
                        strcpy(name, patients[random]);
                        if (patient_get(name) == NULL) {
                            found = 0;
                        }
                    }

                    printf("The patient's name will be %s\n", name);
                    waitForInput();
                } else {
                    scanf("%[^\n]", name);
                    getchar();
                    if (patient_get(name) != NULL) {
                        printf("Patient already exists. Please try again.\n");
                        waitForInput();
                        continue;
                    }
                }

                if (strlen(name) < 2 || strlen(name) > 10) {
                    printf("Invalid name. Please try again.\n");
                    waitForInput();
                    continue;
                }

                strcpy(patient->name, name);
                state++;
                break;
            }

            case 1: {
                printf("Enter patient's age [0 to exit]:\n> ");
                int age = 0;
                scanf("%d", &age);
                getchar();
                if (age == 0) {
                    cont = 0;
                    break;
                }
                if (age < 0 || age > 100) {
                    printf("Invalid age. Please try again.\n");
                    waitForInput();
                    continue;
                }

                patient->age = age;
                state++;
                break;
            }
            case 2: {
                printf("Enter patient's gender [0 to exit; M = male; F = female]:\n> ");
                char gender;
                scanf("%c", &gender);
                getchar();
                if (gender == '0') {
                    cont = 0;
                    break;
                }
                if (gender == 'M' || gender == 'm') {
                    patient->gender = MALE;
                } else if (gender == 'F' || gender == 'f') {
                    patient->gender = FEMALE;
                } else {
                    printf("Invalid gender. Please try again.\n");
                    waitForInput();
                    continue;
                }
                state++;
                break;
            }

            case 3: {
                printf("Enter patient's emergency status [0 to exit; C/Catastrophic; D/Danger; S/Sick]:\n> ");
                char emergency[13];
                scanf("%[^\n]", emergency);
                getchar();
                if (strcmp(emergency, "0") == 0) {
                    cont = 0;
                    break;
                }

                if (strcmp(emergency, "C") == 0 || strcmp(emergency, "Catastrophic") == 0) {
                    patient->emergencyStatus = CATASTROPHIC;
                } else if (strcmp(emergency, "D") == 0 || strcmp(emergency, "Danger") == 0) {
                    patient->emergencyStatus = DANGER;
                } else if (strcmp(emergency, "S") == 0 || strcmp(emergency, "Sick") == 0) {
                    patient->emergencyStatus = SICK;
                } else {
                    printf("Invalid emergency status. Please try again.\n");
                    waitForInput();
                    continue;
                }
                state++;
                break;
            }

            case 4: {
                printf("Enter patient's height [0 to exit]:\n> ");
                double height = 0.0;
                scanf("%lf", &height);
                getchar();
                if (height == 0.0) {
                    cont = 0;
                    break;
                }
                if (height < 0.0) {
                    printf("Invalid height. Please try again.\n");
                    waitForInput();
                    continue;
                }
                patient->height = height;
                state++;
                break;
            }

            case 5: {
                printf("Enter patient's weight [0 to exit]:\n> ");
                double weight = 0.0;
                scanf("%lf", &weight);
                getchar();
                if (weight == 0.0) {
                    cont = 0;
                    break;
                }
                if (weight < 0.0) {
                    printf("Invalid weight. Please try again.\n");
                    waitForInput();
                    continue;
                }
                patient->weight = weight;
                state++;
                break;
            }

            case 6: {
                Patient *pat = patient_add_last(patient);
                printf("Successfully added %s", pat->name);
                waitForInput();
                return;
            }
            default: break;
        }
    }
}

/**
 * Renders the patient details.
 *
 * @param startName The name of the patient to start with.
 *
 * @return void
 *
 * @throws None
 */
void renderDetails(char startName[]) {
    int cont = 1;
    while (cont) {
        clearTerminal();

        printf("\033[1;34m===========================================\033[0m\n");
        printf("\033[1;36m Patient Details \033[0m\n");
        printf("\033[1;34m-------------------------------------------\033[0m\n");

        Patient *patient = patient_get(startName);
        char *emergencyStatus = "";
        if (patient->emergencyStatus == CATASTROPHIC) {
            emergencyStatus = "Catastrophic";
        } else if (patient->emergencyStatus == DANGER) {
            emergencyStatus = "Danger";
        } else {
            emergencyStatus = "Sick";
        }

        printf(
            "\033[1;32m Name               : %s \033[0m\n"
            "\033[1;32m Age                : %d \033[0m\n"
            "\033[1;32m Emergency Status   : %s \033[0m\n"
            "\033[1;32m Gender             : %s \033[0m\n"
            "\033[1;32m Height             : %.2lf \033[0m\n"
            "\033[1;32m Weight             : %.2lf \033[0m\n",
            patient->name,
            patient->age,
            emergencyStatus,
            patient->gender == 0 ? "Male" : "Female",
            patient->height,
            patient->weight
        );

        printf("\033[1;34m-------------------------------------------\033[0m\n");
        printf("\033[1;36m Commands: [N] Next [P] Previous [D] Delete [Q] Quit \033[0m\n> ");
        char command;
        scanf("%c", &command);

        if (command == 'N' || command == 'n') {
            if (patient->next != NULL) startName = patient->next->name;
        } else if (command == 'P' || command == 'p') {
            if (patient->prev != NULL) startName = patient->prev->name;
        } else if (command == 'D' || command == 'd') {
            printf("\033[1;31m About to delete %s \033[0m", startName);
            waitForInput();
            patient_delete(startName);
            if (headPatient == NULL) {
                cont = 0;
            } else {
                startName = headPatient->name;
            }
        } else if (command == 'Q' || command == 'q') {
            cont = 0;
        }
    }

    printf("\033[1;34m===========================================\033[0m\n");
}

void renderView() {
    printf("\033[1;34m===========================================\033[0m\n");
    printf("\033[1;36m|\033[0m %-2s \033[1;36m|\033[0m %-10s \033[1;36m|\033[0m %-10s \033[1;36m|\033[0m\n", "Id",
           "Name", "Emergency");
    printf("\033[1;34m-------------------------------------------\033[0m\n");
    int index = 0;
    Patient *current = headPatient;
    int patientCurrent = patient_count();
    char names[patientCurrent][12];
    while (current != NULL) {
        char *emergencyStatus = "";
        if (current->emergencyStatus == CATASTROPHIC) {
            emergencyStatus = "\033[1;31mCatastrophic\033[0m";
        } else if (current->emergencyStatus == DANGER) {
            emergencyStatus = "\033[1;33mDanger\033[0m";
        } else {
            emergencyStatus = "\033[1;32mSick\033[0m";
        }
        printf("\033[1;36m|\033[0m %2d \033[1;36m|\033[0m %10s \033[1;36m|\033[0m %10s \033[1;36m|\033[0m\n", index + 1,
               current->name, emergencyStatus);
        strcpy(names[index], current->name);
        index++;
        current = current->next;
    }
    printf("\033[1;34m===========================================\033[0m\n");

    printf("Enter patient number to view their details [0 to exit]\n> ");
    int choice;
    scanf("%d", &choice);

    if (choice == 0) {
        return;
    }
    renderDetails(names[choice - 1]);
}

void deleteAll() {
    Patient *current = headPatient;
    while (current != NULL) {
        patient_delete(current->name);
        current = current->next;
    }
}

void renderHeal(Player *player) {
    Patient *current = headPatient;
    int gems = 0;
    int totalPatients = 0;

    printf("\033[1;34m===========================================\033[0m\n");
    printf("\033[1;36m Healing Patients... \033[0m\n");
    printf("\033[1;34m-------------------------------------------\033[0m\n");

    while (current != NULL) {
        gems += ((1000.0 / current->emergencyStatus) * 0.1 * current->age * (player_get(loggedInUser) == NULL
                                                                                 ? 0
                                                                                 : player_get(loggedInUser)->point));
        totalPatients++;
        printf("\033[1;32m Healed %d patients. \033[0m\n", totalPatients);
        current = current->next;
    }

    player->gold += gems;
    synchronizeToDb();
    deleteAll();

    printf("\033[1;34m-------------------------------------------\033[0m\n");
    printf("\033[1;36m Healing Complete! \033[0m\n");
    printf("\033[1;32m Healed %d patients. Gained %d gold. \033[0m\n", totalPatients, gems);
    printf("\033[1;34m-------------------------------------------\033[0m\n");

    printf("\033[1;36m Do you want to view your current stats? [y/n] \033[0m\n> ");
    char res;
    scanf("%c", &res);
    getchar();

    if (res == 'y' || res == 'Y') {
        printf("\033[1;32m Current Gold: %d \033[0m\n", player->gold);
        printf("\033[1;32m Current Level: %d \033[0m\n", player->point);
        waitForInput();
    }

    printf("\033[1;34m===========================================\033[0m\n");
}

void renderUpgrade(Player *player) {
    int requiredGem = 1000 * player->point;
    printf("\033[1;34m===========================================\033[0m\n");
    printf("\033[1;36m Upgrade Your Skill \033[0m\n");
    printf("\033[1;34m-------------------------------------------\033[0m\n");
    printf("\033[1;32m Current Level: %d \033[0m\n", player->point);
    printf("\033[1;32m Next Level: %d \033[0m\n", player->point + 1);
    printf("\033[1;33m Required Gold: %d \033[0m\n", requiredGem);
    printf("\033[1;34m-------------------------------------------\033[0m\n");
    printf("\033[1;36m Do you want to upgrade? [y/n] \033[0m\n> ");
    char res;
    scanf("%c", &res);
    getchar();
    if (res == 'y' || res == 'Y') {
        if (player->gold < requiredGem) {
            printf("\033[1;31m You don't have enough gold! Heal more patients and get back here. \033[0m");
            waitForInput();
        } else {
            player->point++;
            printf("\033[1;32m Congratulation on your new level %d skill! \033[0m", player->point);
            waitForInput();
        }
    } else {
        printf("\033[1;36m Just go here again if you changed your mind! \033[0m");
        waitForInput();
    }
    printf("\033[1;34m===========================================\033[0m\n");
}

void renderEdit(Player *player) {
    int state = 0; // 0 = select patient; 1 = new patient line;
    while (1) {
        printf("===========================================\n");
        int index = 0;
        Patient *current = headPatient;
        int patientCurrent = patient_count();
        char names[patientCurrent][12];
        Patient *selectedPatient = NULL;

        while (current != NULL) {
            char *emergencyStatus = "";
            if (current->emergencyStatus == CATASTROPHIC) {
                emergencyStatus = "Catastrophic";
            } else if (current->emergencyStatus == DANGER) {
                emergencyStatus = "Danger";
            } else {
                emergencyStatus = "Sick";
            }
            printf("| %d | %5s | %5s |\n", index + 1, current->name, emergencyStatus);
            strcpy(names[index], current->name);
            index++;
            current = current->next;
        }
        printf("===========================================\n");
        if (state == 0) {
            printf("Select a patient number to move [0 to go back]:\n> ");
            int selectNum;
            scanf("%d", &selectNum);
            getchar();
            if (selectNum == 0) {
                break;
            }
            if (selectNum > patientCurrent) {
                printf("There is only %d patients.", patientCurrent);
                waitForInput();
                continue;
            }
            selectedPatient = patient_get(names[selectNum - 1]);
            if (selectedPatient == NULL) {
                printf("There is no patient named %s.", names[selectNum - 1]);
                waitForInput();
                continue;
            }
            state++;
        } else if (state == 1) {
            printf("Enter new patient line [0 to go back]:\n> ");
            int selectNum;
            scanf("%d", &selectNum);
            getchar();
            if (selectNum == 0) {
                break;
            }
            if (selectNum > patientCurrent) {
                printf("There is only %d patients.", patientCurrent);
                waitForInput();
                continue;
            }
            patient_swap(selectedPatient, selectNum - 1);
            printf("Successfully swap %s's position to %d", selectedPatient->name, selectNum);
            waitForInput();
            break;
        }
    }
}

void renderGame(Player *player) {
    int state = 0; // 0 = home; 1 = insert; 2 = view; 3 = heal; 4 = upgrade; 5 = edit; 6 = exit
    int cont = 1;
    while (cont) {
        clearTerminal();
        switch (state) {
            case 0: {
                printLogo();
                printf(
                    "\033[1;34m++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\033[0m\n"
                    "\033[1;34m+ Name   : %-53s +\033[0m\n"
                    "\033[1;34m+ Gold   : %-53d +\033[0m\n"
                    "\033[1;34m+ Points : %-53d +\033[0m\n"
                    "\033[1;34m++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\033[0m\n",
                    player->name,
                    player->gold,
                    player->point
                );
                printf(
                    "\033[1;36m=====================================================\033[0m\n"
                    "\033[1;32m  1. Insert Patient  \033[0m\n"
                    "\033[1;32m  2. View All Patient\033[0m\n"
                    "\033[1;32m  3. Heal Patient    \033[0m\n"
                    "\033[1;32m  4. Upgrade Doctor Skill\033[0m\n"
                    "\033[1;32m  5. Edit Patient Line\033[0m\n"
                    "\033[1;31m  6. Exit          \033[0m\n"
                    "\033[1;36m=====================================================\033[0m\n"
                    "\033[1;36m> \033[0m"
                );

                int choice;
                scanf("%d", &choice);
                getchar();
                if (choice == 6) {
                    cont = 0;
                    break;
                };
                if (choice < 1 || choice > 6) {
                    printf("Invalid choice. Please try again.\n");
                    waitForInput();
                    continue;
                }
                state = choice;
                break;
            }

            case 1: {
                renderInsert();
                state = 0;
                break;
            }

            case 2: {
                renderView();
                state = 0;
                break;
            }

            case 3: {
                renderHeal(player);
                state = 0;
                break;
            }

            case 4: {
                renderUpgrade(player);
                state = 0;
                break;
            }

            case 5: {
                renderEdit(player);
                state = 0;
                break;
            }

            case 6: {
                cont = 0;
                break;
            }
        }
    }
}

void renderNewGame() {
    printf(
        "\033[1;34m===============================================\033[0m\n"
        "\033[1;32m          Welcome to the Login Page!         \033[0m\n"
        "\033[1;34m===============================================\033[0m\n"
    );

    printf(
        "\033[1;36mPlease enter your username (2-10 characters)\033[0m\n"
        "\033[1;31m[BACK to return to Homepage]\033[0m\n"
        "\033[1;36m> \033[0m"
    );

    char username[11];
    scanf("%[^\n]", username);
    getchar();
    if (strcmp(username, "BACK") == 0) {
        return;
    }
    if (strlen(username) < 2 || strlen(username) > 10) {
        printf("Invalid username. Please try again.\n");
        waitForInput();
        return;
    }

    Player *player = player_get(username);
    if (player == NULL) {
        Player *newPlayer = player_new();
        strcpy(newPlayer->name, username);
        player_add_last(newPlayer, 0);
        player = newPlayer;
    }

    strcpy(loggedInUser, player->name);
    renderGame(player);
}

void sortByGold(Player *players[], int size) {
    for (int i = 0; i < size - 1; i++) {
        int swapped = 0;
        for (int j = 0; j < size - i - 1; j++) {
            if (players[j]->gold < players[j + 1]->gold) {
                Player *temp = players[j];
                players[j] = players[j + 1];
                players[j + 1] = temp;
                swapped = 1;
            }
        }
        if (!swapped) break;
    }
}


void renderScoreboard() {
    Player *players[2000];
    int size = 0;
    player_get_all(players, &size);
    sortByGold(players, size);

    for (int i = 0; i < size; i++) {
        printf("%d. %s : %d gold\n", i + 1, players[i]->name, players[i]->gold);
    }

    waitForInput();
}

int main(void) {
    loadPatientNames();
    loadPlayers();
    int state = 0; // 0 = homepage; 1 = new game; 2 = register; 3 = exit;
    while (1) {
        clearTerminal();
        switch (state) {
            case 0: {
                printLogo();
                printf(
                    "\033[1;34m===============================================\033[0m\n"
                    "\033[1;32m  1. New Game  \033[0m\n"
                    "\033[1;32m  2. High Score\033[0m\n"
                    "\033[1;32m  3. Exit     \033[0m\n"
                    "\033[1;34m===============================================\033[0m\n"
                    "\033[1;36m> \033[0m"
                );
                int choice;
                scanf("%d", &choice);
                getchar();
                if (choice < 1 || choice > 3) {
                    printf("Invalid choice. Please try again.\n");
                    waitForInput();
                    continue;
                }
                state = choice;
                break;
            }

            case 1: {
                renderNewGame();
                // waitForInput();
                state = 0;
                break;
            }

            case 2: {
                renderScoreboard();
                state = 0;
                break;
            }

            case 3: {
                return 0;
            }
        }
    }
}
