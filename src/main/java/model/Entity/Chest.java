package model.Entity;

import javafx.scene.text.Text;
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

    public Text dropItem(Player player) {
        if(item instanceof Weapon) {
            Weapon weapon = (Weapon) item;
            Weapon temp = player.getInventory().getWeapon();
            player.getInventory().setWeapon(weapon);
            item = temp;
            return chestLog(item, player.getInventory().getWeapon());
        }
        else if(item instanceof Magic) {
            Magic magic = (Magic) item;
            Magic temp = player.getInventory().getMagic();
            player.getInventory().setMagic(magic);
            item = temp;
            return chestLog(item, player.getInventory().getMagic());
        }
        else if(item instanceof UsableItem){
            UsableItem usableItem = (UsableItem) item;
            UsableItem temp = player.getInventory().getUsableItem();
            player.getInventory().setUsableItem(usableItem);
            item = temp;
            return chestLog(item, player.getInventory().getUsableItem());
        }
        return new Text("");
    }

    private Text chestLog(Item itemFrom, Item itemTo) {
        if(itemFrom == null) { return new Text("<You take the "+itemTo.toString()+" from the chest>"); }
        else if(itemTo == null) { return new Text("<You drop the "+itemFrom.toString()+" in the chest>"); }
        else { return new Text("<You take the "+itemTo.toString()+" from the chest, you drop the "+itemFrom.toString()+" in the chest>"); }
    }
}
