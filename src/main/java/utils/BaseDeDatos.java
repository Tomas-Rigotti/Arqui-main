package utils;

import mysql.MySQLDAOFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDeDatos {

    /*private static final String DB_URL = "jdbc:mysql://localhost:3306/Entregable1";
    private static final String USER = "root";
    private static final String PASSWORD = "";*/



    /**
     * Crea las tablas en la base de datos.
     * @throws SQLException si ocurre un error en la conexión o consulta SQL.
     */
    public static void createSchema() throws SQLException, ClassNotFoundException {
        try /*(Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)*/{
            Connection conn = MySQLDAOFactory.getConnection();
            Statement stmt = conn.createStatement(); {

            // SQL para crear las tablas
            String createClientesTable = "CREATE TABLE IF NOT EXISTS clientes ("
                    + "id_cliente INT PRIMARY KEY,"
                    + "nombre VARCHAR(255)"
                    + ")";

            String createProductosTable = "CREATE TABLE IF NOT EXISTS productos ("
                    + "id_producto INT PRIMARY KEY,"
                    + "nombre VARCHAR(255),"
                    + "valor DECIMAL(10, 2)"
                    + ")";

            String createFacturasTable = "CREATE TABLE IF NOT EXISTS facturas ("
                    + "id_factura INT PRIMARY KEY,"
                    + "id_cliente INT,"
                    + "FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)"
                    + ")";

            String createFacturasProductosTable = "CREATE TABLE IF NOT EXISTS facturas_productos ("
                    + "id_factura INT,"
                    + "id_producto INT,"
                    + "cantidad INT,"
                    + "FOREIGN KEY (id_factura) REFERENCES facturas(id_factura),"
                    + "FOREIGN KEY (id_producto) REFERENCES productos(id_producto)"
                    + ")";

            // Ejecutar las consultas de creación de tablas
            stmt.execute(createClientesTable);
            stmt.execute(createProductosTable);
            stmt.execute(createFacturasTable);
            stmt.execute(createFacturasProductosTable);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
