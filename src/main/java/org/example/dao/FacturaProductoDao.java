package org.example.dao;

import org.example.entities.FacturaProducto;

import java.sql.SQLException;
import java.util.List;

public interface FacturaProductoDao {

    public int insertFacturaProducto(int idFactura, int idProducto, int cantidad) throws SQLException;
    public void updateFacturaProducto(FacturaProducto facturaProducto) throws SQLException;
    public void deleteFacturaProducto(int idFactura, int idProducto) throws SQLException;
    public FacturaProducto getFacturaProducto(int idFactura, int idProducto) throws SQLException;
    public List<FacturaProducto> getFacturasProductos() throws SQLException;

}
