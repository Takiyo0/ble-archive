package us.takiyo.characters;

import us.takiyo.controller.Character;
import us.takiyo.enums.SpecialAttributeTypes;
import us.takiyo.interfaces.SpecialAttribute;

public class RogueBase extends Character {
    public RogueBase() {
        super("rogue", 45.0, 180.0, 15.0, 90.0, new String[]{"stab", "slice", "dagger"},
                new SpecialAttribute(SpecialAttributeTypes.SpecialAttributeType.CriticalChance, 0.25));
    }
}
