package view.draw;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Entity.Chest;

public class ChestDrawable implements Drawable {
    private Chest chest = null;
    private ImageView spriteChest;

    public ChestDrawable(ImageView spriteChest) {
        this.spriteChest = spriteChest;
    }

    public void setChest(Chest chest) {
        this.chest = chest;
    }

    @Override
    public void draw() {
        Image sprite = new Image(getClass().getResource("../ressources/chest_"+chest.isOpened()+".png").toExternalForm());
        spriteChest.setImage(sprite);
    }

    public void clear() {
        chest = null;
        spriteChest.setImage(null);
    }
}
