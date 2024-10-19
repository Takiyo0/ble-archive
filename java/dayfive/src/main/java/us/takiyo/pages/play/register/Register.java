package us.takiyo.pages.play.register;

import us.takiyo.Main;
import us.takiyo.controller.Player;
import us.takiyo.extensions.Master;
import us.takiyo.extensions.Page;
import us.takiyo.managers.EncryptionManager;
import us.takiyo.managers.IOManager;

public class Register extends Page {
    public Register() {
        super("play.register.register");
    }

    @Override
    public String execute(Main main) {
        System.out.println("Register");
        String username = IOManager.getUsername("Enter your new username", 5, 15, main.players);
        if (username == null) return "play.play";
        String password = IOManager.getPassword("Enter your new password", 5, 15);
        if (password == null) return "play.play";
        main.players.add(new Player(username, EncryptionManager.Encrypt(password)));
        Master.sendWithEnter(String.format("Welcome to LOST REALM, %s! You can login now", username));
        return "play.play";
    }
}
