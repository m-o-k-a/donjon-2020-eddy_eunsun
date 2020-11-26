package model.Entity;

public abstract class Characters { //todo rename class
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
    public int getMaxHealth() { return maxHealth; }
    public int getStrength() { return strength; }
    public String getName() { return name; }

    public void dammages(int dammages) {
        health -= dammages;
        //if(health > maxHealth) this.health = maxHealth; //comment to allows player to heal theeself more and hence gain hp
        if(health>maxHealth) {maxHealth = health;} //uncomment to allows player to heal theeself more and hence gain hp
    }

    public boolean isDead() { return health<=0; }
}
