package controller;

import dto.UserPage;
import dto.UsersPage;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import model.User;
import repository.UserRepository;

import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UserController {

    public static void index(Context ctx) throws SQLException {
        var users = UserRepository.getAll();
        var page = new UsersPage(users);
        ctx.render("index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var user = UserRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new UserPage(user);
        ctx.render("users/show.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        String name = ctx.formParam("name");
        var user = new User(name);
        UserRepository.save(user);
        var id = UserRepository.findByName(name).get().getId();
        ctx.redirect("/users/" + id);
    }
}
