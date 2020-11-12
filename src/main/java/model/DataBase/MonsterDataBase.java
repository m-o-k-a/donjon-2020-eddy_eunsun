package model.DataBase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MonsterDataBase {
    private static Random random = new Random();

    public enum monsters {
        SLIME, SKELETON
    }

    public static String getSprite(monsters monster) {
        return "../view/ressources/"+monster+".png";
    }

    public static monsters getRandomMonster() {
        return randomEnum(monsters.class);
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> type){
        int x = random.nextInt(type.getEnumConstants().length);
        return type.getEnumConstants()[x];
    }

}
