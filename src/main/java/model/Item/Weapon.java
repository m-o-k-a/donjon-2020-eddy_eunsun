package model.Item;

import model.DataBase.ItemDataBase;

public class Weapon extends AbstractItem {

    ItemDataBase.weapons type;

    public Weapon(String name, int damages, ItemDataBase.weapons type) {
        super(name, damages);
        this.type = type;
    }

    @Override
    public void onUse() {

    }
}
