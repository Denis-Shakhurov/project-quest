import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import model.FactoryGame;
import model.User;
import model.game.Game;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.GameRepository;
import repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {
    Javalin app;
    private static MockWebServer mockBackEnd;
    private final FactoryGame factoryGame = new FactoryGame();


    @BeforeAll
    static void setUpMock() throws IOException {
        mockBackEnd = new MockWebServer();
        var html = Files.readString(Paths.get("src/test/resources/pageForTest.html"));
        var serverResponse = new MockResponse()
                .addHeader("Content-Type", "text/html; charset=utf-8")
                .setResponseCode(200)
                .setBody(html);
        mockBackEnd.enqueue(serverResponse);
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }


    @BeforeEach
    public final void setUp() throws Exception {
        app = App.getApp();
    }

    @Test
    public void startPageTest() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");

            assertEquals(200, response.code());
            assertTrue(response.body().string().contains("QuestGame"));
        });
    }

    @Test
    public void statisticPageTest() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/statistic");

            assertEquals(200, response.code());
            assertTrue(response.body().string().contains("Статистика игр"));
        });
    }

    @Test
    public void pageGameShowNoFoundTest() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/games/9999999");

            assertEquals(404, response.code());
        });
    }

    @Test
    public void showUserTest() throws SQLException {
        User user = new User("Elf");
        UserRepository.save(user);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/users/" + user.getId());

            assertEquals(200, response.code());
            assertTrue(response.body().string().contains(user.getName()));
        });
    }

    @Test
    public void showGameTest() throws SQLException {
        User user = new User("Martin");
        UserRepository.save(user);

        Game game = factoryGame.getGame("CalcGame");
        game.setUserId(user.getId());
        GameRepository.save(game);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/games/" + game.getId());

            assertEquals(200, response.code());
            assertTrue(response.body().string().contains(game.getDescription()));
        });
    }

    @Test
    public void createUserSuccessTest() throws SQLException {
        User userExpected = new User("TestUser");
        UserRepository.save(userExpected);
        JavalinTest.test(app, (server, client) -> {
            client.post("/");

            var userActual = UserRepository.findById(1L).get();
            assertEquals(userExpected.getName(), userActual.getName());
        });
    }
}
