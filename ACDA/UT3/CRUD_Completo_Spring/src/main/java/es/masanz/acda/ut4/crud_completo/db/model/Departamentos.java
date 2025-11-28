package es.masanz.acda.ut4.crud_completo.db.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Departamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codigoID;

    private String nombre;

    private String localizacion;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL)
    private List<Empleados> empleados;

    public Departamentos() {}

    public Departamentos(String nombre, String edad) {
        this.nombre = nombre;
        this.localizacion = edad;
    }

    public Integer getCodigoID() {
        return codigoID;
    }

    public void setCodigoID(Integer codigoID) {
        this.codigoID = codigoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }
}
