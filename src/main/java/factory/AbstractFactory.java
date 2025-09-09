package factory;

import dao.ClienteDAO;
import dao.ProductoDAO;
import mysql.MySQLDAOFactory;

public abstract class AbstractFactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;

    public abstract ClienteDAO getClienteDAO();
    //public abstract FacturaDAO getFacturaDAO();
    // public abstract FacturaProductoDAO getFacturaProductoDAO();
     public abstract ProductoDAO getProductoDAO();

    public static AbstractFactory getFactory(int type) {
        switch (type) {
            case MYSQL_JDBC:
                return MySQLDAOFactory.getInstance();
            case DERBY_JDBC:
                return null; //return DerbyDAOFactory.getInstance();
            default:
                return null;
        }
    }

}
