package model.Entity;

import javafx.scene.text.Text;
import model.Item.Item;
import model.Item.Magic;
import model.Item.UsableItem;
import model.Item.Weapon;

public class Chest {
    private Item item;
    private boolean isOpened;

    public Chest(Item item) {
        this.item = item;
        isOpened = false;
    }

    public Text dropItem(Player player) {
        isOpened = true;
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

    public boolean isOpened() { return isOpened; }

    private Text chestLog(Item itemFrom, Item itemTo) {
        if(itemFrom == null) { return new Text("<Thee taketh the "+itemTo.toString()+" from the chest>\n"); }
        else if(itemTo == null) { return new Text("<Thee dropeth the "+itemFrom.toString()+" in the chest>\n"); }
        else { return new Text("<Thee taketh the "+itemTo.toString()+" from the chest, Thee dropeth the "+itemFrom.toString()+" in the chest>\n"); }
    }
}
