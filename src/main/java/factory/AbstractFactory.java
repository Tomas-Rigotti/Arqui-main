package factory;

import dao.ClienteDAO;
import mysql.MySQLDAOFactory;

public abstract class AbstractFactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;

    public abstract ClienteDAO getClienteDAO();
    // public abstract FacturaDAO getFacturaDAO();
    // public abstract FacturaProductoDAO getFacturaProductoDAO();
    // public abstract ProductoDAO getProductoDAO();

    //Aca irian los DAOs abstractos
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

    /*Esto seria con el singleton pero no lo pude hacer andar todavia
    public static AbstractFactory getDAOFactory(int whichFactory) {
        if(instance == null){
            synchronized (AbstractFactory.class) {
                if(instance == null){
                    switch (whichFactory) {
                        case MYSQL_JDBC:
                            instance = new MySQLDAOFactory();
                            break;
                        case DERBY_JDBC:
                            return null; //DerbyDAOFactory.getInstance
                            break;
                        default:
                            return null;
                            break;
                    }
                }
            }
        }
        return instance;
    }*/
}
