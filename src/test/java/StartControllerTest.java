import controller.StartController;
import dto.UserPage;
import io.javalin.http.Context;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import repository.UserRepository;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StartControllerTest {
    private static Context ctx;

    @BeforeEach
    public final void setUp() throws Exception {
        ctx = mock(Context.class);
    }

    @Test
    @DisplayName("Valid userId cookie returns correct user data and renders page")
    public void validUserIdCookieReturnsUserDataTest() throws SQLException {
        when(ctx.cookie("userId")).thenReturn("1");

        User expectedUser = new User("John", "john@email.com", "pass123", "beginner");
        expectedUser.setId(1L);

        try (MockedStatic<UserRepository> userRepositoryMock = mockStatic(UserRepository.class)) {

            userRepositoryMock.when(() -> UserRepository.findById(1L)).thenReturn(Optional.of(expectedUser));
            StartController.index(ctx);

            verify(ctx).render(eq("start.jte"), argThat(model -> {
                UserPage page = (UserPage) ((Map<String, Object>) model).get("page");
                return page.getUser().getId().equals(1L) &&
                        page.getUser().getName().equals("John") &&
                        page.getUser().getEmail().equals("john@email.com");
            }));
        }
    }

    @Test
    @DisplayName("Empty userId cookie returns null user")
    public void emptyUserIdCookieReturnsNullUserTest() throws SQLException {
        when(ctx.cookie("userId")).thenReturn("");

        StartController.index(ctx);

        verify(ctx).render(eq("start.jte"), argThat(model -> {
            UserPage page = (UserPage) ((Map<String,Object>)model).get("page");
            return page.getUser() == null;
        }));
    }
}
