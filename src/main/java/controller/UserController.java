package controller;

import dto.UserPage;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;
import model.User;
import repository.UserRepository;

import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UserController {

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var user = UserRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new UserPage(user);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("users/show.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        String name = ctx.formParam("name");
        try {
            ctx.formParamAsClass("name", String.class)
                    .check(value -> {
                        try {
                            return UserRepository.findByName(name);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }, "Игрок уже существует").get();
            var user = new User(name);
            var id = UserRepository.save(user);
            ctx.sessionAttribute("flash", "Игрок создан");
            ctx.status(201);
            ctx.redirect("/users/" + id);
        } catch (ValidationException e) {
            ctx.sessionAttribute("flash", "Игрок уже существует");
            ctx.status(422);
            ctx.redirect("/");
        }
    }
}
