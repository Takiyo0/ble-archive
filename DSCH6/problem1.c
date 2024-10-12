#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <string.h>
#include <time.h>
#include <windows.h>

#define NOTE_C4 261
#define NOTE_D4 294
#define NOTE_E4 329
#define NOTE_F4 349
#define NOTE_G4 392
#define NOTE_A4 440
#define NOTE_B4 494
#define NOTE_C5 523
#define NOTE_D5 587

void clearTerminal() {
#ifdef WIN32
    system("cls");
#else
    system("clear");
#endif
}

struct Project {
    char name[255];
};

typedef struct Instrument {
    int id;
    char name[16];
    int attributes[3];
    struct Instrument *next;
} Instrument;

struct Project projects[255];
int projectCount = 0;
int currentProject = 0;

Instrument *hashTable[50];


void loadProjects() {
    FILE *file = fopen("projects.txt", "r");
    if (file == NULL) {
        return;
    }

    char buffer[255];
    while (fgets(buffer, 255, file)) {
        buffer[strcspn(buffer, "\n")] = 0;
        strcpy(projects[projectCount].name, buffer);
        projectCount++;
    }
    fclose(file);
}

void saveProjects() {
    FILE *file = fopen("projects.txt", "w");
    for (int i = 0; i < projectCount; i++) {
        fprintf(file, "%s\n", projects[i].name);
    }
    fclose(file);
}

int addProject(const char name[]) {
    strcpy(projects[projectCount].name, name);
    projectCount++;
    saveProjects();
    return projectCount - 1;
}

void removeProject(const char *name) {
    for (int i = 0; i < projectCount; i++) {
        if (strcmp(projects[i].name, name) == 0) {
            for (int j = i; j < projectCount - 1; j++) {
                strcpy(projects[j].name, projects[j + 1].name);
            }
            projectCount--;
            break;
        }
    }
    saveProjects();
}

int getWordCount(char *name) {
    int count = 0;
    char *temp = strdup(name);
    char *pch = strtok(temp, " ");

    while (pch != NULL) {
        count++;
        pch = strtok(NULL, " ");
    }

    free(temp);
    return count;
}

void waitForInput() {
    printf("\nPress any key to continue...");
    getchar();
}

void listProjects() {
    printf("Projects:\n");
    for (int i = 0; i < projectCount; i++) {
        printf("%d. %s\n", i + 1, projects[i].name);
    }
}

void openProject() {
    if (projectCount == 0) {
        printf("No projects available.\n");
        return;
    }

    listProjects();
    int selected;
    printf("Select a project to open (1-%d): ", projectCount);
    scanf("%d", &selected);

    if (selected >= 1 && selected <= projectCount) {
        currentProject = selected - 1;
        printf("Opened project: %s\n", projects[currentProject].name);
    } else {
        printf("Invalid selection.\n");
    }
}

Instrument *instrument_new(int id, const char *name, int attr1, int attr2, int attr3) {
    Instrument *newInstrument = (Instrument *) malloc(sizeof(Instrument));
    newInstrument->id = id;
    strcpy(newInstrument->name, name);
    newInstrument->attributes[0] = attr1;
    newInstrument->attributes[1] = attr2;
    newInstrument->attributes[2] = attr3;
    newInstrument->next = NULL;
    return newInstrument;
}

int hashFunction(const char *name, int typeId) {
    int sum = 0;
    for (int i = 0; i < strlen(name); i++) {
        sum += name[i] * (typeId + 1);
    }
    return sum % 50;
}

void insertInstrument(const char *name, int typeId, int attr1, int attr2, int attr3) {
    int hash = hashFunction(name, typeId);
    int id = typeId;

    Instrument *newInstrument = instrument_new(id, name, attr1, attr2, attr3);

    if (hashTable[hash] == NULL) {
        hashTable[hash] = newInstrument;
    } else {
        Instrument *current = hashTable[hash];
        while (current->next != NULL) {
            current = current->next;
        }
        current->next = newInstrument;
    }
}

void addInstrument() {
    char instrumentName[16];
    int instrumentType, attr1, attr2, attr3;

    printf("Select Instrument Type (0: Piano, 1: Guitar, 2: Bass, 3: Drum): ");
    scanf("%d", &instrumentType);

    do {
        printf("Enter instrument name (1-15 characters): ");
        scanf("%s", instrumentName);
    } while (strlen(instrumentName) == 0 || strlen(instrumentName) > 15);

    printf("Enter 3 attributes (1-10) [ex: 10 2 10], enter 0 to cancel:\n");
    scanf("%d %d %d", &attr1, &attr2, &attr3);

    if (attr1 == 0 || attr2 == 0 || attr3 == 0) {
        printf("Operation cancelled. Returning to Project Dashboard...\n");
        return;
    }

    insertInstrument(instrumentName, instrumentType, attr1, attr2, attr3);
}

void displayHashTable() {
    for (int i = 0; i < 50; i++) {
        if (hashTable[i] != NULL) {
            Instrument *current = hashTable[i];
            printf("Hash %d:\n", i);
            while (current != NULL) {
                printf("  ID: %d, Name: %s, Attributes: [%d, %d, %d]\n",
                       current->id, current->name, current->attributes[0], current->attributes[1],
                       current->attributes[2]);
                current = current->next;
            }
        }
    }
}

void projectDashboard() {
    int selected = 0;
    char *projectName = projects[currentProject].name;
    if (projectCount == 0 || currentProject > projectCount - 1) {
        printf("No projects found\n");
        waitForInput();
        return;
    }
    while (1) {
        clearTerminal();
        printf("Project: %s\n", projectName);
        printf("---------------------------------------\n");
        printf("Add Instrument %s\n", selected == 0 ? "<" : "");
        printf("View Instruments %s\n", selected == 1 ? "<" : "");
        printf("Delete Instrument %s\n", selected == 2 ? "<" : "");
        printf("Play Song %s\n", selected == 3 ? "<" : "");
        printf("Save and Exit %s\n", selected == 4 ? "<" : "");

        int ch = getch();
        if (ch == 72) {
            if (selected > 0) {
                selected--;
            } else {
                selected = 4;
            }
        } else if (ch == 80) {
            if (selected < 4) {
                selected++;
            } else {
                selected = 0;
            }
        } else if (ch == '\r' || ch == '\n') {
            clearTerminal();
            switch (selected) {
                case 0:
                    addInstrument();
                    waitForInput();
                    break;
                case 1:
                    displayHashTable();
                    waitForInput();
                    break;
                case 2:
                    printf("Deleting Instrument...\n");
                    waitForInput();
                    break;
                case 3:
                    printf("Playing Song...\n");
                    int notes[] = {NOTE_C4, NOTE_D4, NOTE_E4, NOTE_F4, NOTE_G4, NOTE_A4, NOTE_B4, NOTE_C5, NOTE_D5};
                    srand(time(NULL));
                    int elapsedTime = 0;

                    while (elapsedTime < 5000) {
                        int note = notes[rand() % (sizeof(notes) / sizeof(notes[0]))];
                        int duration = (rand() % 500 + 100);

                        Beep(note, duration);
                        elapsedTime += duration;
                    }
                    waitForInput();
                    break;
                case 4:
                    printf("Saving and Exiting...\n");
                    return;
            }
        }
    }
}

int newProject() {
    while (1) {
        clearTerminal();
        printf("Input Project Name [below 15 characters, 2 words, 0 to cancel]\n> ");

        char name[255];
        scanf(" %[^\n]", name);
        if (strcmp(name, "0") == 0) {
            return 0;
        }
        if (strlen(name) > 15) {
            printf("Name must be 15 characters or less");
            waitForInput();
            continue;
        }
        if (getWordCount(name) != 2) {
            printf("Name must be 2 words");
            waitForInput();
            continue;
        }

        currentProject = addProject(name);
        projectDashboard();
        break;
    }
}

int main(void) {
    int state = 0;

    while (1) {
        switch (state) {
            case 0: {
                int selected = 0;
                while (1) {
                    clearTerminal();
                    printf(
                        "Open a project %s\n"
                        "New project %s\n"
                        "Exit %s\n",
                        selected == 0 ? "<" : "",
                        selected == 1 ? "<" : "",
                        selected == 2 ? "<" : ""
                    );

                    int ch = getch();
                    if (ch == 72) {
                        if (selected > 0) {
                            selected--;
                        } else {
                            selected = 2;
                        }
                    } else if (ch == 80) {
                        if (selected < 2) {
                            selected++;
                        } else {
                            selected = 0;
                        }
                    } else if (ch == '\r' || ch == '\n') {
                        switch (selected) {
                            case 0:
                                state = 1;
                                break;
                            case 1:
                                state = 2;
                                break;
                            case 2:
                                state = 3;
                                break;
                        }
                        break;
                    }
                }
                break;
            }

            case 1:
                openProject();
                return 0;

            case 2:
                newProject();
                return 0;

            case 3:
                printf("Saving and exitting...\n");
                saveProjects();
                return 0;
        }
    }
}
