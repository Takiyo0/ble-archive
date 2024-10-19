package us.takiyo.pages;

import us.takiyo.Main;
import us.takiyo.extensions.Page;
import us.takiyo.managers.IOManager;

public class Home extends Page {
    public Home() {
        super("home");
    }

    @Override
    public String execute(Main main) {
        String[] choices = {"Play", "Rules", "Scoreboard", "Exit"};
        int response = IOManager.handleChoice(choices, () -> System.out.println("LOST REALM"));
        if (response == 4) {
            System.exit(0);
        }
        return response == 1 ? "play.play" : response == 2 ? "rule.rule" : response == 3 ? "scoreboard.scoreboard" : "";
    }
}
