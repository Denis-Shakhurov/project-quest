package controller;

import dto.GamePage;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import model.FactoryGame;
import model.game.Game;
import service.GameService;
import utils.NamedRoutes;

import java.sql.SQLException;
import java.util.ArrayDeque;

import static io.javalin.rendering.template.TemplateUtil.model;

public class GameController {
    private static final ArrayDeque<String> answers = new ArrayDeque<>();
    private static int count = 0;

    public static void create(Context ctx) throws SQLException {
        FactoryGame factoryGame = new FactoryGame();
        String nameGame = ctx.formParam("game");
        //var userId = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);
        var userId = Long.parseLong(ctx.cookie("userId"));
        Game game = factoryGame.getGame(nameGame);
        game.setUserId(userId);
        var gameId = GameService.create(game);
        ctx.status(HttpStatus.CREATED);
        ctx.redirect(NamedRoutes.gamePath(gameId));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var game = GameService.findById(id);

        var page = new GamePage(game);
        var answerUser = ctx.formParam("answer");
        if (answerUser != null) {
            answers.add(answerUser);
        } else {
            answers.clear();
            count = 0;
        }

        var questionAndAnswer = game.getQuestionAndAnswer();

        for (String question : questionAndAnswer.keySet()) {
            answers.add(questionAndAnswer.get(question));
            page.setQuestion(question);
        }

        if (answers.size() > 1) {
            var answer = answers.poll();
            var currentAnswer = answers.poll();
            if (currentAnswer.equals(answer)) {
                ctx.sessionAttribute("flash", "Верно!");
                count++;
            } else {
                page.setStatusAnswer("Fail!");
                game.setCountLose(1);
            }
        }

        if (count == 3) {
            page.setStatusAnswer("End game");
            game.setCountWin(1);
        }

        GameService.update(game);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("games/show.jte", model("page", page)).status(HttpStatus.OK);
    }

    public static void destroy(Context ctx) throws SQLException {
        var userId = ctx.pathParamAsClass("id", Long.class).get();
        GameService.destroy(userId);
        ctx.status(HttpStatus.OK);
        ctx.redirect(NamedRoutes.userPath(userId));
    }
}
