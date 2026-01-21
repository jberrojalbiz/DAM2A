package com.example.psep_ut4_api_server.server.pelicula;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.example.psep_ut4_api_server.server.review.Review;
import java.util.List;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150, unique = true)
    private String titulo;        // texto

    @Column(nullable = false, length = 100)
    private String director;        // texto

    @Column(nullable = false)
    private Integer duracion;       // entero

    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal valoracion;  // decimal (ej 8.5)

    @Column(nullable = false)
    private LocalDate fechaEstreno; // fecha

    @Column(nullable = false, length = 50)
    private String genero;          // texto

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    public Pelicula() {}

    public Pelicula(Long id, String titulo, String director, Integer duracion,
                    BigDecimal valoracion, LocalDate fechaEstreno, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.duracion = duracion;
        this.valoracion = valoracion;
        this.fechaEstreno = fechaEstreno;
        this.genero = genero;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDirector() { return director; }
    public Integer getDuracion() { return duracion; }
    public BigDecimal getValoracion() { return valoracion; }
    public LocalDate getFechaEstreno() { return fechaEstreno; }
    public String getGenero() { return genero; }

    public void setId(Long id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDirector(String director) { this.director = director; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }
    public void setValoracion(BigDecimal valoracion) { this.valoracion = valoracion; }
    public void setFechaEstreno(LocalDate fechaEstreno) { this.fechaEstreno = fechaEstreno; }
    public void setGenero(String genero) { this.genero = genero; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
}
