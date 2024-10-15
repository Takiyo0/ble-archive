package us.takiyo;

import us.takiyo.interfaces.Category;
import us.takiyo.interfaces.Item;
import us.takiyo.managers.ItemManager;

import java.io.IOException;
import java.util.*;

public class Main {
    Scanner scanner = new Scanner(System.in);
    ItemManager itemManager = new ItemManager();

    public static void main(String[] args) {
        new Main();
    }

    public void clearTerminal() {
        for (int i = 0; i < 50; ++i) {
            System.out.println();
        }
    }

    public void waitForEnter() {
        System.out.println("Press ENTER to continue...");
        try {
            int a = System.in.read();
        } catch (IOException ignored) {
        }
    }

    public Main() {
        int state = 0;
        while (true) {
            this.clearTerminal();
            switch (state) {
                case 0: {
                    System.out.println("YocKaroo!");
                    System.out.print(
                            "Choose what you want to do\n" +
                                    "1. Create new category\n" +
                                    "2. Insert item\n" +
                                    "3. View all item\n" +
                                    "4. View items by category\n" +
                                    "5. Update category\n" +
                                    "6. Update item\n" +
                                    "7. Delete category\n" +
                                    "8. Delete item\n" +
                                    "0. Exit\n[0-8] > ");
                    int choice = this.scanner.nextInt();
                    this.scanner.nextLine();
                    if (choice == 0) {
                        System.out.println("Bye bye!");
                        return;
                    }
                    if (choice < 1 || choice > 8) {
                        System.out.println("Choice out of range [0-8]");
                        this.waitForEnter();
                        continue;
                    }
                    state = choice;
                    break;
                }

                case 1: {
                    this.CreateNewCategory();
                    state = 0;
                    break;
                }

                case 2: {
                    this.InsertNewItem();
                    state = 0;
                    break;
                }

                case 3: {
                    this.ViewAllItems();
                    state = 0;
                    break;
                }

                case 4: {
                    this.ViewItemsByCategory();
                    state = 0;
                    break;
                }

                case 5: {
                    this.UpdateCategory();
                    state = 0;
                    break;
                }

                case 6: {
                    this.UpdateItem();
                    state = 0;
                    break;
                }

                case 7: {
                    this.DeleteCategory();
                    state = 0;
                    break;
                }

                case 8: {
                    this.DeleteItem();
                    state = 0;
                    break;
                }
            }
        }
    }

    public void PrintItemTable(List<Item> items) {
        System.out.printf("--------------------------------%n");
        System.out.printf("| %-7s | %-15s |%n", "Id", "Item");
        System.out.printf("--------------------------------%n");

        for (Item item : items) {
            System.out.printf("| %-7s | %-15s |%n", item.Id, item.Name);
        }
        System.out.printf("--------------------------------%n");
    }

    public void PrintCategoryTable(List<Category> categories) {
        System.out.printf("--------------------------------%n");
        System.out.printf("| %-7s | %-15s |%n", "Id", "Category");
        System.out.printf("--------------------------------%n");

        for (Category category : categories) {
            System.out.printf("| %-7s | %-15s |%n", category.Id, category.Name);
        }
        System.out.printf("--------------------------------%n");
    }


    public void CreateNewCategory() {
        System.out.println("Create new category");
        System.out.println("============================");
        int state = 0; // 0 = name; 1 = number items to add; 2 = new items;
        int targetItems = 0;

        Category category = new Category();

        while (true) {
            clearTerminal();
            switch (state) {
                case 0: {
                    System.out.print("Insert category name [must be at least 4 characters | 0 to go back]\n> ");
                    String in = this.scanner.nextLine();
                    if (Objects.equals(in, "0")) {
                        return;
                    }
                    if (in.length() < 4) {
                        System.out.println("Category name must be at least 4 characters!");
                        waitForEnter();
                        continue;
                    }
                    category.Name = in;
                    state = 1;
                    break;
                }

                case 1: {
                    System.out.println("Insert number of items you want to insert now [at least 1]\n> ");
                    int count = this.scanner.nextInt();
                    if (count < 1) {
                        System.out.println("Number must be at least 1");
                        waitForEnter();
                        continue;
                    }
                    targetItems = count;
                    state = 2;
                    break;
                }

                case 2: {
                    int current = 0;
                    while (current < targetItems) {
                        clearTerminal();
                        System.out.println("Insert new item name [must be at least 4 characters]\n> ");
                        String newName = this.scanner.nextLine();
                        if (newName.length() < 4) {
                            System.out.println("Item name must be at least 4 characters");
                            waitForEnter();
                            continue;
                        }
                        this.itemManager.AddItem(category.Id, newName);
                        current++;
                    }

                    System.out.println("Successfully added category!");
                    System.out.printf("Id: %s Category: %s", category.Id, category.Name);
                    this.PrintItemTable(category.Items);
                    waitForEnter();
                    return;
                }
            }
        }
    }

    public void InsertNewItem() {
        System.out.println("Insert Item");
        System.out.println("============================");
        // render table here
        Category category = null;
        int targetItems = 0;
        int state = 0; // 0 = ask for category id
        int itemCountBefore = 0;
        while (true) {
            clearTerminal();
            switch (state) {
                case 0: {
                    System.out.println("Insert category ID [0 to go back]\n> ");
                    String in = this.scanner.nextLine();
                    if (Objects.equals(in, "0")) {
                        return;
                    }
                    category = itemManager.GetCategory(in);
                    if (category == null) {
                        System.out.println("Category with ID " + in + " not found.");
                        waitForEnter();
                        continue;
                    }
                    itemCountBefore = category.Items.size();
                    state = 1;
                    break;
                }

                case 1: {
                    System.out.println("Insert number of items you want to insert [at least 1]\n> ");
                    int count = this.scanner.nextInt();
                    if (count < 1) {
                        System.out.println("Number must be at least 1");
                        waitForEnter();
                        continue;
                    }
                    targetItems = count;
                    state = 2;
                    break;
                }

                case 2: {
                    int current = 0;
                    while (current < targetItems) {
                        clearTerminal();
                        System.out.println("Insert new item name [must be at least 4 characters]\n> ");
                        String newName = this.scanner.nextLine();
                        if (newName.length() < 4) {
                            System.out.println("Item name must be at least 4 characters");
                            waitForEnter();
                            continue;
                        }
                        this.itemManager.AddItem(category.Id, newName);
                        current++;
                    }

                    System.out.printf("Successfully added %d items!", this.itemManager.GetItemCount(category.Id) - itemCountBefore);
                    System.out.printf("Id: %s Category: %s", category.Id, category.Name);
                    this.PrintItemTable(category.Items);
                    waitForEnter();
                    return;
                }
            }
        }
    }

    public void ViewAllItems() {
        System.out.println("View all items");
        System.out.println("========================");

        for (Map.Entry<String, Category> entry : this.itemManager.items.entrySet()) {
            Category category = entry.getValue();
            System.out.printf("Id: %s Category: %s", category.Id, category.Name);
            this.PrintItemTable(category.Items);
            System.out.println("%n%n%n");
        }

        waitForEnter();
    }


    public void ViewItemsByCategory() {
        while (true) {
            clearTerminal();
            System.out.println("View items by category");
            System.out.println("=========================");
            this.PrintCategoryTable(new ArrayList<>(this.itemManager.items.values()));
            System.out.println("Insert category ID [0 to go back]\n> ");
            String target = this.scanner.nextLine();
            if (Objects.equals(target, "0")) {
                return;
            }
            Category category = this.itemManager.GetCategory(target);
            if (category == null) {
                System.out.println("Category with ID " + target + " is not found");
                waitForEnter();
                continue;
            }
            this.PrintItemTable(category.Items);
            waitForEnter();
            break;
        }
    }

    public void UpdateCategory() {
        int state = 0; // 0 = ask id; 1 = update name;
        Category category = null;
        while (true) {
            clearTerminal();
            System.out.println("Update category");
            System.out.println("=========================");
            this.PrintCategoryTable(new ArrayList<>(this.itemManager.items.values()));

            switch (state) {
                case 0: {
                    System.out.println("Insert category ID [0 to go back]\n> ");
                    String target = this.scanner.nextLine();
                    if (Objects.equals(target, "0")) {
                        return;
                    }
                    category = this.itemManager.GetCategory(target);
                    if (category == null) {
                        System.out.println("Category with ID " + target + " is not found");
                        waitForEnter();
                        continue;
                    }
                    state = 1;
                    break;
                }

                case 1: {
                    System.out.println("Insert category name [must be at least 4 characters | 0 to go back]\n> ");
                    String name = this.scanner.nextLine();
                    if (Objects.equals(name, "0")) {
                        return;
                    }
                    if (name.length() < 4) {
                        System.out.println("Category name must be at least 4 characters");
                        waitForEnter();
                        continue;
                    }
                    category.Name = name;
                    System.out.println("Successfully updated category!");
                    System.out.printf("Id: %s Category: %s", category.Id, category.Name);
                    this.PrintItemTable(category.Items);
                    waitForEnter();
                    break;
                }
            }
        }
    }

    public void UpdateItem() {
        int state = 0; // 0 = ask id; 1 = update name;
        Category category = null;
        Item item = null;
        while (true) {
            clearTerminal();
            System.out.println("Update item");
            System.out.println("=========================");

            switch (state) {
                case 0: {
                    this.PrintCategoryTable(new ArrayList<>(this.itemManager.items.values()));
                    System.out.println("Insert category ID [0 to go back]\n> ");
                    String target = this.scanner.nextLine();
                    if (Objects.equals(target, "0")) {
                        return;
                    }
                    category = this.itemManager.GetCategory(target);
                    if (category == null) {
                        System.out.println("Category with ID " + target + " does not exists");
                        waitForEnter();
                        continue;
                    }
                    state = 1;
                    break;
                }

                case 1: {
                    this.PrintItemTable(category.Items);
                    System.out.println("Insert Item Id [0 to go back]\n> ");
                    String id = this.scanner.nextLine();
                    if (Objects.equals(id, "0")) {
                        return;
                    }
                    item = this.itemManager.GetItem(category.Items, id);
                    if (item == null) {
                        System.out.println("Item with ID " + id + " does not exists");
                        waitForEnter();
                        continue;
                    }
                    state = 2;
                    break;
                }

                case 2: {
                    System.out.println("Insert new item name [must be at least 4 characters | 0 to go back]\n> ");
                    String prevName = item.Name;
                    String name = this.scanner.nextLine();
                    if (Objects.equals(name, "0")) {
                        return;
                    }
                    if (name.length() < 4) {
                        System.out.println("Category name must be at least 4 characters");
                        waitForEnter();
                        continue;
                    }
                    item.Name = name;
                    System.out.printf("Successfully updated name from %s to %s!", prevName, name);
                    System.out.printf("Id: %s Category: %s", category.Id, category.Name);
                    this.PrintItemTable(category.Items);
                    waitForEnter();
                }
            }
        }
    }

    public int GetNumber() {
        try {
            return Integer.parseInt(this.scanner.nextLine());
        } catch (Exception ignored) {
            return -1;
        }
    }

    public void DeleteCategory() {
        while (true) {
            clearTerminal();
            System.out.println("Delete category");
            System.out.println("=========================");
            this.PrintCategoryTable(new ArrayList<>(this.itemManager.items.values()));
            System.out.println("Insert category ID [0 to go back]\n> ");
            String target = this.scanner.nextLine();
            if (Objects.equals(target, "0")) {
                return;
            }
            Category category = this.itemManager.GetCategory(target);
            if (category == null) {
                System.out.println("Category with ID " + target + " is not found");
                waitForEnter();
                continue;
            }
            this.itemManager.DeleteCategory(target);
            waitForEnter();
            break;
        }
    }

    public void DeleteItem() {
        int state = 0; // 0 = ask id; 1 = delete name;
        Category category = null;
        Item item;
        while (true) {
            clearTerminal();
            System.out.println("Delete item");
            System.out.println("=========================");

            switch (state) {
                case 0: {
                    this.PrintCategoryTable(new ArrayList<>(this.itemManager.items.values()));
                    System.out.println("Insert category ID [0 to go back]\n> ");
                    String target = this.scanner.nextLine();
                    if (Objects.equals(target, "0")) {
                        return;
                    }
                    category = this.itemManager.GetCategory(target);
                    if (category == null) {
                        System.out.println("Category with ID " + target + " does not exists");
                        waitForEnter();
                        continue;
                    }
                    state = 1;
                    break;
                }

                case 1: {
                    this.PrintItemTable(category.Items);
                    System.out.println("Insert Item Id [0 to go back]\n> ");
                    String id = this.scanner.nextLine();
                    if (Objects.equals(id, "0")) {
                        return;
                    }
                    item = this.itemManager.GetItem(category.Items, id);
                    if (item == null) {
                        System.out.println("Item with ID " + id + " does not exists");
                        waitForEnter();
                        continue;
                    }
                    String itemName = item.Name;
                    this.itemManager.RemoveItem(category.Id, id);
                    System.out.printf("Successfully removed " + itemName + "!");
                    System.out.printf("Id: %s Category: %s", category.Id, category.Name);
                    this.PrintItemTable(category.Items);
                    waitForEnter();
                    break;
                }
            }
        }
    }
}