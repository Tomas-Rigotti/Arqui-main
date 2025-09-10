package org.example.factory;

import org.example.dao.ClienteDao;
import org.example.dao.FacturaDao;
import org.example.dao.FacturaProductoDao;
import org.example.jdbcsql.ProductoJDBC_MySql;

import java.sql.SQLException;

public class DerbyFactoryDao extends FactoryDao {

    private static DerbyFactoryDao instance = null;

    public static synchronized DerbyFactoryDao getInstance() {
        if (instance == null) {
            instance = new DerbyFactoryDao();
        }
        return instance;
    }

    @Override
    public ClienteDao getClienteDao() throws SQLException {
        return null;
    }

    @Override
    public FacturaDao getFacturaDao() throws SQLException {
        return null;
    }

    @Override
    public FacturaProductoDao getFacturaProducto() throws SQLException {
        return null;
    }

    @Override
    public ProductoJDBC_MySql getProductoDao() throws SQLException {
        return null;
    }
}
