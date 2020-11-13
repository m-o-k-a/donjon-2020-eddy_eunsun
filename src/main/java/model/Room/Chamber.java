package model.Room;

import model.DataBase.ItemDataBase;
import model.DataBase.MonsterDataBase;
import model.Entity.Chest;
import model.Entity.EntityFactory;
import model.Entity.Monster;
import model.Item.ItemFactory;

import java.util.Random;

public class Chamber implements  Room {
    EntityFactory entityFactory = new EntityFactory();
    ItemFactory itemFactory = new ItemFactory();
    public boolean[] openedDoors;
    Random random = new Random();
    private boolean isFinal;
    public boolean isVisited;
    public Monster monster = null;
    public Chest chest = null;

    public Chamber(boolean[] openedDoors, boolean isFinal) {
        isVisited = false;
        this.openedDoors = openedDoors;
        this.isFinal = isFinal;
        fillChamber();
    }

    private void fillChamber() {
        if(isFinal) return;
        if(random.nextInt(101)%5 == 0) {
            //todo generate lot of possibilities
            MonsterDataBase.monsters newMonster = MonsterDataBase.getRandomMonster();
            monster = entityFactory.createMonster(newMonster, random.nextInt(200), random.nextInt(20), newMonster.toString());
        }
        if(random.nextInt(101)%3 == 0) {
            //todo generate lot of possibilities
            int randomItem = random.nextInt(3);
            Chest newChest = null;
            if(randomItem == 0) { newChest = new Chest(itemFactory.createWeapon("God", 777, ItemDataBase.getRandomWeapon())); }
            else if(randomItem == 1) { newChest = new Chest(itemFactory.createUsableItem("God", 777, ItemDataBase.getRandomUsableItem())); }
            //todo if healing item or magic negative dammages
            else if(randomItem == 2) { newChest = new Chest(itemFactory.createMagic("God", 777, ItemDataBase.getRandomMagic())); }
            chest = newChest;
        }
    }
}
