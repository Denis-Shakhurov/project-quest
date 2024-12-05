import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import model.FactoryGame;
import model.User;
import model.game.Game;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
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

public class GameControllerTest {
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
    public void showGameTest() throws SQLException {
        User user = new User("Martin");
        UserRepository.save(user);

        Game game = factoryGame.getGame("CalcGame");
        game.setUserId(user.getId());
        GameRepository.save(game);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/games/" + game.getId());

            assertEquals(200, response.code());
            Assertions.assertTrue(response.body().string().contains(game.getDescription()));
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
    public void createGameSuccessTest() throws SQLException {
        User user = new User("TestUser");
        Long userId = UserRepository.save(user);

        Game gameExpected = new FactoryGame().getGame("CalcGame");
        gameExpected.setUserId(userId);
        Long gameId = GameRepository.save(gameExpected);

        JavalinTest.test(app, (server, client) -> {
            client.post("/users/" + userId);

            var gameActual = GameRepository.findById(gameId).get();
            assertEquals(gameExpected.getName(), gameActual.getName());
        });
    }

    /*@Test
    public void createGameSuccessTest() throws SQLException {
        Context ctx = mock(Context.class);

        when(ctx.formParam("game")).thenReturn("CalcGame");
        when(ctx.pathParamAsClass("id", Long.class)).thenReturn(Optional.of(1L));

        Game mockGame = mock(Game.class);
        when(mockGame.getName()).thenReturn("CalcGame");

        create(ctx);

        verify(ctx).status(201);
        verify(ctx).redirect(contains("/games/"));

        ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
        verify(GameRepository.class).cast(gameCaptor.capture());

        Game savedGame = gameCaptor.getValue();
        assertEquals("CalcGame", savedGame.getName());
        assertEquals(1L, savedGame.getUserId());
    }*/
}
