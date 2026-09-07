package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static Connection connection;

    public static Connection getConnection() {

        try {

            if (connection == null || connection.isClosed()) {

                connection = DriverManager.getConnection(
                        BaseClass.getConfigProperty("dbURL"),
                        Environment.getDBUsername(),
                        Environment.getDBPassword());
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection() {

        try {

            if (connection != null)

                connection.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

}