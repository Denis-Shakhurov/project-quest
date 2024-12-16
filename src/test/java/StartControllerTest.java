import controller.StartController;
import dto.UsersPage;
import io.javalin.http.Context;
import model.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import repository.UserRepository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class StartControllerTest {
    private final Context ctx =  mock(Context.class);

    // Successfully retrieves all users from repository and renders index page
    @Test
    public void indexRendersUsersPageTest() throws SQLException {
        List<User> users = Arrays.asList(new User("user1"), new User("user2"));

        try (MockedStatic<UserRepository> mockedRepo = mockStatic(UserRepository.class)) {
            mockedRepo.when(UserRepository::getAll).thenReturn(users);

            StartController.index(ctx);

            ArgumentCaptor<String> templateCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<Map<String, Object>> modelCaptor = ArgumentCaptor.forClass(Map.class);

            verify(ctx).render(templateCaptor.capture(), modelCaptor.capture());

            assertEquals("start.jte", templateCaptor.getValue());
            UsersPage capturedPage = (UsersPage) modelCaptor.getValue().get("page");
            assertEquals(users, capturedPage.getUsers());
        }
    }

    // Handles SQL exceptions when database connection fails
    @Test
    public void indexHandlesSqlExceptionTest() {

        try (MockedStatic<UserRepository> mockedRepo = mockStatic(UserRepository.class)) {
            mockedRepo.when(UserRepository::getAll).thenThrow(new SQLException("DB connection failed"));

            assertThrows(SQLException.class, () -> {
                StartController.index(ctx);
            });

            verify(ctx, never()).render(anyString(), any());
        }
    }
}
