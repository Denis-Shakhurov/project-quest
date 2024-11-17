package model.game;

import java.util.Map;

public class CalcGame extends Game {
    private static final int MAX_INT = 10;

    @Override
    public String getDescription() {
        return "Дать верный ответ для математичнской операции.";
    }

    @Override
    public String getName() {
        return "Калькулятор";
    }

    @Override
    public Map<String, String> getQuestionAndAnswer() {
        String[] maths = {"+", "-", "*"};
        int number1 = getRandomInt(0, MAX_INT);
        int number2 = getRandomInt(0, MAX_INT);
        int index = getRandomInt(0, 2);
        String math = maths[index];
        String question = number1 + " " + math + " " + number2;
        String answer = String.valueOf(mathOperation(number1, number2, math));
        return Map.of(question, answer);
    }

    private int mathOperation(int number1, int number2, String math) {
        int result = 0;
        switch (math) {
            case "-" : result = number1 - number2; break;
            case "+" : result = number1 + number2; break;
            case "*" : result = number1 * number2; break;
            default :
                System.out.println("unknown mathematical operation"); break;
        }
        return result;
    }

    private int getRandomInt(int min, int max) {
        return (int) (min + Math.random() * max + 1);
    }
}
