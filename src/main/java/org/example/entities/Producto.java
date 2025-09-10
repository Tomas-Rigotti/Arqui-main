package org.example.entities;

public class Producto {
    private int id;
    private String nombre;
    private float valor;

    public Producto(int id, String nombre, float valor) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }

    public Producto() {

    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public float getValor() {
        return valor;
    }

    public void imprimir(){
        System.out.println("Id: " + id + " , Nombre: " + nombre + " , Valor: " + valor);
    }

}
