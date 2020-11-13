package model.Item;

import model.DataBase.ItemDataBase;

public class ItemFactory {

    public Weapon createWeapon(String name, int damages, ItemDataBase.weapons type) {
        return new Weapon(name, damages, type);
    }

    public Magic createMagic(String name, int damages, ItemDataBase.magic type){
        return new Magic(name, damages, type);
    }

    public UsableItem createUsableItem(String name, int damages, ItemDataBase.usableItem type){
        return new UsableItem(name, damages, type);
    }

}
