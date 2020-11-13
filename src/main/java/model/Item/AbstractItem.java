package model.Item;

public abstract class AbstractItem implements Item {
    private String name;
    private int damages;

    public AbstractItem(String name, int damages) {
        this.name = name;
        this.damages = damages;
    }

    public String getName() {
        return name;
    }

    public int getDamages() {
        return damages;
    }
}
