package model;

import model.game.CalcGame;
import model.game.EvenGame;
import model.game.Game;
import model.game.ProgressionGame;

public class FactoryGame {

    public Game getGame(String nameGame) {
        switch (nameGame) {
            case "CalcGame" : return new CalcGame();
            case "Калькулятор" : return new CalcGame();
            case "EvenGame" : return new EvenGame();
            case "Чётное/нечётное" : return new EvenGame();
            case "ProgressionGame" : return new ProgressionGame();
            case "Прогрессия" : return new ProgressionGame();
            default : throw new IllegalStateException("Unexpected value: " + nameGame);
        }
    }
}
