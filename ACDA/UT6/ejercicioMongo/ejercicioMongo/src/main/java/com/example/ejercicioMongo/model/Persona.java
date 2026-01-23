package com.example.ejercicioMongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "personas")
public class Persona {

    @Id
    private String id;

    private String nombre;
    private Integer edad;

    private List<App> apps = new ArrayList<>();

    public Persona() {}

    public Persona(String nombre, Integer edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.apps = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public List<App> getApps() { return apps; }
    public void setApps(List<App> apps) {
        this.apps = (apps != null) ? apps : new ArrayList<>();
    }
}
