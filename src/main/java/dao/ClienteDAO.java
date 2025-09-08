package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dto.ClienteDTO;
import entity.Cliente;

public interface ClienteDAO {

    public Cliente find(Integer pk);

    public ClienteDTO findClienteDTO(Integer pk);

}
