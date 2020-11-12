package model.Room;

import model.DataBase.MonsterDataBase;
import model.Entity.EntityFactory;
import model.Entity.Monster;

import java.util.Random;

public class Chamber implements  Room {
    EntityFactory entityFactory = new EntityFactory();
    public boolean[] openedDoors;
    Random random = new Random();
    private boolean isFinal;
    public boolean isVisited;
    public Monster monster = null;

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
    }
}
