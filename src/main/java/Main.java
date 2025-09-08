import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import factory.MySQLDAOFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import static utils.BaseDeDatos.createSchema;
import static utils.CargaDatosIniciales.loadData;

// Si usas Maven, agrega estas dependencias a tu pom.xml:
// <dependencies>
//     <!-- MySQL Connector/J -->
//     <dependency>
//         <groupId>mysql</groupId>
//         <artifactId>mysql-connector-java</artifactId>
//         <version>8.0.33</version>
//     </dependency>
//     <!-- Apache Commons CSV -->
//     <dependency>
//         <groupId>org.apache.commons</groupId>
//         <artifactId>commons-csv</artifactId>
//         <version>1.10.0</version>
//     </dependency>
// </dependencies>

public class Main {
    /*private static final String DB_URL = "jdbc:mysql://localhost:3306/Entregable1";
    private static final String USER = "root";
    private static final String PASSWORD = "";*/


    public static void main(String[] args) {

        //En el main faltaria pedir la instancia de la factory
        try {
            // 1. Crear el esquema de la base de datos
            createSchema();
            System.out.println("Esquema de la base de datos creado exitosamente.");

            // 2. Cargar los datos desde los archivos CSV
            loadData();
            System.out.println("Datos cargados exitosamente.");

            System.out.println("----------------------------------------");

            // 3. Obtener el producto que más recaudó
            findMostProfitableProduct();
            System.out.println("----------------------------------------");

            // 4. Imprimir la lista de clientes ordenada por facturación
            listClientsByRevenue();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Esto iria en los DAOs, los metodos abstractos en las interfaces y la conexion en los DAOs de MySQL
    //las pide en la FactoryMySQL, no se si deje bien la sintaxis de las consultas

    /**
     * Retorna el producto que más recaudó.
     * @throws SQLException si ocurre un error en la consulta SQL.
     */
    public static void findMostProfitableProduct() throws SQLException {
        String query = "SELECT p.nombre, SUM(fp.cantidad * p.valor) AS recaudacion "
                + "FROM productos p "
                + "JOIN facturas_productos fp ON p.id_producto = fp.id_producto "
                + "GROUP BY p.nombre "
                + "ORDER BY recaudacion DESC "
                + "LIMIT 1";

        try{ /*(Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);*/
        Connection conn = MySQLDAOFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query); {

            System.out.println("Producto que más recaudó:");
            if (rs.next()) {
                String nombreProducto = rs.getString("nombre");
                double recaudacion = rs.getDouble("recaudacion");
                System.out.printf("Producto: %s, Recaudación: $%.2f%n", nombreProducto, recaudacion);
            } else {
                System.out.println("No se encontraron productos.");
            }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Imprime una lista de clientes, ordenada por a cuál se le facturó más.
     * @throws SQLException si ocurre un error en la consulta SQL.
     */
    public static void listClientsByRevenue() throws SQLException {
        String query = "SELECT c.nombre, SUM(fp.cantidad * p.valor) AS total_facturado "
                + "FROM clientes c "
                + "JOIN facturas f ON c.id_cliente = f.id_cliente "
                + "JOIN facturas_productos fp ON f.id_factura = fp.id_factura "
                + "JOIN productos p ON fp.id_producto = p.id_producto "
                + "GROUP BY c.nombre "
                + "ORDER BY total_facturado DESC";

        try /*(Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);*/{
            Connection conn = MySQLDAOFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query); {

            System.out.println("Lista de clientes ordenada por facturación:");
            while (rs.next()) {
                String nombreCliente = rs.getString("nombre");
                double totalFacturado = rs.getDouble("total_facturado");
                System.out.printf("Cliente: %s, Total Facturado: $%.2f%n", nombreCliente, totalFacturado);
            }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
