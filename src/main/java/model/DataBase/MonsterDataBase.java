package model.DataBase;

import model.Entity.EntityFactory;
import model.Entity.Monster;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MonsterDataBase {
    private static EntityFactory entityFactory = new EntityFactory();
    private static Random random = new Random();

    public enum monsters {
        Slime, Skeleton, Ghost
    }

    public static String getSprite(monsters monster) {
        return "../view/ressources/"+monster+".png";
    }

    private static monsters getRandomMonster() {
        return randomEnum(monsters.class);
    }

    public static Monster generateMonster(int difficulty) {
        monsters monster = getRandomMonster();
        return entityFactory.createMonster(monster, random.nextInt(50*difficulty), random.nextInt(5*difficulty), monster.toString());
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> type){
        int x = random.nextInt(type.getEnumConstants().length);
        return type.getEnumConstants()[x];
    }

}
