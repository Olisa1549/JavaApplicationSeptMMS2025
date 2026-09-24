package hospital.services;

import hospital.database.DatabaseConnection;
import hospital.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class UserManagementService {

    public UserManagementService() {
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = """
            SELECT
                UserId,
                Username,
                PasswordHash,
                Role,
                StaffId,
                IsActive
            FROM Users
            ORDER BY UserId
            """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet resultSet =
                        statement.executeQuery()) {

            while (resultSet.next()) {

                users.add(mapUser(resultSet));
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error retrieving users: "
                    + e.getMessage()
            );
        }

        return users;
    }

    public User getUserById(int userId) {

        String sql = """
            SELECT
                UserId,
                Username,
                PasswordHash,
                Role,
                StaffId,
                IsActive
            FROM Users
            WHERE UserId = ?
            """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error finding user: "
                    + e.getMessage()
            );
        }

        return null;
    }

    public boolean createUser(
            String username,
            String password,
            String role,
            Integer staffId) {

        if (username == null
                || username.isBlank()) {
            return false;
        }

        if (password == null
                || password.isBlank()) {
            return false;
        }

        if (role == null
                || role.isBlank()) {
            return false;
        }

        String sql = """
            INSERT INTO Users
            (
                Username,
                PasswordHash,
                Role,
                StaffId,
                IsActive
            )
            VALUES (?, ?, ?, ?, 1)
            """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);

            if (staffId == null || staffId == 0) {

                statement.setNull(
                        4,
                        Types.INTEGER
                );

            } else {

                statement.setInt(4, staffId);
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error creating user: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public boolean updateUser(
            int userId,
            String username,
            String password,
            String role,
            Integer staffId) {

        String sql = """
            UPDATE Users
            SET
                Username = ?,
                PasswordHash = ?,
                Role = ?,
                StaffId = ?
            WHERE UserId = ?
            """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);

            if (staffId == null || staffId == 0) {

                statement.setNull(
                        4,
                        Types.INTEGER
                );

            } else {

                statement.setInt(4, staffId);
            }

            statement.setInt(5, userId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error updating user: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public boolean toggleUserStatus(int userId) {

        String sql = """
            UPDATE Users
            SET IsActive =
                CASE
                    WHEN IsActive = 1 THEN 0
                    ELSE 1
                END
            WHERE UserId = ?
            """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error changing user status: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public boolean deleteUser(int userId) {

        String sql = """
            DELETE FROM Users
            WHERE UserId = ?
            """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error deleting user: "
                    + e.getMessage()
            );

            return false;
        }
    }

    private User mapUser(ResultSet resultSet)
            throws SQLException {

        User user = new User();

        user.setId(
                resultSet.getInt("UserId")
        );

        user.setUsername(
                resultSet.getString("Username")
        );

        user.setPasswordHash(
                resultSet.getString("PasswordHash")
        );

        user.setRole(
                resultSet.getString("Role")
        );

        int staffId =
                resultSet.getInt("StaffId");

        if (resultSet.wasNull()) {
            user.setStaff(null);
        } else {
            user.setStaff(
                    String.valueOf(staffId)
            );
        }

        user.setActive(
                resultSet.getBoolean("IsActive")
        );

        return user;
    }
}