package controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import model.Player;

public class JavaFXController {
    EventHandler<? super KeyEvent> eventHandler;

    JavaFXController(Player player){

     eventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case UP:    player.goNorth(); break;
                case DOWN:  player.goNorth(); break;
                case LEFT:  player.goNorth(); break;
                case RIGHT: player.goNorth(); break;
            }
        }
    }; }
}
