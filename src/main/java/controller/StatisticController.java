package controller;

import dto.StatisticPage;
import io.javalin.http.Context;
import service.GameService;

import static io.javalin.rendering.template.TemplateUtil.model;

public class StatisticController {

    public static void index(Context ctx) {
        var usersWithGames = GameService.getAllUserNameWithGames();
        var page = new StatisticPage(usersWithGames);
        ctx.render("statistic/index.jte", model("page", page)).status(200);
    }
}
