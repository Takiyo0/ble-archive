package us.takiyo.players;

import us.takiyo.Main;
import us.takiyo.extensions.Player;
import us.takiyo.extensions.Role;
import us.takiyo.extensions.TakiyoList;

import java.util.concurrent.ThreadLocalRandom;

public class Seer extends Player {
    public Seer(String name, Main main) {
        super(name, new Role("seer", false), main, 1);
    }

    @Override
    public Seer clone() {
        return (Seer) super.clone();
    }

    @Override
    public int performNightAction(int ind) {
        int random = 0;
        do {
            random = ThreadLocalRandom.current().nextInt(this.main.getCurrentPlayers().size());
        } while (this.main.getCurrentPlayers().get(random).getRole().isRoleRevealed() || this.main.getCurrentPlayers().get(random).isDead());
        this.main.getCurrentPlayers().get(random).getRole().revealRole();
        return random;
    }

    @Override
    public int performDayAction(int ind, TakiyoList<Number> bad) {
        if (!bad.isEmpty()) return bad.get(ThreadLocalRandom.current().nextInt(bad.size())).intValue();

        int random = 0;
        do {
            random = ThreadLocalRandom.current().nextInt(this.main.getCurrentPlayers().size());
        } while (this.main.getCurrentPlayers().get(random).isDead());
        return random;
    }
}
