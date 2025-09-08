package factory;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLDAOFactory extends AbstractFactory{

    private static MySQLDAOFactory instance = null;

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/Entregable1";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private MySQLDAOFactory() {
    }

    public static synchronized MySQLDAOFactory getInstance() {
        if (instance == null) {
            instance = new MySQLDAOFactory();
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        return ConnectionManager.getConnection(DRIVER, DBURL, USER, PASSWORD);
    }

}
