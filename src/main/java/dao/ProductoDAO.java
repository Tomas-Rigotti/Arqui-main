package dao;

import dto.ProductoDTO;
import entity.Producto;

public interface ProductoDAO {

    Producto find(Integer pk);

    ProductoDTO findProductoDTO(Integer pk);

    ProductoDTO getProductoMasRecaudacion();
}