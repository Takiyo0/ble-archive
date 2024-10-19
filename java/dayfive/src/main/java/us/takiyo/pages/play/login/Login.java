package us.takiyo.pages.play.login;

import us.takiyo.Main;
import us.takiyo.controller.Player;
import us.takiyo.extensions.Master;
import us.takiyo.extensions.Page;
import us.takiyo.managers.EncryptionManager;
import us.takiyo.managers.IOManager;

public class Login extends Page {
    public Login() {
        super("play.login.login");
    }

    @Override
    public String execute(Main main) {
        System.out.println("Login");
        String username = IOManager.getUsername("Enter your username", 5, 15, null);
        if (username == null) return "play.play";
        Player player = main.players.get(Player::getUsername, username);
        if (player == null) {
            Master.sendError("That username doesn't exists. Check the naming or register");
            return "play.login.login";
        }

        String password = IOManager.getPassword("Enter your password", 5, 15);
        if (password == null) return "play.play";
        if (!player.comparePassword(password)) {
            Master.sendError("Wrong password. Try again.");
            return "play.login.login";
        }
        main.setCurrentPlayer(player.getUsername());
        if (player.getCharacter() == null) return "play.register.creation";
        return "game.game";
    }
}
