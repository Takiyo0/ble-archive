package us.takiyo.interfaces;

public class Factory {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FactoryType getType() {
        return type;
    }

    public void setType(FactoryType type) {
        this.type = type;
    }

    public String getAdds() {
        return adds;
    }

    public void setAdds(String adds) {
        this.adds = adds;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public void setProductionRate(int productionRate) {
        this.productionRate = productionRate;
    }

    public int getCosts() {
        return costs;
    }

    public void setCosts(int costs) {
        this.costs = costs;
    }

    public enum FactoryType {
        Wood,
        Stone,
        Gold
    }

    private String name;
    private FactoryType type;
    private String adds;
    private int productionRate = 3;
    private int costs = 10;

    public Factory(FactoryType type) {
        this.type = type;
    }
}
