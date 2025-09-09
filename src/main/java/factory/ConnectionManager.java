package factory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static Connection getConnection(String DRIVER, String DBURL, String USER, String PASSWORD)
            throws SQLException {

        try {
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
            return DriverManager.getConnection(DBURL, USER, PASSWORD);
            
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            
            throw new SQLException("Error al conectar a la base de datos", e);
        }
    }
}
