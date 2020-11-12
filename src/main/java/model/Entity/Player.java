package model.Entity;

public class Player extends Characters {
    public int x;
    public int y;

    public Player(int x, int y, int maxHealth, int strength, String name) {
        super(maxHealth, strength, name);
        this.x = x;
        this.y = y;
    }

    public void goUp() { y--; }
    public void goDown() { y++; }
    public void goRight() { x++; }
    public void goLeft() { x--; }
}
