package controller;

import dto.StatisticPage;
import io.javalin.http.Context;
import repository.GameRepository;

import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

public class StatisticController {

    public static void index(Context ctx) throws SQLException {
        var usersWithGames = GameRepository.getAllUserNameWithGames();
        var page = new StatisticPage(usersWithGames);
        ctx.render("statistic/index.jte", model("page", page)).status(200);
    }
}
