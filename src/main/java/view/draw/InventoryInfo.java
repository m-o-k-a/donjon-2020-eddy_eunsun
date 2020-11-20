package view.draw;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Item.Inventory;

public class InventoryInfo implements Drawable {

    private TextFlow weaponInfo;
    private TextFlow itemInfo;
    private TextFlow magicInfo;
    private Inventory playerInventory;

    public InventoryInfo(TextFlow weaponInfo, TextFlow itemInfo, TextFlow magicInfo, Inventory playerInventory) {
        this.weaponInfo = weaponInfo;
        this.itemInfo = itemInfo;
        this.magicInfo = magicInfo;
        this.playerInventory = playerInventory;
    }

    @Override
    public void draw() {
        weaponInfo.getChildren().clear(); itemInfo.getChildren().clear(); magicInfo.getChildren().clear();
        if(playerInventory.getWeapon() != null) {
            Text weapon = new Text(playerInventory.getWeapon().toString()+"\nDamages: "+playerInventory.getWeapon().getDamages());
            weapon.setFill(Color.WHITE);
            weaponInfo.getChildren().add(weapon);
        }
        if(playerInventory.getUsableItem() != null) {
            Text item = new Text(playerInventory.getUsableItem().toString()+"\nDamages: "+playerInventory.getUsableItem().getDamages());
            item.setFill(Color.WHITE);
            itemInfo.getChildren().add(item);
        }
        if(playerInventory.getMagic() != null) {
            Text magic = new Text(playerInventory.getMagic().toString()+"\nDamages: "+playerInventory.getMagic().getDamages());
            magic.setFill(Color.WHITE);
            magicInfo.getChildren().add(magic);
        }
    }
}
