package model.Entity;

import model.Item.Item;
import model.Item.Magic;
import model.Item.UsableItem;
import model.Item.Weapon;

public class Chest implements Entity {
    private Item item;
    private boolean isOpened;

    public Chest(Item item) {
        this.item = item;
        isOpened = false;
    }

    public void dropItem(Player player) {
        if(item instanceof Weapon) {
            Weapon weapon = (Weapon) item;
            Weapon temp = player.getInventory().getWeapon();
            player.getInventory().setWeapon(weapon);
            item = temp;
        }
        else if(item instanceof Magic) {
            Magic magic = (Magic) item;
            Magic temp = player.getInventory().getMagic();
            player.getInventory().setMagic(magic);
            item = temp;
        }
        else if(item instanceof UsableItem){
            UsableItem usableItem = (UsableItem) item;
            UsableItem temp = player.getInventory().getUsableItem();
            player.getInventory().setUsableItem(usableItem);
            item = temp;
        }
    }
}
