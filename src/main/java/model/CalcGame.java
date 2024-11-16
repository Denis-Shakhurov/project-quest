package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalcGame extends Game {
    private static final int NUMBER_OF_ROUNDS = 1;
    private static final int MAX_INT = 10;

    @Override
    public String getDescription() {
        return "Дать верный ответ для математичнской операции.";
    }

    @Override
    public String getName() {
        return "CalcGame";
    }

    @Override
    public List<Map<String, String>> getQuestionAndAnswer() {
        List<Map<String, String>> questionsAndAnswers = new ArrayList<>();
        String[] maths = {"+", "-", "*"};
        for (int i = 0; i < NUMBER_OF_ROUNDS; i++) {
            int number1 = getRandomInt(0, MAX_INT);
            int number2 = getRandomInt(0, MAX_INT);
            int index = getRandomInt(0, 2);
            String math = maths[index];
            String question = number1 + " " + math + " " + number2;
            String answer = String.valueOf(mathOperation(number1, number2, math));
            questionsAndAnswers.add(Map.of(question, answer));
        }
        return questionsAndAnswers;
    }

    private static int mathOperation(int number1, int number2, String math) {
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
