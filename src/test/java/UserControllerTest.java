import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import model.User;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest {
    Javalin app;
    private static MockWebServer mockBackEnd;

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
    public void pageUserShowNoFoundTest() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/users/9999999");

            assertEquals(404, response.code());
        });
    }

    @Test
    public void createUserSuccessTest() throws SQLException {
        User userExpected = new User("TestUser");
        Long id = UserRepository.save(userExpected);
        JavalinTest.test(app, (server, client) -> {
            client.post("/");

            var userActual = UserRepository.findById(id).get();
            assertEquals(userExpected.getName(), userActual.getName());
        });
    }
}
