package model.Item;

import model.DataBase.ItemDataBase;

public class usableItem extends AbstractItem {

    ItemDataBase.usableItem type;

    public usableItem(String name, int damages, ItemDataBase.usableItem type) {
        super(name, damages);
        this.type = type;
    }

    @Override
    public void onUse() {
        
    }
}
