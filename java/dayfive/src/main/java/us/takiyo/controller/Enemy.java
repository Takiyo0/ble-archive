package us.takiyo.controller;

import us.takiyo.interfaces.EnemySpecialAttribute;

import java.util.concurrent.ThreadLocalRandom;

public class Enemy {
    private String name;
    private final int baseHealth;
    private final int baseAtk;
    private final int baseSpeed;
    private final int baseExpScaling;
    private Character.Element element;
    private EnemySpecialAttribute specialAttribute;
    private int encounterProbability;

    public Enemy(String name, int health, int baseAtk, int speed, int baseExpScaling, int encounterProbability, EnemySpecialAttribute specialAttribute) {
        this.name = name;
        this.baseHealth = health;
        this.baseAtk = baseAtk;
        this.baseSpeed = speed;
        this.element = Character.Element.values()[ThreadLocalRandom.current().nextInt(Character.Element.values().length)];
        this.encounterProbability = encounterProbability;
        this.baseExpScaling = baseExpScaling;
        this.specialAttribute = specialAttribute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth(int floor) {
        return baseHealth + 20 * floor;
    }

    public int getBaseAtk(int floor) {
        return baseAtk + 5 * floor;
    }


    public int getSpeed(int floor) {
        return baseSpeed + 2 * floor;
    }

    public int getExpScaling(int floor) {
        return baseExpScaling + 2 * floor;
    }

    public Character.Element getElement() {
        return element;
    }

    public void setElement(Character.Element element) {
        this.element = element;
    }

    public EnemySpecialAttribute getSpecialAttribute() {
        return specialAttribute;
    }

    public void setSpecialAttribute(EnemySpecialAttribute specialAttribute) {
        this.specialAttribute = specialAttribute;
    }

    public int getEncounterProbability() {
        return encounterProbability;
    }

    public void setEncounterProbability(int encounterProbability) {
        this.encounterProbability = encounterProbability;
    }
}
