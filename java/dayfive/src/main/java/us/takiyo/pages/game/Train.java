package us.takiyo.pages.game;

import us.takiyo.Main;
import us.takiyo.controller.Character;
import us.takiyo.controller.Player;
import us.takiyo.extensions.Master;
import us.takiyo.extensions.Page;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Train extends Page {
    public Train() {
        super("game.train");
    }

    @Override
    public String execute(Main main) {
        Player player = main.players.get(Player::getUsername, main.getCurrentPlayer());
        Character character = player == null ? null : player.getCharacter();
        if (character == null) {
            Master.sendError("You haven't selected your character yet. Redirecting to character creation");
            return "play.register.creation";
        }
        // perhaps animate this later...
        System.out.printf("You decided to train your %s%n", character.getType());
        Master.sleepSecond(1);
        double exp = ThreadLocalRandom.current().nextDouble(0.0, 0.9) * (50 * character.getLevel());
        boolean leveledUp = character.addExp(player, exp);
        System.out.printf("%s gained %.2f exp!%n", character.getName(), exp);
        if (leveledUp)
            System.out.printf("%nHoorayy!! %s leveled up to %d!%n", character.getName(), character.getLevel());
        Master.waitForEnter();
        return "game.game";
    }
}
