import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import controller.GameController;
import controller.StartController;
import controller.StatisticController;
import controller.UserController;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import repository.BaseRepository;
import utils.NamedRoutes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        // Создаем приложение
        Javalin app = getApp();
        app.start(8080); // Стартуем веб-сервер
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

        app.get(NamedRoutes.startPath(), StartController::index);
        app.post(NamedRoutes.userPath("{id}"), GameController::create);
        app.post(NamedRoutes.startPath(), UserController::create);
        app.get(NamedRoutes.userPath("{id}"), UserController::show);
        app.get(NamedRoutes.gamePath("{id}"), GameController::show);
        app.post(NamedRoutes.gamePath("{id}"), GameController::show);
        app.get(NamedRoutes.statisticPath(), StatisticController::index);

        return app;
    }
}
