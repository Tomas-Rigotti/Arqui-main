package dao;

import java.util.List;
import dto.ClienteDTO;
import entity.Cliente;

public interface ClienteDAO {

    Cliente find(Integer pk);

    ClienteDTO findClienteDTO(Integer pk);

    List<ClienteDTO> getAllClienteDTOorderByFacturacion();

}
