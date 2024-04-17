package employeemanage;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection; // Maintain a single connection (or connection pool)

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Code to establish connection (similar to what you have now)
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();  // Close the connection
        }
    }
}
