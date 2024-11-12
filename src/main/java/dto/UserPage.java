package dto;

import model.User;

public class UserPage {
    private User user;

    public UserPage(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
