package com.example.api_rest_call;

import androidx.annotation.NonNull;

public class Auto {
    private String id;
    private String marca;
    private String modelo;

    public Auto (String id, String marca, String modelo){
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getId() {
        return id;
    }

    public String setId(String id) {
        this.id = id;
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String setMarca(String marca) {
        this.marca = marca;
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String setModelo(String modelo) {
        this.modelo = modelo;
        return modelo;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
