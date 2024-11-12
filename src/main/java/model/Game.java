package model;

import java.util.List;
import java.util.Map;

public abstract class Game {
    private String name;
    public abstract String getDescription();
    public abstract List<Map<String, String>> getQuestionAndAnswer();

    public Game(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
