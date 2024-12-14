import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import config.Provider;
import controller.*;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.security.RouteRole;
import config.javalinjwt.JWTAccessManager;
import config.javalinjwt.JWTProvider;
import config.javalinjwt.JavalinJWT;
import model.Roles;
import model.User;
import repository.BaseRepository;
import utils.NamedRoutes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {

        // run app
        Javalin app = getApp();
        // run web server
        app.start(8080);
    }

    public static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    public static Javalin getApp() throws Exception {

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
        var dataSource = new HikariDataSource(hikariConfig);
        var url = App.class.getClassLoader().getResourceAsStream("schema.sql");
        var sql = new BufferedReader(new InputStreamReader(url))
                .lines().collect(Collectors.joining("\n"));
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
            config.bundledPlugins.enableDevLogging();
        });

        // create the provider
        JWTProvider<User> provider = Provider.create();

        Handler decodeHandler = JavalinJWT.createCookieDecodeHandler(provider);
        // create the access manager
        Map<String, RouteRole> rolesMapping = new HashMap<>() {{
            put("user", Roles.USER);
            put("admin", Roles.ADMIN);
        }};

        JWTAccessManager accessManager = new JWTAccessManager("level", rolesMapping, Roles.GUEST);

        // set the paths
        app.before(decodeHandler);
        //app.accessManager(accessManager);
        app.beforeMatched(accessManager);

        app.get(NamedRoutes.startPath(), StartController::index, Roles.GUEST, Roles.USER, Roles.ADMIN);
        app.post(NamedRoutes.userPath("{id}"), GameController::create, Roles.USER, Roles.ADMIN);
        app.post(NamedRoutes.startPath(), UserController::create, Roles.GUEST, Roles.USER, Roles.ADMIN);
        app.get(NamedRoutes.userPath("{id}"), UserController::show, Roles.USER, Roles.ADMIN);
        app.get(NamedRoutes.gamePath("{id}"), GameController::show, Roles.USER, Roles.ADMIN);
        app.post(NamedRoutes.gamePath("{id}"), GameController::show, Roles.USER, Roles.ADMIN);
        app.get(NamedRoutes.statisticPath(), StatisticController::index, Roles.USER, Roles.ADMIN);
        app.post("/registration", UserController::create, Roles.GUEST, Roles.USER, Roles.ADMIN);
        app.get("/registration", RegistrationController::index, Roles.GUEST, Roles.USER, Roles.ADMIN);
        app.get("/login", LoginController::index, Roles.GUEST, Roles.USER, Roles.ADMIN);
        app.post("/login", UserController::login, Roles.GUEST, Roles.USER, Roles.ADMIN);
        app.get("/logout", UserController::logout, Roles.USER, Roles.ADMIN);

        return app;
    }
}
