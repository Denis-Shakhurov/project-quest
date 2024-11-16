package controller;

import dto.GamePage;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import model.CalcGame;
import model.Game;
import repository.GameRepository;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static io.javalin.rendering.template.TemplateUtil.model;

public class GameController {
    private static List<String> answers = new LinkedList<>();
    private static List<String> currentAnswers = new LinkedList<>();
    private static int count = 0;

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
        var answer = ctx.formParam("answer");
        if (answer != null) {
            answers.add(answer);
        }
        page.setAnswer(answer);

        for (Map<String, String> map : game.getQuestionAndAnswer()) {
            for (String question : map.keySet()) {
                currentAnswers.add(map.get(question));
                page.setCurrentAnswer(map.get(question));
                page.setQuestion(question);
            }
        }

        if (!answers.isEmpty() && !currentAnswers.isEmpty()) {
            if (answers.get(count).equals(currentAnswers.get(count))) {
                page.setFlash("correct!");
            } else {
                page.setFlash("fail!");
            }
            count++;
        }

        ctx.render("games/show.jte", model("page", page));
    }
}
