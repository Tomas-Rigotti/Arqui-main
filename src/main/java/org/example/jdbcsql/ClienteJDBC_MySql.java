package org.example.jdbcsql;

import org.example.dao.ClienteDao;
import org.example.entities.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteJDBC_MySql implements ClienteDao {

    private Connection conn;



    public ClienteJDBC_MySql(Connection conn) throws SQLException {
        this.conn = conn;
    }

    @Override
    public int insertCliente(int idCliente, String nombre, String email) throws SQLException {
        String query = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        try{
            ps.setInt(1, idCliente);
            ps.setString(2, nombre);
            ps.setString(3, email);
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
    public void updateCliente(Cliente cliente) throws SQLException {
    // Consulta para verificar si el producto factura existe
        String select = "SELECT * FROM Cliente WHERE idCliente = ?";
        PreparedStatement psSelect = null;
        PreparedStatement psUpdate = null;
        ResultSet rs = null;

        try {
            //Preparando el SELECT para verificar la existencia de la factura
            psSelect = conn.prepareStatement(select);
            psSelect.setInt(1, cliente.getIdCliente());
            rs = psSelect.executeQuery();

            // Si la factura existe, actualizamos
            if (rs.next()) {
                // Consulta para actualizar la factura
                String update = "UPDATE Cliente SET nombre = ?, email = ? WHERE idCliente = ?";

                // Preparando la actualización
                psUpdate = conn.prepareStatement(update);
                psUpdate.setString(1, cliente.getNombre());
                psUpdate.setString(2, cliente.getEmail());
                psUpdate.setInt(3, cliente.getIdCliente());


                // Ejecutando la actualización
                int rowsAffected = psUpdate.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Cliente actualizado con éxito.");
                } else {
                    System.out.println("No se pudo actualizar el/la Cliente.");
                }
            } else {
                System.out.println("Cliente no encontrada.");
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
            conn.commit();
        }
    }
    @Override
    public void deleteCliente (int idCliente) throws SQLException {
        // Consulta para verificar si el producto existe
        String select = "SELECT * FROM Cliente WHERE idCliente = ?";
        // Consulta para eliminar el producto
        String delete = "DELETE FROM Cliente WHERE idCliente = ?";

        PreparedStatement psSelect = null;
        PreparedStatement psDelete = null;
        ResultSet rs = null;

        try {
            // Preparar el SELECT para verificar la existencia del producto
            psSelect = conn.prepareStatement(select);
            psSelect.setInt(1, idCliente);
            rs = psSelect.executeQuery();

            // Si el producto existe, se elimina
            if (rs.next()) {
                //Preparar la eliminación
                psDelete = conn.prepareStatement(delete);
                psDelete.setInt(1, idCliente);

                //Ejecutar la eliminación
                int rowsAffected = psDelete.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Cliente eliminado con éxito.");
                } else {
                    System.out.println("No se pudo eliminar el Cliente.");
                }
            } else {
                System.out.println("Cliente no encontrado.");
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
    public Cliente getCliente (int idCliente) throws SQLException {
        String select = "select * from Cliente where idCliente = ?";
        PreparedStatement ps = conn.prepareStatement(select);
        ps.setInt(1, idCliente);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Cliente(rs.getInt("idCliente"), rs.getString("nombre"), rs.getString("email"));
        } else {
            return null; // o lanzar una excepción si el producto no se encuentra
        }
    }

    @Override
    public List<Cliente> getClientes () throws SQLException {
        ArrayList<Cliente> result = new ArrayList<Cliente>();
        String select = "SELECT * FROM Cliente";
        PreparedStatement ps = this.conn.prepareStatement(select);

        //ResultSet guardara el resultado al ejecutar el estado de la consulta
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            result.add(new Cliente(rs.getInt("idCliente"), rs.getString("nombre"), rs.getString("email")));
        }
        return result;
    }


        //Enunciado 4
    @Override
    public List<Cliente> getMasFacturadosOrdenados() throws SQLException {
        ArrayList<Cliente> result = new ArrayList<Cliente>();
        String select = "SELECT c.idCliente, c.nombre, c.email, SUM(fp.cantidad * p.valor) AS total_facturado " +
                "FROM Cliente c " +
                "JOIN Factura f ON c.idCliente = f.idCliente " +
                "JOIN Factura_Producto fp ON f.idFactura = fp.idFactura " +
                "JOIN Producto p ON fp.idProducto = p.idProducto " +
                "GROUP BY c.idCliente, c.nombre, c.email " +
                "ORDER BY total_facturado DESC";

        PreparedStatement ps = this.conn.prepareStatement(select);

        //ResultSet guardara el resultado al ejecutar el estado de la consulta
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            result.add(new Cliente(rs.getInt("idCliente"), rs.getString("nombre"), rs.getString("email")));
        }
        return result;
    }
}
