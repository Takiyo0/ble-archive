#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

struct Jewelry {
    char id[6];
    char name[100];
    char description[100];
    char type[100];
    int price;
    int stock;
};

struct JewelryArray {
    struct Jewelry *jewelries;
    int count;
};

int generate4Id() {
    srand(time(0));
    const int ran = 1000 + rand() % 9000;
    return ran;
}

void printLandingPage() {
    printf(
        "   /$$   /$$           /$$$$$$$    /$$     /$$\n"
        "  | $$  /$$/          | $$__  $$  | $$    |__/\n"
        "  | $$ /$$/   /$$$$$$ | $$  \\ $$ /$$$$$$   /$$  /$$$$$$   /$$$$$$\n"
        "  | $$$$$/   |____  $$| $$$$$$$/|_  $$_/  | $$ /$$__  $$ /$$__  $$\n"
        "  | $$  $$    /$$$$$$$| $$__  $$  | $$    | $$| $$$$$$$$| $$  \\__/\n"
        "  | $$\\  $$  /$$__  $$| $$  \\ $$  | $$ /$$| $$| $$_____/| $$\n"
        "  | $$ \\  $$|  $$$$$$$| $$  | $$  |  $$$$/| $$|  $$$$$$$| $$\n"
        "  |__/  \\__/ \\_______/|__/  |__/   \\___/  |__/ \\_______/|__/\n\n"
        "----------------------------------------------------------------------\n\n"
        "Enter your role (Admin | User) [Case Sensitive] > ");
}

void clearTerminal() {
#ifdef _WIN32
    system("cls");
#else
        system("clear");
#endif
}

// JewelryId#JewelryName#JewelryDescription#JewelryType#JewelryPrice#JewelryStock
void addData(char id[6], char name[], char description[], char type[], int price, int stock) {
    FILE *f = fopen("KaRtier.txt", "a");
    if (f == NULL) {
        return;
    }

    fprintf(f, "%s#%s#%s#%s#%d#%d\n", id, name, description, type, price, stock);
    fclose(f);
}

struct Jewelry getJewelry(char id[6]) {
    FILE *f = fopen("KaRtier.txt", "r");
    if (f == NULL) {
        return (struct Jewelry){};
    }

    struct Jewelry j;
    while (fscanf(f, "%s#%s#%s#%s#%d#%d\n", j.id, j.name, j.description, j.type, &j.price, &j.stock) != EOF) {
        if (strcmp(j.id, id) == 0) {
            fclose(f);
            return j;
        }
    }
    fclose(f);
    return j;
}

void DeleteJewelry(const int id) {
    char realId[10];
    sprintf(realId, "J%d", id);

    FILE *f = fopen("KaRtier.txt", "r");
    if (f == NULL) {
        return;
    }

    FILE *temp = fopen("temp.txt", "w");
    if (temp == NULL) {
        fclose(f);
        return;
    }

    struct Jewelry j;
    int i = 0;
    while (fscanf(f, "%5[^#]#%19[^#]#%49[^#]#%9[^#]#%d#%d\n", j.id, j.name, j.description, j.type, &j.price,
                  &j.stock) != EOF) {
        if (strcmp(j.id, realId) != 0) {
            fprintf(temp, "%s#%s#%s#%s#%d#%d\n", j.id, j.name, j.description, j.type, j.price, j.stock);
        } else {
            i++;
        }
    }

    fclose(f);
    fclose(temp);

    if (i > 0) {
        remove("KaRtier.txt");
        rename("temp.txt", "KaRtier.txt");
    } else {
        remove("temp.txt");
        printf("Jewelry with ID %s not found.\n", realId);
    }
}

struct JewelryArray getAllJewelry() {
    FILE *f = fopen("KaRtier.txt", "r");
    if (f == NULL) {
        return (struct JewelryArray){};
    }

    struct Jewelry *jewelries = malloc(sizeof(struct Jewelry) * 100);

    int i = 0;
    char buffer[256];
    while (fgets(buffer, sizeof(buffer), f) != NULL) {
        sscanf(buffer, "%[^#]#%[^#]#%[^#]#%[^#]#%d#%d",
               jewelries[i].id,
               jewelries[i].name,
               jewelries[i].description,
               jewelries[i].type,
               &jewelries[i].price,
               &jewelries[i].stock);
        i++;
    }
    fclose(f);
    return (struct JewelryArray){.jewelries = jewelries, .count = i};
}

int isJewelryExists(char id[6]) {
    struct JewelryArray jewelries = getAllJewelry();
    if (jewelries.jewelries == NULL) {
        return 0;
    }

    for (int i = 0; i < 100; i++) {
        if (strcmp(jewelries.jewelries[i].id, id) == 0) {
            free(jewelries.jewelries);
            return 1;
        }
    }

    free(jewelries.jewelries);
    return 0;
}

void changeJewelryStock(char id[6], int newStock) {
    FILE *f = fopen("KaRtier.txt", "r");
    if (f == NULL) {
        return;
    }
    FILE *temp = fopen("temp.txt", "w");
    if (temp == NULL) {
        fclose(f);
        return;
    }
    struct Jewelry j;
    while (fscanf(f, "%5[^#]#%19[^#]#%49[^#]#%9[^#]#%d#%d\n", j.id, j.name, j.description, j.type, &j.price,
                  &j.stock) != EOF) {
        if (strcmp(j.id, id) == 0) {
            j.stock = newStock;
            fprintf(temp, "%s#%s#%s#%s#%d#%d\n", j.id, j.name, j.description, j.type, j.price, j.stock);
        } else {
            fprintf(temp, "%s#%s#%s#%s#%d#%d\n", j.id, j.name, j.description, j.type, j.price, j.stock);
        }
    }
    fclose(f);
    fclose(temp);
    remove("KaRtier.txt");
    rename("temp.txt", "KaRtier.txt");
}

void printHomeAdminPage() {
    printf(
        "Welcome Admin!\n"
        "How can I help you today?\n\n"
        "1. Add New Jewelry\n"
        "2. Delete Jewelry\n"
        "3. View All Product\n"
        "4. Exit\n"
        "Enter your choice > ");
}

void renderAllJewelries(struct Jewelry *jewelries) {
    for (int i = 0; i < 100; i++) {
        if (jewelries[i].id[0] != '\0') {
            printf("JewelryId: %s\n", jewelries[i].id);
            printf("JewelryName: %s\n", jewelries[i].name);
            printf("JewelryDescription: %s\n", jewelries[i].description);
            printf("JewelryType: %s\n", jewelries[i].type);
            printf("JewelryPrice: %d\n", jewelries[i].price);
            printf("JewelryStock: %d\n", jewelries[i].stock);
            printf("\n\n");
        }
    }
}

int extractId(const char *input) {
    int id;
    if (input[0] == 'J' && sscanf(input + 1, "%d", &id) == 1) {
        return id;
    }
    return -1;
}

void displayAllJewelry(struct Jewelry jewelries[], int count) {
    printf(
        "╔═════════╦════════════════════════════════╦═══════════════════════════════════════════════╦═══════════╦═══════╦═══════╗\n");
    printf(
        "║    ID   ║             Name               ║                   Description                 ║  Type     ║ Price ║ Stock ║\n");
    printf(
        "╠═════════╬════════════════════════════════╬═══════════════════════════════════════════════╬═══════════╬═══════╬═══════╣\n");
    for (int i = 0; i < count; i++) {
        printf("║ %7s ║ %30s ║ %45s ║ %9s ║ %5d ║ %5d ║\n",
               jewelries[i].id,
               jewelries[i].name,
               jewelries[i].description,
               jewelries[i].type,
               jewelries[i].price,
               jewelries[i].stock);
    }
    printf(
        "╚═════════╩════════════════════════════════╩═══════════════════════════════════════════════╩═══════════╩═══════╩═══════╝\n");
}

int compareById(const void *a, const void *b) {
    return strcmp(((struct Jewelry *) a)->id, ((struct Jewelry *) b)->id);
}

int compareByName(const void *a, const void *b) {
    return strcmp(((struct Jewelry *) a)->name, ((struct Jewelry *) b)->name);
}

int compareByType(const void *a, const void *b) {
    return strcmp(((struct Jewelry *) a)->type, ((struct Jewelry *) b)->type);
}

int compareByPrice(const void *a, const void *b) {
    return ((struct Jewelry *) a)->price - ((struct Jewelry *) b)->price;
}

int compareByStock(const void *a, const void *b) {
    return ((struct Jewelry *) a)->stock - ((struct Jewelry *) b)->stock;
}

void sortJewelries(struct JewelryArray *allJewelries, int sortSystem) {
    switch (sortSystem) {
        case 1:
            qsort(allJewelries->jewelries, allJewelries->count, sizeof(struct Jewelry), compareById);
            break;
        case 2:
            qsort(allJewelries->jewelries, allJewelries->count, sizeof(struct Jewelry), compareByName);
            break;
        case 3:
            qsort(allJewelries->jewelries, allJewelries->count, sizeof(struct Jewelry), compareByType);
            break;
        case 4:
            qsort(allJewelries->jewelries, allJewelries->count, sizeof(struct Jewelry), compareByPrice);
            break;
        case 5:
            qsort(allJewelries->jewelries, allJewelries->count, sizeof(struct Jewelry), compareByStock);
            break;
        default:
            break;
    }
}

void renderAddingPage(struct Jewelry jewelry) {
    printf(
        "╔═════════════╦═════════════════════════════════════════════════════════════════════════════════════════════════════════╗\n"
        "║ Name        ║ %-103s ║\n"
        "╟─────────────╫─────────────────────────────────────────────────────────────────────────────────────────────────────────╢\n"
        "║ Description ║ %-103s ║\n"
        "╟─────────────╫─────────────────────────────────────────────────────────────────────────────────────────────────────────╢\n"
        "║ Type        ║ %-103s ║\n"
        "╟─────────────╫─────────────────────────────────────────────────────────────────────────────────────────────────────────╢\n"
        "║ Price       ║ $%-102d ║\n"
        "╟─────────────╫─────────────────────────────────────────────────────────────────────────────────────────────────────────╢\n"
        "║ Stock       ║ %-103d ║\n"
        "╚═════════════╩═════════════════════════════════════════════════════════════════════════════════════════════════════════╝\n",
        strlen(jewelry.name) == 0 ? "(empty)" : jewelry.name,
        strlen(jewelry.description) == 0 ? "(empty)" : jewelry.description,
        strlen(jewelry.type) == 0 ? "(not set)" : jewelry.type,
        jewelry.price > 5000 || jewelry.price < 0 ? 0 : jewelry.price,
        jewelry.stock > 100 || jewelry.stock < 0 ? 0 : jewelry.stock
    );
}

void adminPage() {
    int state = 0; // 0 = home page; 1 = add; 2 = delete; 3 = view; 4 = exit

    while (1) {
        clearTerminal();
        switch (state) {
            case 0: {
                printHomeAdminPage();
                int choice;
                scanf("%d", &choice);
                getchar();
                state = choice;
                continue;
            }

            case 1: {
                int st = 0; // 0 = name; 1 = description; 2 = type; 3 = price; 4 = stock; 5 = finish
                while (1) {
                    struct Jewelry jewelryToAdd;
                    clearTerminal();
                    renderAddingPage(jewelryToAdd);
                    if (st == 0) {
                        printf("Enter Jewelry Name > ");
                        scanf("%[^\n]", jewelryToAdd.name);
                        getchar();

                        if (strlen(jewelryToAdd.name) < 6 || strlen(jewelryToAdd.name) > 20) {
                            printf("Name length must be between 6 and 20. Press any key to retry...");
                            getchar();
                            continue;
                        }

                        st++;
                        continue;
                    }

                    if (st == 1) {
                        printf("Enter Jewelry Description > ");
                        scanf("%[^\n]", jewelryToAdd.description);
                        getchar();

                        if (strlen(jewelryToAdd.description) < 10 || strlen(jewelryToAdd.description) > 50) {
                            printf("Description length must be between 10 and 50. Press any key to retry...");
                            getchar();
                            continue;
                        }

                        st++;
                        continue;
                    }

                    if (st == 2) {
                        printf("Enter Jewelry Type (Ring | Necklace | Bracelet) > ");
                        scanf("%s", jewelryToAdd.type);
                        getchar();

                        if (strcmp(jewelryToAdd.type, "Ring") != 0 && strcmp(jewelryToAdd.type, "Necklace") != 0 &&
                            strcmp(jewelryToAdd.type, "Bracelet") != 0) {
                            printf("Invalid type. Press any key to retry...");
                            getchar();
                            continue;
                        }

                        st++;
                        continue;
                    }

                    if (st == 3) {
                        printf("Enter the price (500-5000) > ");
                        scanf("%d", &jewelryToAdd.price);
                        while (getchar() != '\n') {
                        }

                        if (jewelryToAdd.price < 500 || jewelryToAdd.price > 5000) {
                            printf("Price must be between 500 and 5000. Press any key to retry...");
                            getchar();
                            continue;
                        }

                        st++;
                        continue;
                    }

                    if (st == 4) {
                        printf("Enter the quantity (0-100) > ");
                        scanf("%d", &jewelryToAdd.stock);
                        while (getchar() != '\n');

                        if (jewelryToAdd.stock < 0 || jewelryToAdd.stock > 100) {
                            printf("Quantity must be between 0 and 100. Press any key to retry...");
                            getchar();
                            continue;
                        }

                        st++;
                        continue;
                    }

                    if (st == 5) {
                        char id[6];
                        sprintf(id, "J%d", generate4Id());
                        strcpy(jewelryToAdd.id, id);
                        addData(jewelryToAdd.id, jewelryToAdd.name, jewelryToAdd.description, jewelryToAdd.type,
                                jewelryToAdd.price, jewelryToAdd.stock);
                        printf("Jewelry added successfully. Press any key to go back to admin menu...");
                        getchar();
                        state = 0;
                        st = 0;
                        break;
                    }
                }

                state = 0;
                break;
            }

            case 2: {
                char input[10];
                int id;

                while (1) {
                    clearTerminal();
                    struct JewelryArray allJewelries = getAllJewelry();
                    displayAllJewelry(allJewelries.jewelries, allJewelries.count);
                    printf("Enter the id of the jewelry to delete (e.g., J1234) [0 -> Exit to admin menu] > ");
                    fgets(input, sizeof(input), stdin);

                    if (input[strlen(input) - 1] == '\n') {
                        input[strlen(input) - 1] = '\0';
                    }

                    if (strcmp(input, "0") == 0) {
                        state = 0;
                        break;
                    }

                    id = extractId(input);

                    if (id == -1) {
                        printf("Invalid input. Press any key to retry...\n");
                        getchar();
                        continue;
                    }

                    int exists = isJewelryExists(input);
                    if (!exists) {
                        printf("Jewelry with id %s does not exist. Press any key to retry...\n", input);
                        getchar();
                        continue;
                    }

                    DeleteJewelry(id);

                    clearTerminal();
                    allJewelries = getAllJewelry();
                    displayAllJewelry(allJewelries.jewelries, allJewelries.count);
                    printf("Enter the id of the jewelry to delete (e.g., J1234) > %s", input);
                    printf("Jewelry deleted successfully. Press any key to go back to admin menu...");
                    getchar();
                    state = 0;
                    break;
                }

                break;
            }
            case 3: {
                struct JewelryArray allJewelries = getAllJewelry();
                int sortSystem = 0;
                // 0 = normal; 1 = by jewelry id; 2 = by name; 3 = by type; 4 = by price; 5 = by stock
                if (allJewelries.jewelries == NULL) {
                    printf("No data found. Press any key to go back to admin menu...");
                    getchar();
                    state = 0;
                    continue;
                }

                while (1) {
                    clearTerminal();
                    sortJewelries(&allJewelries, sortSystem);
                    printf(
                        "╔══════════════════════════════════════════════════════╗\n"
                        "║            View All Available Jewelries              ║\n"
                        "╚══════════════════════════════════════════════════════╝\n"
                    );

                    displayAllJewelry(allJewelries.jewelries, allJewelries.count);
                    printf(
                        "\n\nSort by:\n"
                        "%s1. JewelryID%s\n"
                        "%s2. Name%s\n"
                        "%s3. Category%s\n"
                        "%s4. Price%s\n"
                        "%s5. Stock%s\n"
                        "0. Back to Admin Page\n\n",
                        sortSystem == 1 ? ">> " : "", sortSystem == 1 ? " <<" : "",
                        sortSystem == 2 ? ">> " : "", sortSystem == 2 ? " <<" : "",
                        sortSystem == 3 ? ">> " : "", sortSystem == 3 ? " <<" : "",
                        sortSystem == 4 ? ">> " : "", sortSystem == 4 ? " <<" : "",
                        sortSystem == 5 ? ">> " : "", sortSystem == 5 ? " <<" : ""
                    );

                    printf("Enter your choice > ");
                    scanf("%d", &sortSystem);
                    getchar();
                    if (sortSystem < 0 || sortSystem > 5) {
                        printf("Invalid input. Press any key to retry...");
                        getchar();
                        continue;
                    }

                    if (sortSystem == 0) {
                        state = 0;
                        break;
                    }
                }
                break;
            }

            case 4: {
                exit(0);
            }

            default:
                break;
        }
    }
}

void userPage() {
    int state = 0; // 0 = home; 1 = shop
    while (1) {
        clearTerminal();
        switch (state) {
            case 0: {
                printf(
                    "Hello user!\n"
                    "How can I help you today?\n\n"
                    "1. Shop jewellery\n"
                    "0. Exit\n\n"
                    "Enter your choice > "
                );
                break;
            }

            case 1: {
                printf(
                    "╔═════════════════════════════════════════╗\n"
                    "║         View All Jewelry Menu           ║\n"
                    "╚═════════════════════════════════════════╝\n"
                );
                struct JewelryArray allJewelries = getAllJewelry();
                displayAllJewelry(allJewelries.jewelries, allJewelries.count);
                printf("Enter Jewelry ID to buy [0 -> Exit to main menu] > ");
                char input[6];
                scanf("%5s", input);
                if (input[0] == '0') {
                    state = 0;
                    continue;
                }
                // printf("First char is %c\n", input[0]);
                if (input[0] != 'J' && input[0] != 'j') {
                    printf("ID must start with J. Press any key to retry...");
                    getchar();
                    continue;
                }

                int exists = -1;
                for (int i = 0; i < allJewelries.count; i++) {
                    // printf("Comparing %s with %s\n", input, allJewelries.jewelries[i].id);
                    if (strcmp(allJewelries.jewelries[i].id, input) == 0) {
                        // printf("Found %s\n", allJewelries.jewelries[i].id);
                        exists = i;
                        break;
                    }
                }

                // printf("Exists: %d\n", exists);

                if (exists >= 0) {
                    changeJewelryStock(input, allJewelries.jewelries[exists].stock - 1);
                    printf("You bought %s. Press any key to go back to user menu...",
                           allJewelries.jewelries[exists].name);
                    getchar();
                    getchar();
                    state = 0;
                    continue;
                }

                printf("Jewelry with id %s does not exist. Press any key to retry...", input);
                getchar();
                getchar();
                continue;
            }
        }

        char input = getchar();
        getchar();
        if (input == '1') {
            state = 1;
        } else {
            exit(0);
        }
    }
}

int main(void) {
    int state = 0; // 0 = landing page, 1 = admin, 2 = user

    while (1) {
        clearTerminal();
        switch (state) {
            case 0:
                printLandingPage();
                break;
            case 1:
                adminPage();
                break;
            case 2:
                userPage();
                break;
            default:
                break;
        }

        char input[6];
        scanf("%s", input);
        getchar();
        if (strcmp(input, "Admin") == 0) {
            state = 1;
        } else if (strcmp(input, "User") == 0) {
            state = 2;
        } else {
            printf("Invalid input. Try again by pressing any key...");
            getchar();
        }
    }
}
