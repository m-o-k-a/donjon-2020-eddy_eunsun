package model.Entity;

import model.DataBase.ActionDataBase;
import model.DataBase.MonsterDataBase;

import java.util.Random;

public class Monster extends Characters {
    private Random random = new Random();
    private MonsterDataBase.monsters typeOfMonster;

    public Monster(MonsterDataBase.monsters typeOfMonster, int maxHealth, int strength, String name) {
        super(maxHealth, strength, name);
        this.typeOfMonster = typeOfMonster;
    }

    public MonsterDataBase.monsters type() {
        return typeOfMonster;
    }

    public ActionDataBase.Action selectAction() {
        //add different attack pools for monsters
        switch (typeOfMonster) {
            case Slime:
                if(random.nextBoolean()) return basicAttack();
                return noneAttack();
            default:
                return basicAttack();
        }
    }

    private ActionDataBase.Action noneAttack() { return ActionDataBase.Action.NONE; }

    private ActionDataBase.Action basicAttack() { return ActionDataBase.Action.ATTACK; }
}
