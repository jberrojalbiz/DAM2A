package com.example.ejercicioBiblioteca.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "bibliotecas")
public class Biblioteca {

    @Id
    private String id;

    private String nombre;
    private String ubicacion;

    @DBRef
    private List<Libro> libros = new ArrayList<>();

    public Biblioteca() {}

    public Biblioteca(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.libros = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) {
        this.libros = (libros != null) ? libros : new ArrayList<>();
    }
}
