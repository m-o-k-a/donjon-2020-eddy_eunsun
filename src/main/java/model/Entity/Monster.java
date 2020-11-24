package model.Entity;

import model.DataBase.ActionDataBase;
import model.DataBase.MonsterDataBase;

public class Monster extends Characters {
    private MonsterDataBase.monsters typeOfMonster;

    public Monster(MonsterDataBase.monsters typeOfMonster, int maxHealth, int strength, String name) {
        super(maxHealth, strength, name);
        this.typeOfMonster = typeOfMonster;
    }

    public MonsterDataBase.monsters type() {
        return typeOfMonster;
    }

    public ActionDataBase.Action selectAction() {
        //only attack, may have more later
        return ActionDataBase.Action.ATTACK;
    }
}
