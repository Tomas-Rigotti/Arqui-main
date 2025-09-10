package org.example.dao;

import org.example.entities.Producto;

import java.sql.SQLException;
import java.util.List;

public interface ProductoDao {
    public int insertProducto(int idProducto, String nombre, int valor) throws SQLException;
    public void updateProducto(Producto idProducto) throws SQLException;
    public void deleteProducto(int idProducto) throws SQLException;
    public Producto getProducto(int idProducto) throws SQLException;
    public List<Producto> getProductos() throws SQLException;
    public Producto getProductoMasVendido() throws SQLException;
}
