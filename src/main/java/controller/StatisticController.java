package controller;

import dto.StatisticPage;
import io.javalin.http.Context;
import repository.GameRepository;
import repository.UserRepository;

import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

public class StatisticController {

    public static void index(Context ctx) throws SQLException {
        var users = UserRepository.getAll();
        var games = GameRepository.getAll();
        var page = new StatisticPage(games, users);
        ctx.render("statistic/index.jte", model("page", page));
    }
}
