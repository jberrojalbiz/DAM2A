package com.example.ejercicioBiblioteca.dto;

public class BibliotecaConteoDTO {
    private String bibliotecaId;
    private String nombre;
    private String ubicacion;
    private int totalLibros;

    public BibliotecaConteoDTO() {}

    public BibliotecaConteoDTO(String bibliotecaId, String nombre, String ubicacion, int totalLibros) {
        this.bibliotecaId = bibliotecaId;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.totalLibros = totalLibros;
    }

    public String getBibliotecaId() { return bibliotecaId; }
    public void setBibliotecaId(String bibliotecaId) { this.bibliotecaId = bibliotecaId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public int getTotalLibros() { return totalLibros; }
    public void setTotalLibros(int totalLibros) { this.totalLibros = totalLibros; }
}
