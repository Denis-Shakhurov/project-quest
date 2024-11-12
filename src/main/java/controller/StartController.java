package controller;

import dto.StartPage;
import io.javalin.http.Context;

import static io.javalin.rendering.template.TemplateUtil.model;

public class StartController {

    public static void index(Context ctx) {
        var page = new StartPage();
        ctx.render("index.jte", model("page", page));
    }
}
