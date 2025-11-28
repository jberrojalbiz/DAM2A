package es.masanz.acda.ut4.crud_completo.db.model;

import jakarta.persistence.*;

@Entity
public class Empleados {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idE;

    private String nombre;

    private Integer DNI_E;

    private Integer telefono;

    private Integer salario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigoID", nullable = false)
    private Departamentos departamento;

    public Empleados() {}

    public Empleados(String nombre, Integer DNI_E, Integer telefono, Integer salario) {
        this.nombre = nombre;
        this.DNI_E = DNI_E;
        this.telefono = telefono;
        this.salario = salario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDNI_E() {
        return DNI_E;
    }

    public void setDNI_E(Integer DNI_E) {
        this.DNI_E = DNI_E;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public Integer getSalario() {
        return salario;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }

    public Integer getIdE() {
        return idE;
    }

    public void setIdE(Integer idE) {
        this.idE = idE;
    }
}
