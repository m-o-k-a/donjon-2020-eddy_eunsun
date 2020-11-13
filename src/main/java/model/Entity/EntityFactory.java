package model.Entity;

import model.DataBase.MonsterDataBase;
import model.Item.Item;

public class EntityFactory {

    public Player createPlayer(int x, int y, int health, int strength, String name) {
        return new Player(x, y, health, strength, name);
    }

    public Monster createMonster(MonsterDataBase.monsters typeOfMonster, int maxHealth, int strength, String name) {
        return new Monster(typeOfMonster, maxHealth, strength, name);
    }

    public Chest createChest(Item item) {
        return  new Chest(item);
    }
}
