package model.Item;

import model.DataBase.ItemDataBase;

public class Magic extends AbstractItem {

    ItemDataBase.magic type;

    public Magic(String name, int damages, ItemDataBase.magic type) {
        super(name, damages);
        this.type = type;
    }


    @Override
    public void onUse() {

    }
}