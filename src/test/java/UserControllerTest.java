import config.Provider;
import controller.UserController;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.testtools.JavalinTest;
import model.User;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import repository.UserRepository;
import utils.NamedRoutes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    Javalin app;
    private static MockWebServer mockBackEnd;
    private static Context ctx;

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
        ctx = mock(Context.class);
    }

    @Test
    public void showUserTest() throws SQLException {
        User user = new User("Ivan", "ivan@gmail.com", "wqerty", "user");
        UserRepository.save(user);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/users/" + user.getId());

            assertEquals(HttpStatus.OK, response.code());
            assertTrue(response.body().string().contains(user.getName()));
        });
    }

    @Test
    public void pageUserShowNoFoundTest() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/users/9999999");

            assertEquals(404, response.code());
        });
    }

    @Test
    public void createUserSuccessTest() throws SQLException {
        User userExpected = new User("Ivan", "ivan@gmail.com", "wqerty", "user");
        Long id = UserRepository.save(userExpected);
        JavalinTest.test(app, (server, client) -> {
            client.post(NamedRoutes.startPath());

            var userActual = UserRepository.findById(id).get();
            assertEquals(userExpected.getName(), userActual.getName());
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", "ser"})
    public void createUserWithWrongName(String  name) throws SQLException {
        when(ctx.formParam("name")).thenReturn(name);
        when(ctx.formParam("email")).thenReturn("test@nail.com");
        when(ctx.formParam("password")).thenReturn("password");

        UserController.create(ctx);

        verify(ctx).status(HttpStatus.BAD_REQUEST);
        verify(ctx).sessionAttribute("flash", "Неккоректные данные");
        verify(ctx).redirect(NamedRoutes.registrationPath());
    }

    @ParameterizedTest
    @NullSource
    public void createUserWithNullName(String  name) throws SQLException {
        when(ctx.formParam("name")).thenReturn(name);
        when(ctx.formParam("email")).thenReturn("test@nail.com");
        when(ctx.formParam("password")).thenReturn("password");

        UserController.create(ctx);

        verify(ctx).status(HttpStatus.BAD_REQUEST);
        verify(ctx).sessionAttribute("flash", "Неккоректные данные");
        verify(ctx).redirect(NamedRoutes.registrationPath());
    }
}
