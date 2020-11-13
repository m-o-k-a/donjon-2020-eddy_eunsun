package model.Item;

import model.DataBase.ItemDataBase;

public class Magic extends AbstractItem {

    private ItemDataBase.magic type;

    public Magic(String name, int damages, ItemDataBase.magic type) {
        super(name, damages);
        this.type = type;
    }


    @Override
    public void onUse() {

    }

    @Override
    public String toString() { return getName()+" of "+type; }
}
