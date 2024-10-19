package us.takiyo.interfaces;

import us.takiyo.enums.SpecialAttributeTypes;

public class SpecialAttribute {
    private SpecialAttributeTypes.SpecialAttributeType type;
    private double value;

    public SpecialAttribute(SpecialAttributeTypes.SpecialAttributeType type, double value) {
        this.type = type;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public SpecialAttributeTypes.SpecialAttributeType getType() {
        return type;
    }

    public void setType(SpecialAttributeTypes.SpecialAttributeType type) {
        this.type = type;
    }
}