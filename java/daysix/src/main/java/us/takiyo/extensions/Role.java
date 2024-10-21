package us.takiyo.extensions;

public class Role {
    private final String name;
    private boolean isRevealed;
    private boolean isBad;

    public Role(String name, boolean isBad) {
        this.name = name;
        this.isRevealed = false;
        this.isBad = isBad;
    }

    public String getName() {
        return name;
    }

    public void revealRole() {
        this.isRevealed = true;
    }

    public boolean isRoleRevealed() {
        return isRevealed;
    }

    public boolean isBad() {
        return isBad;
    }
}
