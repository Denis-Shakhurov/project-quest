package repository;

import model.User;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository extends BaseRepository {

    public static Long save(User user) throws SQLException {
        var sql = "INSERT INTO users (name) VALUES (?)";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.executeUpdate();
            var generatedKey = stmt.getGeneratedKeys();
            if (generatedKey.next()) {
                user.setId(generatedKey.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
            return user.getId();
        }
    }

    public static List<User> getAll() throws SQLException {
        var sql = "SELECT * FROM users";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                var name = resultSet.getString("name");
                var id = resultSet.getLong("id");
                var user = new User(name);
                user.setId(id);
                users.add(user);
            }
            return users;
        }
    }

    public static Optional<User> findById(Long id) throws SQLException {
        var sql = "SELECT * FROM users WHERE id = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var user = new User(name);
                user.setId(id);
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public static boolean findByName(String str) throws SQLException {
        var sql = "SELECT * FROM users WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, str);
            var resultSet = stmt.executeQuery();
            List<User> users = new ArrayList<>();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var id = resultSet.getLong("id");
                var user = new User(name);
                user.setId(id);
                users.add(user);
            }
            return users.isEmpty();
        }
    }
}
