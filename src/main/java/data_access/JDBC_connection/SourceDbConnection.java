package data_access.JDBC_connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SourceDbConnection {
    private static Connection connection;


    public static final String DB_NAME = "sourceDB_SEP4A19G2";
    private static String dbAddress = "10.200.131.2";
    private static String dbUsername = "SEP4A19G2";
    private static String dbPassword = "SEP4A19G2";

    private static String connectionUrl = "jdbc:sqlserver://" + dbAddress + ";database=" + DB_NAME + ";user=" + dbUsername + ";password=" + dbPassword;


    /**
     * Lazy implementation of the database connection
     *
     * @return connection
     */
    public static Connection getConnection() {
        if (connection != null)
            return connection;
        return getNewConnection();
    }

    /**
     * Creates a new database connection through JDBC driver
     *
     * @return connection
     */
    private static Connection getNewConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("DB connected");
        return connection;
    }
}
