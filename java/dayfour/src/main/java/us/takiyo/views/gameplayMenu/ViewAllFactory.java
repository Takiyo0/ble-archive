package us.takiyo.views.gameplayMenu;

import us.takiyo.Main;
import us.takiyo.controllers.Player;
import us.takiyo.extensions.ViewExtension;
import us.takiyo.interfaces.Factory;
import us.takiyo.interfaces.View;

public class ViewAllFactory extends ViewExtension {
    Player player;
    Main main;

    public ViewAllFactory(Main main, Player player) {
        super(ViewType.Normal);
        this.player = player;
        this.main = main;
        this.addOption();
    }

    void addOption() {
        View page = new View(0);
        page.function = (currentState) -> {
            int target = 0, brk = 0;
            System.out.println("======================================================================================");
            System.out.printf("| %-20s | %-10s | %-20s | %-23s |\n", "Name", "Type", "Production Rate/day", "Special Attributes");
            System.out.println("======================================================================================");
            for (Factory factory : this.player.getFactories())
                System.out.printf("| %-20s | %-10s | %-20s | %-23s |\n", factory.getName(), factory.getType().toString().toLowerCase(), factory.getProductionRate() + " " + factory.getType().toString().toLowerCase() + "s/day", factory.getAdds());
            System.out.println("======================================================================================");
            this.main.waitForEnter();
            return "" + target + brk;
        };

        this.options.add(page);
    }
}
