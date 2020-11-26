package view.draw;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.DataBase.MonsterDataBase;
import model.Entity.Monster;

public class MonsterDrawable implements Drawable {

    private Monster monster;
    private ImageView spriteMonster;
    private Pane scene;


    public MonsterDrawable(ImageView spriteChest, Pane scene) {
        this.spriteMonster = spriteChest;
        this.scene = scene;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    @Override
    public void draw() {
        Image sprite = new Image(getClass().getResource(MonsterDataBase.getSprite(monster.type())).toExternalForm());
        spriteMonster.setImage(sprite);
        spriteMonster.layoutXProperty().bind(scene.widthProperty().subtract(sprite.getWidth()).divide(2));
        spriteMonster.layoutYProperty().bind(scene.heightProperty().subtract(sprite.getHeight()).divide(2));
    }

    public void clear() {
        monster = null;
        spriteMonster.setImage(null);
    }
}
