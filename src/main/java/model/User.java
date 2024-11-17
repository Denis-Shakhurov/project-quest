package model;

import model.game.Game;

import java.util.List;

public class User {
    private Long id;
    private String name;
    private List<Game> games;

    public User(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Game> getGames() {
        return games;
    }
}
