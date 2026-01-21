package com.example.psep_ut4_api_server.server.review;

import com.example.psep_ut4_api_server.server.pelicula.Pelicula;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=80)
    private String autor;

    @Column(nullable=false, precision=3, scale=1)
    private BigDecimal puntuacion;

    @Column(nullable=false, length=400)
    private String comentario;

    @Column(nullable=false)
    private LocalDateTime fecha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pelicula_id")
    private Pelicula pelicula;

    public Review() {}

    public Review(Long id, String autor, BigDecimal puntuacion, String comentario, LocalDateTime fecha, Pelicula pelicula) {
        this.id = id;
        this.autor = autor;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha = fecha;
        this.pelicula = pelicula;
    }

    public Long getId() { return id; }
    public String getAutor() { return autor; }
    public BigDecimal getPuntuacion() { return puntuacion; }
    public String getComentario() { return comentario; }
    public LocalDateTime getFecha() { return fecha; }
    public Pelicula getPelicula() { return pelicula; }

    public void setId(Long id) { this.id = id; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setPuntuacion(BigDecimal puntuacion) { this.puntuacion = puntuacion; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public void setPelicula(Pelicula pelicula) { this.pelicula = pelicula; }
}
