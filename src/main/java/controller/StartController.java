package controller;

import dto.StartPage;
import dto.UsersPage;
import io.javalin.http.Context;
import repository.UserRepository;

import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

public class StartController {

    public static void index(Context ctx) throws SQLException {
        var users = UserRepository.getAll();
        var page = new UsersPage(users);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("index.jte", model("page", page));
    }
}
