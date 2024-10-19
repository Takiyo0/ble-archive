package us.takiyo.interfaces;

public class EnemySpecialAttribute {
    public String description;
    public int chance;
    public int damageMultiplier;

    public EnemySpecialAttribute(String description, int chance, int damageMultiplier) {
        this.description = description;
        this.chance = chance;
        this.damageMultiplier = damageMultiplier;
    }
}
