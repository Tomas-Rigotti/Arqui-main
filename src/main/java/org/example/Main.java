package org.example;

import java.util.List;

import org.example.dao.ClienteDao;
import org.example.dao.FacturaDao;
import org.example.dao.FacturaProductoDao;
import org.example.dao.ProductoDao;
import org.example.entities.Cliente;
import org.example.entities.Factura;
import org.example.entities.FacturaProducto;
import org.example.entities.Producto;
import org.example.factory.FactoryDao;
import org.example.utils.HelperMySQL;

public class Main {
    public static void main(String[] args) throws Exception {

        HelperMySQL helper = new HelperMySQL();

        helper.dropTables();
        helper.createTables();
        helper.populateDB();

        FactoryDao mysql = FactoryDao.getDAOFactory(1);

        Producto producto = new Producto(111,"Pelota de futbol", 50);
        Cliente cliente = new Cliente(111,"juan","juan@juan");
        FacturaProducto facturaProducto = new FacturaProducto(500,100,300);
        Factura factura = new Factura(512,99);

        ProductoDao productoDao = mysql.getProductoDao();
        FacturaProductoDao facturaProductoDao = mysql.getFacturaProducto();
        ClienteDao clienteDao = mysql.getClienteDao();
        FacturaDao facturaDao = mysql.getFacturaDao();


        /*Pruebas Generales
        productoDao.insertProducto(111,"Auriculares LG Pro Max", 200);
        productoDao.updateProducto(producto);
        productoDao.deleteProducto(111);
        Producto p = productoDao.getProducto(1);
        p.imprimir();

        List<Producto> prueba = new ArrayList<Producto>();
        prueba = productoDao.getProductos();
        for (Producto producto2 : prueba) {
            producto2.imprimir();
        }

        //--------------------------------------------------------------------------

        facturaProductoDao.insertFacturaProducto(500,100,10);
        facturaProductoDao.updateFacturaProducto(facturaProducto);
        facturaProductoDao.deleteFacturaProducto(500,100);
        List<FacturaProducto> fp = facturaProductoDao.getFacturasProductos();


        //--------------------------------------------------------------------------


        clienteDao.insertCliente(111,"yo","yo@yo");
        clienteDao.updateCliente(cliente);
        clienteDao.deleteCliente(111);
        Cliente c1 = clienteDao.getCliente(111);
        c1.imprimir();
        List<Cliente> prueba2 = clienteDao.getClientes();
        for (Cliente c2 : prueba2) {
            c2.imprimir();
        }

        //--------------------------------------------------------------------------


        facturaDao.insertFactura(512,100);
        facturaDao.updateFactura(factura);
        facturaDao.deleteFactura(512);
        Factura f1 = facturaDao.getFactura(511);
        f1.imprimirFactura();
        List<Factura> f2 = facturaDao.getPFacturas();
        for (Factura f3 : f2) {
            f3.imprimirFactura();
        }*/

        Producto masVendido = productoDao.getProductoMasVendido();
        masVendido.imprimir();

        System.out.println("---------------------------------------------");

        /* Enuncuado 3 */

        /* Enunciado 4 */

        List<Cliente> masFacturados = clienteDao.getMasFacturadosOrdenados();
        for (Cliente c : masFacturados) {
            c.imprimir();
        }

        /* Enuncuado 4 */
    }

}
