package us.takiyo.pages.game;

import us.takiyo.Main;
import us.takiyo.controller.Character;
import us.takiyo.controller.Player;
import us.takiyo.extensions.Master;
import us.takiyo.extensions.Page;
import us.takiyo.managers.IOManager;

public class Game extends Page {
    public Game() {
        super("game.game");
    }

    @Override
    public String execute(Main main) {
        String[] choices = {"Train (Gain EXP)", "Adventure (Fight enemies in floors)", "Logout"};
        Player player = main.players.get(Player::getUsername, main.getCurrentPlayer());
        if (player == null) {
            Master.sendError("Either you are not logged in or something went wrong with the state management");
            return "play.play";
        }
        Character character = player.getCharacter();
        if (character == null) {
            Master.sendError("You haven't picked your character yet. Redirecting you to character selection");
            return "play.register.creation";
        }

        int response = IOManager.handleChoice(choices, () ->
                System.out.printf(
                        "Welcome, %s!\n\n" +
                                "Character's stats:\n" +
                                "Name : %s\n" +
                                "Element : %s\n" +
                                "Base Attack : %.2f\n" +
                                "Health : %.2f\n" +
                                "Speed : %.2f\n" +
                                "Mana : %.2f\n" +
                                "Level : %d\n" +
                                "EXP : %.1f\n" +
                                "%s : %.2f\n\n" +
                                "Your progress:\n" +
                                "Current floor : %d\n" +
                                "Current score : %d\n\n" +
                                "What would you like to do?\n",
                        player.getUsername(),
                        character.getName(),
                        character.getElement(),
                        character.getBaseAtk(),
                        character.getHealth(),
                        character.getSpeed(),
                        character.getMana(),
                        character.getLevel(),
                        character.getExp(),
                        character.getSpecialAtr().getType(),
                        character.getSpecialAtr().getValue(),
                        player.getFloor(),
                        player.getScore()
                ));
        if (response == 3) {
            main.setCurrentPlayer(null);
            return "home";
        }

        return response == 1 ? "game.train" : "game.adventure";
    }
}
