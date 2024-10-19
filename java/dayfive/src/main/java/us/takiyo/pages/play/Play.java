package us.takiyo.pages.play;

import us.takiyo.Main;
import us.takiyo.extensions.Master;
import us.takiyo.extensions.Page;
import us.takiyo.managers.IOManager;

public class Play extends Page {
    public Play() {
        super("play.play");
    }

    @Override
    public String execute(Main main) {
        String[] choices = {"Login", "Register", "Back"};
        int response = IOManager.handleChoice(choices, () -> System.out.println("Login"));
        return response == 1 ? "play.login.login" : response == 2 ? "play.register.register" : "home";
    }
}
