package controller;

import dto.GamePage;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import model.FactoryGame;
import model.game.Game;
import repository.GameRepository;

import java.sql.SQLException;
import java.util.ArrayDeque;

import static io.javalin.rendering.template.TemplateUtil.model;

public class GameController {
    private static ArrayDeque<String> answers = new ArrayDeque<>();
    private static int count = 0;

    public static void create(Context ctx) throws SQLException {
        FactoryGame factoryGame = new FactoryGame();
        String nameGame = ctx.formParam("game");
        var userId = ctx.pathParamAsClass("id", Long.class).get();
        Game game = factoryGame.getGame(nameGame);
        game.setUserId(userId);
        var idGame = GameRepository.save(game);
        ctx.redirect("/games/" + idGame);

    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var game = GameRepository.findByNId(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));

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
            page.setCurrentAnswer(questionAndAnswer.get(question));
            page.setQuestion(question);
        }


        if (answers.size() > 1) {
            var answer = answers.poll();
            var currentAnswer = answers.poll();
            if (currentAnswer.equals(answer)) {
                page.setFlash("Верно!");
            } else {
                page.setFlash("fail!");
                game.setCountLose(1);
            }
            count++;
        }

        if (count == 3) {
            page.setFlash("Игра окончена");
            game.setCountWin(1);
        }

        ctx.render("games/show.jte", model("page", page));
    }
}
