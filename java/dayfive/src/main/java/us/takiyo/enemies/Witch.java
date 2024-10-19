package us.takiyo.enemies;

import us.takiyo.controller.Enemy;
import us.takiyo.interfaces.EnemySpecialAttribute;

public class Witch extends Enemy {
    public Witch() {
        super("witch", 50, 10, 5, 20, 30, new EnemySpecialAttribute("special spell", 20, 2));
    }
}