package org.example.jdbcsql;

import org.example.dao.FacturaProductoDao;
import org.example.entities.FacturaProducto;
import org.example.entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacturaProductoJDBC_MySql implements FacturaProductoDao {
    private Connection conn;

    public FacturaProductoJDBC_MySql(Connection con) {
        this.conn = con;
    }

    @Override
    public int insertFacturaProducto(int idFactura, int idProducto, int cantidad) throws SQLException {
        String query = "INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        try {
            ps.setInt(1, idFactura);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
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
        return 0;
    }

    @Override
    public void updateFacturaProducto(FacturaProducto Factura) throws SQLException {
        // Consulta para verificar si el producto factura existe
        String select = "SELECT * FROM Factura_Producto WHERE idFactura = ?";
        PreparedStatement psSelect = null;
        PreparedStatement psUpdate = null;
        ResultSet rs = null;

        try {
            //Preparando el SELECT para verificar la existencia de la factura
            psSelect = conn.prepareStatement(select);
            psSelect.setInt(1, Factura.getIdFactura());
            rs = psSelect.executeQuery();

            // Si la factura existe, actualizamos
            if (rs.next()) {
                // Consulta para actualizar la factura
                String update = "UPDATE Factura_Producto SET cantidad = ? WHERE idFactura = ? AND idProducto = ?";

                // Preparando la actualización
                psUpdate = conn.prepareStatement(update);

                psUpdate.setInt(1, Factura.getCantidad());
                psUpdate.setInt(2, Factura.getIdFactura());
                psUpdate.setInt(3,Factura.getIdProducto());


                // Ejecutando la actualización
                int rowsAffected = psUpdate.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("FacturaProducto actualizado con éxito.");
                } else {
                    System.out.println("No se pudo actualizar la facturaProducto.");
                }
            } else {
                System.out.println("facturaProducto no encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Relanzar la excepción para que la maneje la capa superior
        } finally {
            // Cerrar ResultSet, PreparedStatement
            if (rs != null) {
                rs.close();
            }
            if (psSelect != null) {
                psSelect.close();
            }
            if (psUpdate != null) {
                psUpdate.close();
            }
        }

        conn.commit();
    }

    @Override
    public void deleteFacturaProducto(int idFactura, int idProducto) throws SQLException {
        // Consulta para verificar si la factura existe
        String select = "SELECT * FROM Factura_Producto WHERE idFactura = ? AND idProducto = ?";
        // Consulta para eliminar la factura
        String delete = "DELETE FROM Factura_Producto WHERE idFactura = ?  AND idProducto = ?";

        PreparedStatement psSelect = null;
        PreparedStatement psDelete = null;
        ResultSet rs = null;

        try {
            // Preparar el SELECT para verificar la existencia de la factura
            psSelect = conn.prepareStatement(select);
            psSelect.setInt(1, idFactura);
            psSelect.setInt(2, idProducto);
            rs = psSelect.executeQuery();

            // Si la factura existe, se elimina
            if (rs.next()) {
                // Preparar la eliminación
                psDelete = conn.prepareStatement(delete);
                psDelete.setInt(1, idFactura);
                psDelete.setInt(2, idProducto);

                // Ejecutar la eliminación
                int rowsAffected = psDelete.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("FacturaProducto eliminada con éxito.");
                } else {
                    System.out.println("No se pudo eliminar la facturaProducto.");
                }
            } else {
                System.out.println("FacturaProducto no encontrada.");
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
        }
        conn.commit();
    }

    @Override
    public FacturaProducto getFacturaProducto(int idFactura, int idProducto) throws SQLException {
        String select = "select * from Factura_Producto where idFactura = ? AND idProducto = ?";
        PreparedStatement ps = conn.prepareStatement(select);
        ps.setInt(1, idFactura);
        ps.setInt(2, idProducto);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new FacturaProducto(rs.getInt("idFactura"), rs.getInt("idProducto"), rs.getInt("cantidad"));
        } else {
            return null; // o lanzar una excepción si el producto no se encuentra
        }
    }

    @Override
    public List<FacturaProducto> getFacturasProductos() throws SQLException {
        ArrayList<FacturaProducto> result = new ArrayList<FacturaProducto>();
        String select = "SELECT * FROM Factura_Producto";
        PreparedStatement ps = this.conn.prepareStatement(select);

        //ResultSet guardara el resultado al ejecutar el estado de la consulta
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            result.add(new FacturaProducto(rs.getInt("idFactura"), rs.getInt("idProducto"), rs.getInt("cantidad")));
        }
        return result;
    }
}
