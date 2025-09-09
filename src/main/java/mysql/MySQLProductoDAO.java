package mysql;

import dao.ProductoDAO;
import dto.ProductoDTO;
import entity.Producto;

import java.sql.*;

public class MySQLProductoDAO implements ProductoDAO {
    private final Connection conn;

    public MySQLProductoDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Producto find(Integer pk) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM productos WHERE idProducto = ?");
            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setValor(rs.getFloat("valor"));
                return producto;
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
    public ProductoDTO findProductoDTO(Integer pk) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM productos WHERE idProducto = ?");
            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ProductoDTO producto = new ProductoDTO();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setValor(rs.getFloat("valor"));
                return producto;
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
    public ProductoDTO getProductoMasRecaudacion() {
        PreparedStatement ps = null;
        try{
        String query = "SELECT p.nombre, SUM(fp.cantidad * p.valor) AS recaudacion "
                + "FROM productos p "
                + "JOIN facturas_productos fp ON p.id_producto = fp.id_producto "
                + "GROUP BY p.nombre "
                + "ORDER BY recaudacion DESC "
                + "LIMIT 1";

            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query); {
                if (rs.next()) {
                    ProductoDTO producto = new ProductoDTO();
                    producto.setNombre(rs.getString("nombre"));
                    producto.setValor(rs.getFloat("valor"));
                    producto.setTotalRecaudado(rs.getDouble("recaudacion"));
                    return producto;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}