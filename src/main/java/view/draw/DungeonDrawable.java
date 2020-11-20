package view.draw;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.DataBase.MonsterDataBase;
import model.Entity.Monster;

public class DungeonDrawable implements Drawable {

    private Pane scene;
    private Background background;
    private Image image;
    private BackgroundImage backgroundImage;

    public DungeonDrawable(Pane scene) {
        this.scene = scene;
        image = new Image(getClass().getResource("../ressources/room-000.jpg").toExternalForm());
        backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        background = new Background(backgroundImage);
    }

    public void setBackground(String background) {
        image = new Image(getClass().getResource(""+background).toExternalForm());
    }

    @Override
    public void draw() {
        backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        scene.setBackground(new Background(backgroundImage));
    }
}
