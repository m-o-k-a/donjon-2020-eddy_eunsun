package model.Item;

import model.DataBase.ItemDataBase;

public class UsableItem extends AbstractItem {

    private ItemDataBase.usableItem type;

    public UsableItem(String name, int damages, ItemDataBase.usableItem type) {
        super(name, damages);
        this.type = type;
    }


    @Override
    public void onUse() {

    }

    @Override
    public String toString() { return type+" of "+getName(); }
}
