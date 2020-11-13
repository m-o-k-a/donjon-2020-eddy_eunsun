package model.Entity;

import model.Item.Inventory;

public class Player extends Characters {
    public int x;
    public int y;
    private Inventory inventory;

    public Player(int x, int y, int maxHealth, int strength, String name) {
        super(maxHealth, strength, name);
        this.x = x;
        this.y = y;
        inventory = new Inventory(null, null, null);
    }

    public Inventory getInventory() { return inventory; }

    public void goUp() { y--; }
    public void goDown() { y++; }
    public void goRight() { x++; }
    public void goLeft() { x--; }
}
