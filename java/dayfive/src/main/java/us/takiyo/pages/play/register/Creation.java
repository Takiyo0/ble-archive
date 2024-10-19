package us.takiyo.pages.play.register;

import us.takiyo.Main;
import us.takiyo.controller.Player;
import us.takiyo.extensions.Master;
import us.takiyo.extensions.Page;
import us.takiyo.managers.IOManager;
import us.takiyo.controller.Character;

public class Creation extends Page {
    public Creation() {
        super("play.register.creation");
    }

    @Override
    public String execute(Main main) {
        String[] roles = new String[main.baseCharacters.size()];
        for (int i = 0; i < roles.length; i++) roles[i] = main.baseCharacters.get(i).getType();
        int response = IOManager.handleRoleSelection(roles, () -> System.out.println("PICK ROLE"));
        String username = IOManager.getUsername(String.format("Enter the %s's name", roles[response - 1]), 5, 15, null);
        if (username == null) {
            main.setCurrentPlayer(null);
            return "play.play";
        }
        Character baseCharacter = main.baseCharacters.get(Character::getType, roles[response - 1]);
        Player player = main.players.get(Player::getUsername, main.getCurrentPlayer());
        Character newCharacter = new Character(baseCharacter, username);
        player.setCharacter(newCharacter);
        Master.sendWithEnter(String.format("You invited %s as your %s with element of %s", username, roles[response - 1], newCharacter.getElement()));
        return "game.game";
    }
}
