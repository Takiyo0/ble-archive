package us.takiyo.interfaces;

import us.takiyo.Main;
import us.takiyo.controllers.Player;
import us.takiyo.extensions.ViewExtension;

import java.util.function.Function;

public class View {
    public int choice;
    public Function<Integer, String> function;
    // is used when there's only one choice
    public ViewExtension extensionPage;

    public View(int choice) {
        this.choice = choice;
    }
}