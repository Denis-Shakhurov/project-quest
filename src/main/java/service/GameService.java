package service;

import io.javalin.http.NotFoundResponse;
import model.game.Game;
import repository.GameRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GameService {

    public static Long create(Game game) throws SQLException {
        return GameRepository.save(game);
    }

    public static Optional<Game> findById(Long id) throws SQLException {
        return GameRepository.findById(id);
    }

    public static List<Game> getAllGameForUser(Long userId) throws SQLException {
        return GameRepository.getAllGameForUser(userId);
    }

    public static void update(Game game) throws SQLException {
        GameRepository.update(game);
    }

    public static void destroy(Long userId) throws SQLException{
        GameRepository.deleteAllGameForUser(userId);
    }
}
