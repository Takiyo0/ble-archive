package us.takiyo.extensions;

import us.takiyo.Main;

public abstract class Player implements Cloneable {
    private String name;
    private final Role role;
    private boolean isDead = false;
    private final int defaultCount;

    public Main main;

    public Player(String name, Role role, Main main, int count) {
        this.name = name;
        this.role = role;
        this.main = main;
        this.defaultCount = count;
    }

    @Override
    public Player clone() {
        try {
            return (Player) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Player clone(String newName) {
        Player clonedPlayer = this.clone();
        clonedPlayer.setName(newName);
        return clonedPlayer;
    }

    public abstract int performNightAction(int ind);

    public abstract int performDayAction(int ind, TakiyoList<Number> bad);

    public int getDefaultCount() {
        return defaultCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public boolean isDead() {
        return isDead;
    }

    public void kill() {
        this.isDead = true;
    }
}