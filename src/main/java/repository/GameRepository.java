package repository;

import model.FactoryGame;
import model.game.CalcGame;
import model.game.Game;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class GameRepository extends BaseRepository {

    public static Long save(Game game) throws SQLException {
        var sql = "INSERT INTO games (name, user_id) VALUES (?, ?)";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, game.getName());
            stmt.setLong(2, game.getUserId());
            stmt.executeUpdate();
            var generatedKey = stmt.getGeneratedKeys();
            if (generatedKey.next()) {
                game.setId(generatedKey.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
        return game.getId();
    }

    public static Optional<Game> findByName(String str) throws SQLException {
        var sql = "SELECT * FROM games WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, str);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var id = resultSet.getLong("id");
                var game = new CalcGame();
                game.setId(id);
                return Optional.of(game);
            }
        }
        return Optional.empty();
    }

    public static Optional<Game> findByNId(Long id) throws SQLException {
        var sql = "SELECT * FROM games WHERE id = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var factoryGame = new FactoryGame();
                var name = resultSet.getString("name");
                var game = factoryGame.getGame(name);
                game.setId(id);
                return Optional.of(game);
            }
        }
        return Optional.empty();
    }
}
