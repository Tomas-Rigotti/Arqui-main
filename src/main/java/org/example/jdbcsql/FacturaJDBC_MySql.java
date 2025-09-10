package org.example.jdbcsql;

import org.example.dao.FacturaDao;
import org.example.entities.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacturaJDBC_MySql implements FacturaDao {
    private Connection con;

    public FacturaJDBC_MySql(Connection con) {
        this.con = con;
    }



    @Override
    public int insertFactura(int idFactura, int idCliente) throws SQLException {
        String query = "INSERT INTO Factura (idFactura, idCliente) VALUES ( ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        try{
            ps.setInt(1, idFactura);
            ps.setInt(2, idCliente);
            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ps.close();
            con.commit();
        }
        return 0;
    }

    @Override
    public void updateFactura(Factura Factura) throws SQLException {
// Consulta para verificar si el producto factura existe
        String select = "SELECT * FROM Factura WHERE idFactura = ?";
        PreparedStatement psSelect = null;
        PreparedStatement psUpdate = null;
        ResultSet rs = null;

        try {
            //Preparando el SELECT para verificar la existencia de la factura
            psSelect = con.prepareStatement(select);
            psSelect.setInt(1, Factura.getIdFactura());
            rs = psSelect.executeQuery();

            // Si la factura existe, actualizamos
            if (rs.next()) {
                // Consulta para actualizar la factura
                String update = "UPDATE Factura SET idCliente = ? WHERE idFactura = ?";

                // Preparando la actualización
                psUpdate = con.prepareStatement(update);
                psUpdate.setInt(1, Factura.getIdCliente());
                psUpdate.setInt(2, Factura.getIdFactura());


                // Ejecutando la actualización
                int rowsAffected = psUpdate.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Factura actualizado con éxito.");
                } else {
                    System.out.println("No se pudo actualizar la factura.");
                }
            } else {
                System.out.println("factura no encontrada.");
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
            con.commit();
        }
    }

    @Override
    public void deleteFactura(int idFactura) throws SQLException {
        // Consulta para verificar si la factura existe
        String select = "SELECT * FROM Factura WHERE idFactura = ?";
        // Consulta para eliminar la factura
        String delete = "DELETE FROM Factura WHERE idFactura = ?";

        PreparedStatement psSelect = null;
        PreparedStatement psDelete = null;
        ResultSet rs = null;

        try {
            // Preparar el SELECT para verificar la existencia de la factura
            psSelect = con.prepareStatement(select);
            psSelect.setInt(1, idFactura);
            rs = psSelect.executeQuery();

            // Si la factura existe, se elimina
            if (rs.next()) {
                // Preparar la eliminación
                psDelete = con.prepareStatement(delete);
                psDelete.setInt(1, idFactura);

                // Ejecutar la eliminación
                int rowsAffected = psDelete.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Factura eliminada con éxito.");
                } else {
                    System.out.println("No se pudo eliminar la factura.");
                }
            } else {
                System.out.println("Factura no encontrada.");
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
            con.commit();
        }
    }

    @Override
    public Factura getFactura(int idFactura) throws SQLException {
        String select = "select * from Factura where idFactura = ?";
        PreparedStatement ps = con.prepareStatement(select);
        ps.setInt(1, idFactura);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Factura(rs.getInt("idFactura"), rs.getInt("idCliente"));
        } else {
            return null; // o lanzar una excepción si el producto no se encuentra
        }
    }

    @Override
    public List<Factura> getPFacturas() throws SQLException {
        ArrayList<Factura> result = new ArrayList<Factura>();
        String select = "SELECT * FROM Factura";
        PreparedStatement ps = this.con.prepareStatement(select);

        //ResultSet guardara el resultado al ejecutar el estado de la consulta
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            result.add(new Factura(rs.getInt("idFactura"), rs.getInt("idCliente")));
        }
        return result;
    }
}

