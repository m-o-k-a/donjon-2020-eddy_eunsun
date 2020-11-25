package model.DataBase;

import java.util.Random;

public class MonsterAction {
    public static ActionDataBase.Action selectAction(MonsterDataBase.monsters typeOfMonster) {
        Random random = new Random();
        //add different attack pools for monsters
        switch (typeOfMonster) {
            case Slime:
                if(random.nextBoolean()) return basicAttack();
                return noneAttack();
            default:
                return basicAttack();
        }
    }

    private static ActionDataBase.Action noneAttack() { return ActionDataBase.Action.NONE; }

    private static ActionDataBase.Action basicAttack() { return ActionDataBase.Action.ATTACK; }
}
