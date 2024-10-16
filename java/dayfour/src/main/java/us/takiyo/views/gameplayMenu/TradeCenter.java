package us.takiyo.views.gameplayMenu;

import us.takiyo.Main;
import us.takiyo.controllers.Player;
import us.takiyo.extensions.ResourceExtension;
import us.takiyo.extensions.ViewExtension;
import us.takiyo.interfaces.Money;
import us.takiyo.interfaces.TradePair;
import us.takiyo.interfaces.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class TradeCenter extends ViewExtension {
    Player player;
    Main main;

    public TradeCenter(Main main, Player player) {
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
                this.player.printResources();
                int offers = ThreadLocalRandom.current().nextInt(3, 6);
                TradePair[] pairs = this.player.getTradePairs(this.player.getDay()) == null ? new TradePair[offers] : this.player.getTradePairs(this.player.getDay());
                if (this.player.getTradePairs(this.player.getDay()) == null) {
                    for (int i = 0; i < offers; i++) {
                        ResourceExtension resource = this.player.getResourceByIndex(ThreadLocalRandom.current().nextInt(this.player.getResourceCount()));
                        if (Objects.equals(resource.getName(), "money")) {
                            i--;
                            continue;
                        }
                        int price = ThreadLocalRandom.current().nextInt(5, 20);
                        pairs[i] = new TradePair(resource.getName(), price);
                    }
                    this.player.addTradePairs(this.player.getDay(), pairs);
                }

                String reset = "\u001B[0m";
                String green = "\u001B[32m";
                String yellow = "\u001B[33m";
                String cyan = "\u001B[36m";
                String magenta = "\u001B[35m";
                String bold = "\u001B[1m";
                String darkGrey = "\u001B[90m";

                System.out.printf(magenta + "╔═══════════════════════════════════════╗\n" + reset);
                System.out.printf(magenta + "║ %8s" + cyan + bold + "🌟 Trade Center Day %d" + magenta + " %8s║\n", "", this.player.getDay(), "");
                System.out.printf(magenta + "╠═══════════════════════════════════════╣\n" + reset);
                System.out.printf(magenta + "║" + yellow + " %-5s " + magenta + "║" + yellow + " %-13s " + magenta + "║" + yellow + " %-13s " + magenta + "║\n" + reset, "No", "Cost", "Reward");
                System.out.printf(magenta + "╠═══════╦═══════════════╦═══════════════╣\n" + reset);

                for (int i = 0; i < pairs.length; i++) {
                    TradePair pair = pairs[i];
                    if (pair.bought) {
                        System.out.printf(magenta + "║" + darkGrey + " %-5d " + magenta + "║" + darkGrey + " %-13s " + magenta + "║" + darkGrey + " %-13s " + magenta + "║\n" + reset,
                                i + 1,
                                pair.quantity + " " + pair.itemType.toUpperCase(),
                                "SOLD OUT");
                    }
                    System.out.printf(magenta + "║" + green + " %-5d " + magenta + "║" + cyan + " %-13s " + magenta + "║" + green + " %-13s " + magenta + "║\n" + reset,
                            i + 1,
                            pair.quantity + " " + pair.itemType.toUpperCase(),
                            pair.moneyReward + " MONEY");
                }

                System.out.printf(magenta + "╚═══════╩═══════════════╩═══════════════╝\n" + reset);
                int notAvailableCount = 0;
                for (int i = 0; i < pairs.length; i++) if (pairs[i].bought) notAvailableCount++;

                int[] available = new int[pairs.length - notAvailableCount];
                int index = 0;
                for (int i = 0; i < pairs.length; i++) if (!pairs[i].bought) available[index++] = i + 1;

                System.out.printf(magenta + "Choose offer [%s; 0 to go back]\n> " + reset, this.parseRanges(available));
                int choice = this.main.getChoice();
                if (choice < 0 || choice > pairs.length) {
                    this.main.sendError("Invalid input");
                    continue;
                }
                if (choice == 0) {
                    break;
                }
                TradePair pair = pairs[choice - 1];
                if (pair.bought) {
                    this.main.sendError("You already bought this today. Come back again tomorrow!");
                    continue;
                }
                if (!player.isMoneyEnough(new Money[]{new Money(pair.itemType, pair.quantity)})) {
                    this.main.sendError("Oops, you don't have enough money");
                    continue;
                }
                this.player.useResource(new Money[]{new Money(pair.itemType, -pair.quantity), new Money("money", pair.moneyReward)});
                pair.bought = true;
                System.out.printf("Nice to trade with you! (you traded %s with your %s) Come back later\n", pair.moneyReward + " money", pair.quantity + " " + pair.itemType);
                this.main.waitForEnter();
                break;
            }
            return "" + target + brk;
        };

        this.options.add(page);
    }

    private String parseRanges(int[] numbers) {
        Arrays.sort(numbers);

        List<String> ranges = new ArrayList<>();
        int start = numbers[0];
        int end = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] == end + 1) {
                end = numbers[i];
            } else {
                if (start == end) {
                    ranges.add(String.valueOf(start));
                } else {
                    ranges.add(start + "-" + end);
                }
                start = numbers[i];
                end = numbers[i];
            }
        }

        if (start == end) {
            ranges.add(String.valueOf(start));
        } else {
            ranges.add(start + "-" + end);
        }

        return String.join(",", ranges);
    }
}
