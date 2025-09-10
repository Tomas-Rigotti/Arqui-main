package org.example.dao;

import org.example.entities.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface ClienteDao {
    public int insertCliente(int idCliente, String nombre, String email) throws SQLException;
    public void updateCliente(Cliente cliente) throws SQLException;
    public void deleteCliente(int idCliente) throws SQLException;
    public Cliente getCliente(int idCliente) throws SQLException;
    public List<Cliente> getClientes() throws SQLException;
    public List<Cliente> getMasFacturadosOrdenados() throws SQLException;
}
