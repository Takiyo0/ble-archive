package us.takiyo.enemies;

import us.takiyo.controller.Enemy;
import us.takiyo.interfaces.EnemySpecialAttribute;

public class Dragon extends Enemy {
    public Dragon() {
        super("dragon", 50, 10, 5, 30, 10, new EnemySpecialAttribute("fire breath", 20, 3));
    }
}
