package model;

import view.ConsoleView;
import view.View;

public class Player {
    View view ;

    public Player(View view) {
        this.view = view;
    }

    public void goNorth() {
        view.handleMove(new Move("You face a wall"));
    }
}
