#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#define ll long long

#define MAX_FISH 100
#define NAME_LEN 100
#define TAG_LEN 4

void base() {
    printf("1. New Fish\n");
    printf("2. See Fish\n");
    printf("3. Update Fish\n");
    printf("4. Exit\n");
    printf(">> ");
}

bool startsWithUpper(const char *s) {
    return (s[0] >= 'A' && s[0] <= 'Z') || (s[1] >= 'A' && s[1] <= 'Z');
}

bool tagIs3Digits(const char *s) {
    return s[0] >= '0' && s[0] <= '9' && s[1] >= '0' && s[1] <= '9' && s[2] >= '0' && s[2] <= '9' && strlen(s) == 3;
}

bool stringHasSpaces(const char *s) {
    return strchr(s, ' ') != NULL;
}

bool stringIsNumber(const char *s) {
    for (int i = 0; i < strlen(s); i++) {
        if (s[i] < '0' || s[i] > '9') {
            return false;
        }
    }
    return true;
}

char fishes[MAX_FISH][NAME_LEN];
char tags[MAX_FISH][TAG_LEN];
int fishCount = 0;

void addFish(const char name[], const char tag[]) {
    if (fishCount < MAX_FISH) {
        strncpy(fishes[fishCount], name, NAME_LEN - 1);
        fishes[fishCount][NAME_LEN - 1] = '\0';
        strncpy(tags[fishCount], tag, TAG_LEN - 1);
        tags[fishCount][TAG_LEN - 1] = '\0';
        fishCount++;
    } else {
        printf("Fish list is full!\n");
    }
}

void updateFish(int n, const char name[], const char tag[]) {
    if (n >= 0 && n < fishCount) {
        strncpy(fishes[n], name, NAME_LEN - 1);
        fishes[n][NAME_LEN - 1] = '\0';
        strncpy(tags[n], tag, TAG_LEN - 1);
        tags[n][TAG_LEN - 1] = '\0';
    } else {
        printf("Invalid fish number!\n");
    }
}

int main() {
    while (1) {
        #ifdef _WIN32
                system("cls");
        #else
                system("clear");
        #endif

        base();
        int choice;
        scanf("%d", &choice);

        switch (choice) {
            case 1: {
                while (1) {
                    char temp[10000];
                    char name[NAME_LEN], tag[TAG_LEN];

                    printf("Enter a fish (format Name#Tag): ");
                    getchar();
                    fgets(temp, sizeof(temp), stdin);

                    temp[strcspn(temp, "\n")] = 0;

                    char *hashPos = strchr(temp, '#');
                    if (hashPos == NULL || stringHasSpaces(temp)) {
                        printf("Invalid fish format. Please try again.\n");
                        continue;
                    }

                    *hashPos = '\0';
                    strncpy(name, temp, NAME_LEN - 1);
                    strncpy(tag, hashPos + 1, TAG_LEN - 1);

                    name[NAME_LEN - 1] = '\0';
                    tag[TAG_LEN - 1] = '\0';

                    if (strlen(name) > NAME_LEN - 1 || strlen(tag) > TAG_LEN - 1) {
                        printf("Invalid fish format. Please try again.\n");
                        continue;
                    }

                    if (stringIsNumber(name) || !stringIsNumber(tag) || !tagIs3Digits(tag)) {
                        printf("Invalid fish format. Please try again.\n");
                        continue;
                    }

                    if (!startsWithUpper(name)) {
                        printf("Invalid fish format. Name must start with an uppercase letter.\n");
                        continue;
                    }

                    addFish(name, tag);
                    printf("Fish '%s#%s' is valid and created successfully.\n", name, tag);
                    break;
                }

                printf("\nPress any key to continue...");
                getchar();
                break;
            }

            case 2: {
                if (fishCount == 0) {
                    printf("No fish in the list yet.\n");
                } else {
                    printf("Current fish:\n");
                    for (int i = 0; i < fishCount; i++) {
                        printf("%d. %s#%s\n", i + 1, fishes[i], tags[i]);
                    }
                }
                printf("\n Press any key to continue... ");
                getchar();
                getchar();
                break;
            }

            case 3: {
                int n;
                char name[NAME_LEN], tag[TAG_LEN];
                printf("Enter the number of the fish to update: ");
                scanf("%d", &n);

                if (n < 1 || n > fishCount) {
                    printf("Invalid fish number. Please enter a number between 1 and %d.\n", fishCount);
                    break;
                }

                while (1) {
                    printf("Enter a new fish name (format Name#Tag): ");
                    getchar();
                    scanf("%99[^#]#%3s", name, tag);
                    getchar();

                    if (!startsWithUpper(name) || !tagIs3Digits(tag)) {
                        printf("Invalid fish format. Please try again.\n");
                        continue;
                    }
                    break;
                }

                updateFish(n - 1, name, tag);

                printf("Fish name updated to '%s#%s' successfully!\n", name, tag);

                printf("\n Press any key to continue... ");
                getchar();
                getchar();
                break;
            }

            case 4: {
                exit(0);
            }

            default: {
                printf("Invalid choice! Please choose a valid option.\n");
                break;
            }
        }
    }
    return 0;
}
