package dto;

import model.Game;
import model.User;

import java.util.List;

public class UserPage {
    private User user;
    private List<Game> games;

    public UserPage(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public List<Game> getGames() {
        return games;
    }
}
