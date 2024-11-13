package controller;

import dto.GamePage;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import model.CalcGame;
import model.Game;
import repository.GameRepository;

import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

public class GameController {

    public static void create(Context ctx) throws SQLException {
        String name = ctx.queryParam("name");
        var userId = ctx.pathParamAsClass("id", Long.class).get();
        Game game;
        //if (name.equals("CalcGame")) {
            game = new CalcGame();
            game.setUserId(userId);
            GameRepository.save(game);
            var g = GameRepository.findByName("CalcGame").get();
            ctx.redirect("/games/" + g.getId());
        //}
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var game = GameRepository.findByNId(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new GamePage(game);
        ctx.render("games/show.jte", model("page", page));
    }
}
