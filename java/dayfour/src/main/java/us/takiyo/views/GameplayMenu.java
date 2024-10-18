package us.takiyo.views;

import us.takiyo.Main;
import us.takiyo.controllers.Player;
import us.takiyo.extensions.ViewExtension;
import us.takiyo.interfaces.View;
import us.takiyo.views.gameplayMenu.*;

public class GameplayMenu extends ViewExtension {
    Main main;
    Player player = new Player();

    public GameplayMenu(Main main, Player player) {
        super(ViewType.NeedChoices);
        this.main = main;
        if (player != null) this.player = player;
        this.addOption();
    }

    void addOption() {
        View home = new View(0);
        home.function = (currentState) -> {
            int target = 0;
            int brk = 0;
            while (true) {
                this.main.clearTerminal(false);
                System.out.printf("Day %d\n", player.getDay());
                this.player.printResources();
                System.out.print("""
                        Actions:
                        1. Finish day
                        2. Buy factory
                        3. View all factory
                        4. Trade center
                        5. Exit
                        Choose action
                        >\s""");
                int choice = this.main.getChoice();
                if (choice < 1 || choice > 5) {
                    this.main.sendError("Invalid choice");
                    continue;
                }
                target = choice;
                break;
            }
            return "" + target + brk;
        };

        View finishDay = new View(1);
        finishDay.extensionPage = new FinishDay(this.main, this.player);

        View buyFactory = new View(2);
        buyFactory.extensionPage = new BuyFactory(this.main, this.player);

        View viewAllFactory = new View(3);
        viewAllFactory.extensionPage = new ViewAllFactory(this.main, this.player);

        View tradeCenter = new View(4);
        tradeCenter.extensionPage = new TradeCenter(this.main, this.player);

        View exit = new View(5);
        exit.extensionPage = new GameOver(this.main, this.player);

        this.options.add(home);
        this.options.add(finishDay);
        this.options.add(buyFactory);
        this.options.add(viewAllFactory);
        this.options.add(tradeCenter);
        this.options.add(exit);
    }
}
