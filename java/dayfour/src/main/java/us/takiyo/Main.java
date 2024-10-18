package us.takiyo;

import us.takiyo.extensions.Master;
import us.takiyo.views.GameplayMenu;

public class Main extends Master {
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super();

        GameplayMenu gameplayMenu = new GameplayMenu(this, null);

        int state = 0;
        while (true) {
            this.clearTerminal(false);
            switch (state) {
                case 0: {
                    System.out.print("""
                            -- BLALBAL --
                            1. Play Game
                            2. Exit
                            >\s""");
                    int choice = this.getChoice();
                    if (choice == 1) state = 1;
                    else if (choice == 2) return;
                    else this.sendError("Invalid choice");
                    break;
                }

                case 1: {
                    gameplayMenu.startHandle();
                    return;
                }
            }
        }
    }


}