package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionManager {
    public static Connection getConnection(String DRIVER, String DBURL, String USER, String PASSWORD) throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(DBURL, USER, PASSWORD);
    }
}
