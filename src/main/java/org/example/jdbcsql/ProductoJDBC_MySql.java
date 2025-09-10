package org.example.jdbcsql;

import org.example.dao.ProductoDao;
import org.example.entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoJDBC_MySql implements ProductoDao {
    private Connection conn;

    public ProductoJDBC_MySql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int insertProducto(int idProducto, String nombre, int valor) throws SQLException {
        String query = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        try{
            ps.setInt(1, idProducto);
            ps.setString(2, nombre);
            ps.setInt(3, valor);
            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ps.close();
            conn.commit();
        }
        //hasta aca todo el codigo puede estar en la tabla facturaProducto dentro del metodo insertar y luego invocarlo aca
        return 0;
    }

    @Override
    public void updateProducto(Producto idProducto) throws SQLException {
        //Consulta para verificar si el producto existe
        String select = "SELECT * FROM Producto WHERE idProducto = ?";
        PreparedStatement psSelect = null;
        PreparedStatement psUpdate = null;
        ResultSet rs = null;

        try {
            //Preparando el SELECT para verificar la existencia del producto
            psSelect = conn.prepareStatement(select);
            psSelect.setInt(1, idProducto.getId());
            rs = psSelect.executeQuery();

            //Si el producto existe, actualizamos
            if (rs.next()) {
                //Consulta para actualizar el producto
                String update = "UPDATE Producto SET nombre = ?, valor = ? WHERE idProducto = ?";

                //Preparando la actualización
                psUpdate = conn.prepareStatement(update);
                psUpdate.setString(1, idProducto.getNombre());
                psUpdate.setFloat(2, idProducto.getValor());
                psUpdate.setFloat(3, idProducto.getId());

                //Ejecutando la actualización
                int rowsAffected = psUpdate.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Producto actualizado con éxito.");
                } else {
                    System.out.println("No se pudo actualizar el producto.");
                }
            } else {
                System.out.println("Producto no encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; //Relanzar la excepción para que la maneje la capa superior
        } finally {
            //Cerrar ResultSet, PreparedStatement
            if (rs != null) {
                rs.close();
            }
            if (psSelect != null) {
                psSelect.close();
            }
            if (psUpdate != null) {
                psUpdate.close();
            }
            conn.commit();
        }
    }

    @Override
    public void deleteProducto(int idProducto) throws SQLException {
        // Consulta para verificar si el producto existe
        String select = "SELECT * FROM Producto WHERE idProducto = ?";
        // Consulta para eliminar el producto
        String delete = "DELETE FROM Producto WHERE idProducto = ?";

        PreparedStatement psSelect = null;
        PreparedStatement psDelete = null;
        ResultSet rs = null;

        try {
            // Preparar el SELECT para verificar la existencia del producto
            psSelect = conn.prepareStatement(select);
            psSelect.setInt(1, idProducto);
            rs = psSelect.executeQuery();

            // Si el producto existe, se elimina
            if (rs.next()) {
                //Preparar la eliminación
                psDelete = conn.prepareStatement(delete);
                psDelete.setInt(1, idProducto);

                //Ejecutar la eliminación
                int rowsAffected = psDelete.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Producto eliminado con éxito.");
                } else {
                    System.out.println("No se pudo eliminar el producto.");
                }
            } else {
                System.out.println("Producto no encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Relanzar la excepción para que la maneje la capa superior
        } finally {
            // Cerrar ResultSet y PreparedStatement
            if (rs != null) {
                rs.close();
            }
            if (psSelect != null) {
                psSelect.close();
            }
            if (psDelete != null) {
                psDelete.close();
            }

            conn.commit();
        }
    }

    @Override
    public Producto getProducto(int idProducto) throws SQLException {
        String select = "select * from Producto where idProducto = ?";
        PreparedStatement ps = conn.prepareStatement(select);
        ps.setInt(1, idProducto);
        ResultSet rs = ps.executeQuery();

        // Verificar si hay un resultado
        if (rs.next()) {
            return new Producto(rs.getInt("idProducto"), rs.getString("nombre"), rs.getFloat("valor"));
        } else {
            return null; // o lanzar una excepción si el producto no se encuentra
        }
    }

    @Override
    public List<Producto> getProductos() throws SQLException {
        ArrayList<Producto> result = new ArrayList<Producto>();
        String select = "SELECT * FROM Producto";
        PreparedStatement ps = conn.prepareStatement(select);

        //ResultSet guardara el resultado al ejecutar el estado de la consulta
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            result.add(new Producto(rs.getInt("idProducto"), rs.getString("nombre"), rs.getFloat("valor")));
        }
        return result;
    }

    public Producto getProductoMasVendido() throws SQLException {
        String select = "SELECT p.idProducto, p.nombre, p.valor, SUM(pf.cantidad * p.valor) AS recaudacion "
                + "FROM Producto p "
                + "JOIN Factura_Producto pf ON p.idProducto = pf.idProducto "
                + "GROUP BY p.idProducto "
                + "ORDER BY recaudacion DESC "
                + "LIMIT 1";
        PreparedStatement ps = conn.prepareStatement(select);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Producto(rs.getInt("idProducto"), rs.getString("nombre"), rs.getFloat("valor"));
        } else {
            return null; // o lanzar una excepción si el producto no se encuentra
        }
    }
}
