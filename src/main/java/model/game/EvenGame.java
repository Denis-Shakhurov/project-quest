package model.game;

import java.util.Map;

public class EvenGame extends Game {
    private static final int MAX_INT = 200;

    @Override
    public String getDescription() {
        return "В данной игре нужно ответить явдяется число чётным или нечётным.";
    }

    @Override
    public Map<String, String> getQuestionAndAnswer() {
        int number = getRandomInt(1, MAX_INT);
        return isEven(number)
                ? Map.of(String.valueOf(number), "да")
                : Map.of(String.valueOf(number), "нет");
    }

    @Override
    public String getName() {
        return "Чётное/нечётное";
    }

    private int getRandomInt(int min, int max) {
        return (int) (min + Math.random() * max + 1);
    }

    private boolean isEven(int number) {
        return number % 2 == 0;
    }
}
