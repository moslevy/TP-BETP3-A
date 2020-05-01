package com.example.api_rest_call;

import androidx.annotation.NonNull;

public class Auto {
    private String id;
    private String marca;
    private String modelo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
