package dto;

public class ClienteDTO {
    private Integer idCliente;
    private String nombre;
    private String email;
    private Double totalFacturado;  
    
    public Integer getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Double getTotalFacturado() {
        return totalFacturado;
    }
    public void setTotalFacturado(Double totalFacturado) {
        this.totalFacturado = totalFacturado;
    }
    
}
