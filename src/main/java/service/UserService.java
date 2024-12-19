package service;

import model.User;
import repository.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {

    public static List<User> getAll() throws SQLException {
        return UserRepository.getAll();
    }

    public static Optional<User> findById(Long id) throws SQLException {
        return UserRepository.findById(id);
    }

    public static Optional<User> findByEmail(String email) throws SQLException {
        return UserRepository.findByEmail(email);
    }

    public static boolean existByEmail(String email) throws SQLException {
        return UserRepository.existByEmail(email);
    }

    public static Long create(User user) throws SQLException {
        return UserRepository.save(user);
    }

    public static void delete(Long id) throws SQLException {
        UserRepository.delete(id);
    }
}
