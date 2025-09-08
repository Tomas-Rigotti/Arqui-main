package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CargaDatosIniciales {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Entregable1";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Carga los datos desde los archivos CSV a las tablas.
     * @throws SQLException si ocurre un error en la conexión o consulta SQL.
     * @throws IOException si ocurre un error al leer los archivos.
     */
    public static void loadData() throws SQLException, IOException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            // Cargar clientes.csv
            String insertClienteSQL = "INSERT INTO clientes (id_cliente, nombre) VALUES (?, ?)";
            loadCsv("clientes.csv", insertClienteSQL, conn, new int[]{1, 2});

            // Cargar productos.csv
            String insertProductoSQL = "INSERT INTO productos (id_producto, nombre, valor) VALUES (?, ?, ?)";
            loadCsv("productos.csv", insertProductoSQL, conn, new int[]{1, 2, 3});

            // Cargar facturas.csv
            String insertFacturaSQL = "INSERT INTO facturas (id_factura, id_cliente) VALUES (?, ?)";
            loadCsv("facturas.csv", insertFacturaSQL, conn, new int[]{1, 2});

            // Cargar facturas-productos.csv
            String insertFacturaProductoSQL = "INSERT INTO facturas_productos (id_factura, id_producto, cantidad) VALUES (?, ?, ?)";
            loadCsv("facturas-productos.csv", insertFacturaProductoSQL, conn, new int[]{1, 2, 3});
        }
    }

    /**
     * Helper para leer un CSV y cargar datos.
     * @param filePath Ruta al archivo CSV.
     * @param sql La consulta SQL de inserción.
     * @param conn La conexión a la base de datos.
     * @param columnIndices Los índices de las columnas a usar para la inserción.
     * @throws IOException si ocurre un error al leer el archivo.
     * @throws SQLException si ocurre un error con la consulta.
     */
    private static void loadCsv(String filePath, String sql, Connection conn, int[] columnIndices) throws IOException, SQLException {
        try (CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(filePath));
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println("Cargando datos desde: " + filePath);
            for (CSVRecord record : parser) {
                // Configurar los parámetros del PreparedStatement dinámicamente
                for (int i = 0; i < columnIndices.length; i++) {
                    int colIndex = columnIndices[i];
                    String value = record.get(colIndex - 1);
                    if (colIndex == 3 && filePath.equals("productos.csv")) {
                        pstmt.setBigDecimal(i + 1, new java.math.BigDecimal(value));
                    } else {
                        pstmt.setString(i + 1, value);
                    }
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Carga de " + filePath + " completada.");
        }
    }
}
