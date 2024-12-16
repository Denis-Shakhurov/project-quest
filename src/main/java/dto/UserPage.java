package dto;

import model.game.Game;
import model.User;

import java.util.List;

public class UserPage extends BasePage {
    private User user;
    private List<Game> games;

    public UserPage(User user, List<Game> games) {
        this.user = user;
        this.games = games;
    }

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
