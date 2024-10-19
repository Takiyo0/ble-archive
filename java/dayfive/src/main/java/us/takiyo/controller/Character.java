package us.takiyo.controller;

import us.takiyo.interfaces.SpecialAttribute;

import java.util.concurrent.ThreadLocalRandom;

public class Character {
    public String getElement() {
        return element.toString().toLowerCase();
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public String getNameForRandomization() {
        return namesForRandomization[ThreadLocalRandom.current().nextInt(0, namesForRandomization.length)];
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public enum Element {
        Fire,
        Water,
        Earth
    }

    private String name;
    private String type;
    private double baseAtk;
    private final double baseHealth;
    private double health;
    private double mana;
    private double speed;
    private double exp = 0;
    private int level = 1;
    private Element element;
    private SpecialAttribute specialAtr;

    private final String[] namesForRandomization;

    // used to add character to user
    public Character(Character character, String name) {
        this.name = name;
        this.type = character.type;
        this.baseAtk = character.baseAtk;
        this.baseHealth = character.baseHealth;
        this.health = character.health;
        this.speed = character.speed;
        this.mana = character.mana;
        this.specialAtr = character.specialAtr;
        this.namesForRandomization = character.namesForRandomization;
        this.element = Element.values()[ThreadLocalRandom.current().nextInt(Element.values().length)];
    }

    // used for initialization for base
    public Character(String type, double baseAtk, double health, double speed, double mana, String[] namesForRandomization, SpecialAttribute specialAtr) {
        this.type = type;
        this.baseAtk = baseAtk;
        this.baseHealth = health;
        this.health = health;
        this.speed = speed;
        this.mana = mana;
        this.namesForRandomization = namesForRandomization;
        this.specialAtr = specialAtr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpecialAttribute getSpecialAtr() {
        return specialAtr;
    }

    public void setSpecialAtr(SpecialAttribute specialAtr) {
        this.specialAtr = specialAtr;
    }

    public double getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public double getSpeed() {
        return speed;
    }

    public double getBaseHealth() {
        return baseHealth;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getBaseAtk() {
        return baseAtk;
    }

    public void setBaseAtk(int baseAtk) {
        this.baseAtk = baseAtk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean addExp(Player player, double exp) {
        this.exp += exp;
        int targetExp = 100 + 50 * (this.level - 1);
        if (this.exp > targetExp) {
            this.exp = this.exp - targetExp;
            level++;
            this.levelUp(player);
            return true;
        }
        return false;
    }

    private void levelUp(Player player) {
        Skill newSkill = new Skill(player);
        player.addSkill(newSkill);
    }

    public double getExp() {
        return exp;
    }
}
