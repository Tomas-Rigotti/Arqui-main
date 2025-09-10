package org.example.factory;

import org.example.jdbcsql.ClienteJDBC_MySql;
import org.example.jdbcsql.FacturaJDBC_MySql;
import org.example.jdbcsql.FacturaProductoJDBC_MySql;
import org.example.jdbcsql.ProductoJDBC_MySql;


import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlFactoryDao extends FactoryDao {
    private static MySqlFactoryDao instance = null;

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String uri = "jdbc:mysql://localhost:3306/Entrega1?createDatabaseIfNotExist=true";
    public static Connection conn;

    private MySqlFactoryDao() {
    }

    //Singleton
    public static synchronized MySqlFactoryDao getInstance() {
        if (instance == null) {
            instance = new MySqlFactoryDao();
        }
        return instance;
    }

    //Crea la conexion con la base sql
    public Connection createConnection() {
        if (conn != null) {
            return conn;
        }
        String driver = DRIVER;
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(uri, "root", "");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection() {
        if (conn != null){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ClienteJDBC_MySql getClienteDao() throws SQLException {
        return new ClienteJDBC_MySql(createConnection());
    }

    @Override
    public FacturaJDBC_MySql getFacturaDao() throws SQLException {
        return new FacturaJDBC_MySql(createConnection());
    }

    @Override
    public FacturaProductoJDBC_MySql getFacturaProducto() throws SQLException {
        return new FacturaProductoJDBC_MySql(createConnection());
    }

    @Override
    public ProductoJDBC_MySql getProductoDao() throws SQLException {
        return new ProductoJDBC_MySql(createConnection());
    }
}
