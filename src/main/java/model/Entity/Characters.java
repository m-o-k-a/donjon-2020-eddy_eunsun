package model.Entity;

public abstract class Characters implements Entity {
    private int health;
    private int maxHealth;
    private int strength;
    private String name;

    public Characters(int maxHealth, int strength, String name) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.strength = strength;
        this.name = name;
    }

    public int getHealth() { return health; }
    public int getStrength() { return strength; }
    public String getName() { return name; }

    public void dammages(int dammages) {
        health -= dammages;
        if(health > maxHealth) this.health = maxHealth;
    }

    public boolean isDead() { return health<=0; }

    public void useAttack(Characters target) {
        dammages(this.strength);
    }

}
