package income;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Janusz on 02.11.2016.
 */
public class ConnectionManager {
    private static String url = "jdbc:h2:~/income";
    private static String driverName = "org.h2.Driver";
    private static String username = "user";
    private static String password = "T3stP@sword";
    private static Connection con;
    private ConnectionManager(){}
    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }
        return con;
    }
}
