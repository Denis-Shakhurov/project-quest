package controller;

import com.lambdaworks.crypto.SCryptUtil;
import config.Provider;
import config.javalinjwt.JWTProvider;
import dto.UserPage;
import dto.UsersPage;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import model.User;
import repository.GameRepository;
import repository.UserRepository;
import utils.NamedRoutes;

import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UserController {
    private static final JWTProvider<User> provider = Provider.create();

    public static void index(Context ctx) throws SQLException {
        var users = UserRepository.getAll();
        var page = new UsersPage(users);
        ctx.status(HttpStatus.OK);
        ctx.render("users/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var user = UserRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var games = GameRepository.getAllGameForUser(id);
        var page = new UserPage(user, games);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.status(HttpStatus.OK);
        ctx.render("users/show.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        var name = ctx.formParam("name");
        var email = ctx.formParam("email");
        var password = ctx.formParam("password");
        var level = ctx.formParamAsClass("level", String.class).getOrDefault("user");
        if (isValidName(name) && isValidEmail(email) && isValidPassword(password)) {
            try {
                ctx.formParamAsClass("email", String.class)
                        .check(value -> {
                            try {
                                return UserRepository.existByEmail(email);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }, "Игрок с таким " + email + " уже существует").get();
                var passwordHash = SCryptUtil.scrypt(password, 2, 2, 2);
                var user = new User(name, email, passwordHash, level);
                var id = UserRepository.save(user);

                var token = provider.generateToken(user);
                ctx.cookie("jwt", token);

                ctx.cookie("userId", String.valueOf(id));
                ctx.sessionAttribute("flash", "Игрок создан");
                ctx.status(HttpStatus.CREATED);
                ctx.redirect(NamedRoutes.startPath());
            } catch (ValidationException e) {
                ctx.sessionAttribute("flash", "Игрок с таким " + email + " уже существует");
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.redirect("/registration");
            }
        } else {
            ctx.sessionAttribute("flash", "Неккоректные данные");
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.redirect("/registration");
        }
    }

    public static void login(Context ctx) {
        try {
            var email = ctx.formParam("email");
            var password = ctx.formParam("password");
            var user = UserRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundResponse("User with " + email + " not found"));
            if (user.getPassword() != null && SCryptUtil.check(password, user.getPassword())) {

                var token = provider.generateToken(user);
                ctx.cookie("jwt", token);
                ctx.cookie("userId", String.valueOf(user.getId()));
                ctx.sessionAttribute("flash", "Привет " + user.getName() + " !");
                ctx.status(HttpStatus.OK);
                ctx.redirect(NamedRoutes.startPath());
            } else if (user.getEmail() == null) {
                ctx.sessionAttribute("flash", "Игрок с email - \"" + ctx.formParam("email") + "\" не существует");
                ctx.status(422);
                ctx.redirect("/login");
            } else {
                ctx.status(422);
                ctx.sessionAttribute("flash", "Некорректные логин или пароль");
                ctx.redirect("/login");
            }
        } catch (SQLException | NotFoundResponse e) {
            ctx.status(422);
            ctx.redirect("/login");
        }
    }

    public static void logout(Context ctx) {
        ctx.sessionAttribute("flash", null);
        ctx.cookie("jwt", "");
        ctx.cookie("userId", "");
        ctx.redirect(NamedRoutes.startPath());
    }

    public static void destroy(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        GameRepository.deleteAllGameForUser(id);
        UserRepository.delete(id);
        ctx.status(HttpStatus.OK);
        ctx.redirect(NamedRoutes.userPath(id));
    }

    private static boolean isValidName(String name) {
        return name != null && name.matches("(\\w+|[а-яА-Я0-9]+)") && name.length() >= 4;
    }

    private static boolean isValidEmail(String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
            return true;
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isValidPassword(String password) {
        return password != null && password.length() > 5;
    }
}
