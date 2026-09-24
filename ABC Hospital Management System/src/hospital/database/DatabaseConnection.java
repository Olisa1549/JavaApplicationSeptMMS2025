package hospital.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Central SQL Server connection provider used by the existing DAO layer. */
public class DatabaseConnection {
    private static final String DEFAULT_URL = "jdbc:sqlserver://localhost;"
            + "databaseName=LifeSaverHospitalDatabase;"
            + "encrypt=false;trustServerCertificate=true";
    private static final String URL = ApplicationConfig.get("database.url", DEFAULT_URL);
    private static final String USERNAME = ApplicationConfig.get("database.username", "lifesaver_app");
    private static final String PASSWORD = ApplicationConfig.get("database.password", "LifeSaverDb@12345");

    private DatabaseConnection() { }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQL Server JDBC Driver not found. Check lib/mssql-jdbc-12.4.1.jre11.jar.", e);
        }
    }

    public static boolean isAvailable() {
        try (Connection connection = getConnection()) {
            return connection.isValid(3);
        } catch (SQLException e) {
            return false;
        }
    }
}
