package model;

import model.game.Game;

import java.util.List;

public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String level;
    private List<Game> games;

    public User() {
    }

    public User(String name, String email, String password, String level) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.level = level;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Game> getGames() {
        return games;
    }
}
