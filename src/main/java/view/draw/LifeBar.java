package view.draw;

import javafx.scene.shape.Rectangle;
import model.Entity.Player;

public class LifeBar implements Drawable {

    Rectangle playerLife;
    Player player;
    double width;

    public LifeBar(Rectangle playerLife, Player player, double width) {
        this.player = player;
        this.playerLife = playerLife;
        this.width = width;
    }

    @Override
    public void draw() {
        playerLife.setWidth((width*player.getHealth())/player.getMaxHealth());
    }

}
