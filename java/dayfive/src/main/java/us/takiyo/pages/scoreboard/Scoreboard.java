package us.takiyo.pages.scoreboard;

import us.takiyo.Main;
import us.takiyo.controller.Player;
import us.takiyo.extensions.Master;
import us.takiyo.extensions.Page;
import us.takiyo.extensions.PlayerComparator;
import us.takiyo.extensions.TakiyoList;

public class Scoreboard extends Page {
    public Scoreboard() {
        super("scoreboard.scoreboard");
    }

    @Override
    public String execute(Main main) {
        if (main.players.isEmpty()) {
            Master.sendWithEnter("There's no player available. Let you be the first player here!");
        }
        main.players.sort(new PlayerComparator());
        System.out.printf("Scoreboard of %d players%n", main.players.size());
        System.out.println();
        System.out.printf("    %-20s | %-10s |%n", "Username", "Score");
        for (int i = 0; i < main.players.size(); i++) {
            Player player = main.players.get(i);
            System.out.printf("%d. %-20s | %-10d |%n", i + 1, player.getUsername(), player.getScore());
        }

        Master.waitForEnter();
        return "game.game";
    }
}
