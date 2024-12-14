package controller;

import io.javalin.http.Context;

public class RegistrationController {
    public static void index(Context ctx) {
        ctx.render("users/registration.jte");
    }
}
