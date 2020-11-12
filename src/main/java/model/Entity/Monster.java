package model.Entity;

import model.DataBase.MonsterDataBase;

public class Monster extends Characters {
    MonsterDataBase.monsters typeOfMonster;

    public Monster(MonsterDataBase.monsters typeOfMonster, int maxHealth, int strength, String name) {
        super(maxHealth, strength, name);
        this.typeOfMonster = typeOfMonster;
    }

    public void selectAction() {

    }
}
