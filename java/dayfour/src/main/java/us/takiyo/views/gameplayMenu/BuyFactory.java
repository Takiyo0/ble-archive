package us.takiyo.views.gameplayMenu;

import us.takiyo.Main;
import us.takiyo.controllers.Player;
import us.takiyo.extensions.ResourceExtension;
import us.takiyo.extensions.ViewExtension;
import us.takiyo.interfaces.Factory;
import us.takiyo.interfaces.Money;
import us.takiyo.interfaces.View;

import java.util.Arrays;
import java.util.Scanner;

public class BuyFactory extends ViewExtension {
    Player player;
    Main main;

    public BuyFactory(Main main, Player player) {
        super(ViewType.Normal);
        this.player = player;
        this.main = main;
        this.addOption();
    }

    void addOption() {
        View page = new View(0);
        page.function = (currentState) -> {
            int target = 0, brk = 0;
            while (true) {
                this.main.clearTerminal(false);
                String[] factories = {"Wood Factory", "Stone Factory", "Gold Factory"};
                this.player.printResources();

                String reset = "\u001B[0m";
                String brightCyan = "\u001B[96m";
                String yellow = "\u001B[93m";
                String brightGreen = "\u001B[92m";
                String bold = "\u001B[1m";

                System.out.println(brightCyan + "╔═══════════════════════════════════════════════════════════╗");
                System.out.printf("║%-20s" + bold + "🛠️  Buy Factory  🛠️" + reset + brightCyan + "%-19s║\n", "", "");
                System.out.println(brightCyan + "╠════╦══════════════════════════════════════════════════════╣");
                System.out.printf("║ No ║ " + yellow + "%-25sType%-24s" + reset + brightCyan + "║\n", "", "");
                System.out.println(brightCyan + "╠════╬══════════════════════════════════════════════════════╣");

                for (int i = 0; i < this.player.getResourceCount(); i++) {
                    ResourceExtension resource = this.player.getResourceByIndex(i);
                    System.out.printf("║ %-2d ║ " + yellow + "%-52s" + brightCyan + " ║\n", i + 1, resource.getName().toUpperCase() + " FACTORY");
                }

                System.out.println(brightCyan + "╠════╩══════════════════════════════════════════════════════╣");
                System.out.println("║ " + brightGreen + "To buy any factory, you need: 10 wood, 10 stone, 10 gold." + reset + brightCyan + " ║");
                System.out.println(brightCyan + "╚═══════════════════════════════════════════════════════════╝" + reset);
                System.out.println(brightCyan + "Choose factory [1-" + this.player.getResourceCount() + "], [0] to go back" + reset);
                System.out.print(" > ");

                int choice = this.main.getChoice();
                if (choice == 0) {
                    brk = 1;
                    break;
                }
                if (choice < 1 || choice > 3) {
                    this.main.sendError("Invalid factory name");
                    continue;
                }

                int state = 0; // 0 = name; 1 = additional data;
                Factory factory = new Factory(choice == 1 ? Factory.FactoryType.Wood : choice == 2 ? Factory.FactoryType.Stone : Factory.FactoryType.Gold);
                Money[] costs = new Money[3];
                costs[0] = new Money("wood", 10);
                costs[1] = new Money("stone", 10);
                costs[2] = new Money("gold", 10);

                this.main.clearTerminal(true);
                System.out.printf("Enter more details about your new %s\n", factories[choice - 1]);
                while (state != 2) {
                    this.main.clearTerminal(true);
                    switch (state) {
                        case 0: {
                            System.out.print("Input factory name [5-15 characters] (inclusive)\n> ");
                            String name = new Scanner(System.in).nextLine();
                            if (name.length() < 5 || name.length() > 15) {
                                this.main.sendError("Invalid factory name");
                                continue;
                            }
                            if (!this.player.isMoneyEnough(costs)) {
                                this.main.sendError("You don't have enough money");
                                continue;
                            }
                            factory.setName(name);
                            state++;
                            break;
                        }

                        case 1: {
                            boolean keep = true;
                            while (keep) {
                                switch (choice - 1) {
                                    case 0: {
                                        String[] woodTypes = {"Teak", "Mahogany", "Oak"};
                                        System.out.printf("Input wood type [%s] (inclusive)\n> ", String.join(" | ", woodTypes));
                                        String type = new Scanner(System.in).nextLine();
                                        if (!Arrays.asList(woodTypes).contains(type)) {
                                            this.main.sendError("Invalid wood type");
                                            continue;
                                        }
                                        factory.setAdds(type);
                                        keep = false;
                                        break;
                                    }
                                    case 1: {
                                        String[] woodTypes = {"Granite", "Marble", "Limestone"};
                                        System.out.printf("Input stone type [%s] (inclusive)\n> ", String.join(" | ", woodTypes));
                                        String type = new Scanner(System.in).nextLine();
                                        if (!Arrays.asList(woodTypes).contains(type)) {
                                            this.main.sendError("Invalid stone type");
                                            continue;
                                        }
                                        factory.setAdds(type);
                                        keep = false;
                                        break;
                                    }
                                    case 2: {
                                        System.out.print("Input gold purity [18-24] (inclusive)\n> ");
                                        String type = new Scanner(System.in).nextLine();
                                        if (this.main.parseInt(type) < 18 || this.main.parseInt(type) > 24) {
                                            this.main.sendError("Invalid gold purity");
                                            continue;
                                        }
                                        factory.setAdds(type);
                                        keep = false;
                                        break;
                                    }
                                }
                            }
                            this.player.addFactory(factory);
                            this.player.onBuyFactory(factory);
                            System.out.printf("Congratulation on your new %s!\n", factories[choice - 1]);
                            this.main.waitForEnter();
                            state = 2;
                            break;
                        }
                    }
                }
                break;
            }
            return "" + target + brk;
        };

        this.options.add(page);
    }
}
