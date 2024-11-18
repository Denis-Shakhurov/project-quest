package dto;

import model.User;
import model.game.Game;

import java.util.List;

public class StatisticPage {
    private List<Game> games;
    private List<User> users;

    public StatisticPage(List<Game> games, List<User> users) {
        this.games = games;
        this.users = users;
    }

    public List<Game> getGames() {
        return games;
    }

    public List<User> getUsers() {
        return users;
    }
}
