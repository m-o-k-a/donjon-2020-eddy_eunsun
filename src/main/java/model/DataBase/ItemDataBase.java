package model.DataBase;

import java.util.Random;

public class ItemDataBase {
    private static Random random = new Random();

    public enum weapons {
        SWORD, CLAYMORE, STAFF, POLEARM, FIST
    }

    public enum usableItem {
        POTION, BOMB
    }

    public enum magic {
        FIRE, WATER, WIND, THUNDER, LIGHT, DARK, EARTH
    }

    public static ItemDataBase.weapons getRandomWeapon() {
        return randomEnum(ItemDataBase.weapons.class);
    }
    public static ItemDataBase.usableItem getRandomUsableItem() { return randomEnum(ItemDataBase.usableItem.class); }
    public static ItemDataBase.magic getRandomMagic() { return randomEnum(ItemDataBase.magic.class); }

    private static <T extends Enum<?>> T randomEnum(Class<T> type){
        int x = random.nextInt(type.getEnumConstants().length);
        return type.getEnumConstants()[x];
    }
}
