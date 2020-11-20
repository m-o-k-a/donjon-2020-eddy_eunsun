package model.DataBase;

import model.Entity.Monster;
import model.Item.ItemFactory;
import model.Item.Magic;
import model.Item.UsableItem;
import model.Item.Weapon;

import java.util.Random;

public class ItemDataBase {
    private static ItemFactory itemFactory = new ItemFactory();
    private static Random random = new Random();

    public enum weapons {
        Sword, Claymore, Staff, Polearm, Fist
    }

    public enum usableItem {
        Potion, Bomb
    }

    public enum magic {
        Fire, Water, Earth, Light, Dark
    }

    private static ItemDataBase.weapons getRandomWeapon() {
        return randomEnum(ItemDataBase.weapons.class);
    }
    private static ItemDataBase.usableItem getRandomUsableItem() { return randomEnum(ItemDataBase.usableItem.class); }
    private static ItemDataBase.magic getRandomMagic() { return randomEnum(ItemDataBase.magic.class); }

    public static Weapon generateWeapon(int difficulty) {
        return itemFactory.createWeapon("God", random.nextInt(10*difficulty), getRandomWeapon());
    }

    public static Magic generateMagic(int difficulty) {
        ItemDataBase.magic magicItem = getRandomMagic();
        int damages = random.nextInt(20*difficulty);
        if (magicItem == magic.Light) damages *=-1;
        return itemFactory.createMagic("God", damages, magicItem);
    }

    public static UsableItem generateUsableItem(int difficulty) {
        ItemDataBase.usableItem item = getRandomUsableItem();
        int damages = random.nextInt(15*difficulty);
        if(item == usableItem.Potion) damages *=-1;
        return itemFactory.createUsableItem("God", damages, item);
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> type){
        int x = random.nextInt(type.getEnumConstants().length);
        return type.getEnumConstants()[x];
    }
}
