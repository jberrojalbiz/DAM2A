package com.example.ejercicioBiblioteca.dto;

public class AutorTopDTO {
    private String autor;
    private int totalLibros;

    public AutorTopDTO() {}

    public AutorTopDTO(String autor, int totalLibros) {
        this.autor = autor;
        this.totalLibros = totalLibros;
    }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public int getTotalLibros() { return totalLibros; }
    public void setTotalLibros(int totalLibros) { this.totalLibros = totalLibros; }
}
