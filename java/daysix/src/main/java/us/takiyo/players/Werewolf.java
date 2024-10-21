package us.takiyo.players;

import us.takiyo.Main;
import us.takiyo.extensions.Player;
import us.takiyo.extensions.Role;
import us.takiyo.extensions.TakiyoList;

import java.util.concurrent.ThreadLocalRandom;

public class Werewolf extends Player {
    public Werewolf(String name, Main main) {
        super(name, new Role("werewolf", true), main, 2);
    }

    @Override
    public Werewolf clone() {
        return (Werewolf) super.clone();
    }

    @Override
    // wolf will find a player to kill
    public int performNightAction(int ind) {
        int random = 0;
        do {
            random = ThreadLocalRandom.current().nextInt(this.main.getCurrentPlayers().size());
        } while (this.main.getCurrentPlayers().get(random).isDead() || this.main.getCurrentPlayers().get(random).getRole().getName().equalsIgnoreCase("werewolf"));
        this.main.getCurrentPlayers().get(random).kill();
        return random;
    }

    @Override
    // wolf vote on day
    public int performDayAction(int ind, TakiyoList<Number> bad) {
        if (!bad.isEmpty()) return bad.get(ThreadLocalRandom.current().nextInt(bad.size())).intValue();

        int random = 0;
        do {
            random = ThreadLocalRandom.current().nextInt(this.main.getCurrentPlayers().size());
        } while (this.main.getCurrentPlayers().get(random).isDead());
        return random;
    }
}
