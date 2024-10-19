package us.takiyo.extensions;

import us.takiyo.controller.Player;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2) {
        return p2.getScore() - p1.getScore();
    }
}
