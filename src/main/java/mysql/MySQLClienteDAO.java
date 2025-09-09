package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ClienteDAO;
import dto.ClienteDTO;
import entity.Cliente;

public class MySQLClienteDAO implements ClienteDAO {
    private final Connection conn;

    public MySQLClienteDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Cliente find(Integer pk) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM clientes WHERE idCliente = ?");
            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setEmail(rs.getString("email"));
                return cliente;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ClienteDTO findClienteDTO(Integer pk) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM clientes WHERE idCliente = ?");
            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ClienteDTO cliente = new ClienteDTO();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setEmail(rs.getString("email"));
                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<ClienteDTO> getAllClienteDTOorderByFacturacion() {
        PreparedStatement ps = null;
        List<ClienteDTO> listaClientesDTO = new ArrayList<>();
        try {
            String query = "SELECT c.nombre, SUM(fp.cantidad * p.valor) AS total_facturado "
                    + "FROM clientes c "
                    + "JOIN facturas f ON c.idCliente = f.idCliente "
                    + "JOIN facturas_productos fp ON f.idFactura = fp.idFactura "
                    + "JOIN productos p ON fp.idProducto = p.idProducto "
                    + "GROUP BY c.nombre "
                    + "ORDER BY total_facturado DESC";

            ps = conn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ClienteDTO cliente = new ClienteDTO();
                cliente.setNombre(rs.getString("nombre"));
                cliente.setTotalFacturado(rs.getDouble("total_facturado"));
                listaClientesDTO.add(cliente);
            }

            return listaClientesDTO;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaClientesDTO;

    }
}
