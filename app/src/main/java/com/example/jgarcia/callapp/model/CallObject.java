package com.example.jgarcia.callapp.model;

public class CallObject {

    private String id;

    private String numero;

    private String nombre;

    public CallObject(){

    }

    public CallObject(String id, String numero, String nombre){
        this.id = id;
        this.nombre = nombre;
        this.numero = numero;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
