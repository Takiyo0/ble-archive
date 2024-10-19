package us.takiyo.characters;

import us.takiyo.controller.Character;
import us.takiyo.enums.SpecialAttributeTypes;
import us.takiyo.interfaces.SpecialAttribute;

public class WarriorBase extends Character {
    public WarriorBase() {
        super("warrior", 50.0, 200.0, 10.0, 100.0, new String[]{"slash", "strike", "smash"},
                new SpecialAttribute(SpecialAttributeTypes.SpecialAttributeType.Armor, 15));
    }
}