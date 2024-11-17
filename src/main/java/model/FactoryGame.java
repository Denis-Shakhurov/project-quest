package model;

import model.game.CalcGame;
import model.game.EvenGame;
import model.game.Game;
import model.game.ProgressionGame;

public class FactoryGame {

    public Game getGame(String nameGame) {
        return switch (nameGame) {
            case "CalcGame", "Калькулятор" -> new CalcGame();
            case "EvenGame", "Чётное/нечётное" -> new EvenGame();
            case "ProgressionGame", "Прогрессия" -> new ProgressionGame();
            default -> throw new IllegalStateException("Unexpected value: " + nameGame);
        };
    }
}
