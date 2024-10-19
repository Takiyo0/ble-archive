package us.takiyo.characters;

import us.takiyo.controller.Character;
import us.takiyo.enums.SpecialAttributeTypes;
import us.takiyo.interfaces.SpecialAttribute;

public class MageBase extends Character {
    public MageBase() {
        super("mage", 40.0, 150.0, 12.0, 120.0, new String[]{"bolt", "lance", "surge"},
                new SpecialAttribute(SpecialAttributeTypes.SpecialAttributeType.MagicPower, 30));
    }
}
