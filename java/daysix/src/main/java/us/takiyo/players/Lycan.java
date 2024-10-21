package us.takiyo.players;

import us.takiyo.Main;
import us.takiyo.extensions.Player;
import us.takiyo.extensions.Role;
import us.takiyo.extensions.TakiyoList;

import java.util.concurrent.ThreadLocalRandom;

public class Lycan extends Player {

    public Lycan(String name, Main main) {
        super(name, new Role("lycan", true), main, 1);
    }

    @Override
    public Lycan clone() {
        return (Lycan) super.clone();
    }

    @Override
    // lycan does nothing on night
    public int performNightAction(int ind) {
        return -200;
    }

    @Override
    // lycan vote on day
    public int performDayAction(int ind, TakiyoList<Number> bad) {
        if (!bad.isEmpty()) return bad.get(ThreadLocalRandom.current().nextInt(bad.size())).intValue();

        int random = 0;
        do {
            random = ThreadLocalRandom.current().nextInt(this.main.getCurrentPlayers().size());
        } while (this.main.getCurrentPlayers().get(random).isDead());
        return random;
    }
}
