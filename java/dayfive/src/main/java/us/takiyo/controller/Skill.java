package us.takiyo.controller;

import us.takiyo.enums.SpecialAttributeTypes;

import java.util.Objects;

public class Skill {
    private String name;
    private SpecialAttributeTypes.SkillType type;
    Player player;

    // to create new skill
    public Skill(Player player) {
        this.player = player;
        if (player.getCharacter() != null)
            this.name = player.getCharacter().getElement() + " " + player.getCharacter().getNameForRandomization();
    }

    // for loading data from db
    public Skill(String name, Player player) {
        this.player = player;
        this.name = name;
    }

    public int getManaCost() {
        return 10 * player.getCharacter().getLevel();
    }

    public double getMultiplier() {
        return 1.5 * player.getCharacter().getLevel();
    }

    public double getDamageMultiplier(String enemyElement) {
        return player.getCharacter().getBaseAtk() * this.getMultiplier() * this.getElementMultiplier(player.getCharacter().getElement(), enemyElement);
    }

    private double getElementMultiplier(String attacker, String enemy) {
        if (Objects.equals(attacker, "fire") && Objects.equals(enemy, "earth")) return 1.5;
        if (Objects.equals(attacker, "earth") && Objects.equals(enemy, "water")) return 1.5;
        if (Objects.equals(attacker, "water") && Objects.equals(enemy, "fire")) return 1.5;
        if (Objects.equals(attacker, enemy)) return 1.0;
        return 1.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
