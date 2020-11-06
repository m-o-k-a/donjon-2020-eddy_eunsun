package model;

public class Player {
    public int x;
    public int y;
    private int lifePoint;
    private int maxLife;

    public Player(int x, int y, int lifePoint) {
        this.x = x;
        this.y = y;
        this.lifePoint = lifePoint;
        this.maxLife = lifePoint;
    }

    public void goUp() { y--; }
    public void goDown() { y++; }
    public void goRight() { x++; }
    public void goLeft() { x--; }


    public int getLifePoint() {
        return lifePoint;
    }
    public void setLifePoint(int lifePoint) {
        if(lifePoint > maxLife) this.lifePoint = maxLife;
        else this.lifePoint = lifePoint;
    }
}
