package dto;

import model.Game;

public class GamePage {
    private Game game;

    public GamePage(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
