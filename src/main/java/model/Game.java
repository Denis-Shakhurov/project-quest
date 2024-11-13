package model;

import java.util.List;
import java.util.Map;

public abstract class Game {
    private Long id;
    private Long userId;
    public abstract String getDescription();
    public abstract List<Map<String, String>> getQuestionAndAnswer();
    public abstract String getName();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
