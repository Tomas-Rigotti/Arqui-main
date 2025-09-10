package org.example.entities;

public class Cliente {

    private Integer idCliente;
    private String nombre;
    private String email;

    public Cliente (Integer idCliente, String nombre, String email) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.email = email;
    }

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

    public void imprimir(){
        System.out.println("Id: " + idCliente + " , Nombre: " + nombre + " , Email: " + email);
    }
}
