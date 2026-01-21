CREATE TABLE IF NOT EXISTS productos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(120) NOT NULL,
    stock INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    fecha_alta DATE NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    autor VARCHAR(80) NOT NULL,
    puntuacion DECIMAL(3,1) NOT NULL,
    comentario VARCHAR(400) NOT NULL,
    fecha DATETIME NOT NULL,
    pelicula_id BIGINT NOT NULL,
    CONSTRAINT fk_reviews_pelicula
    FOREIGN KEY (pelicula_id)
    REFERENCES peliculas(id)
    ON DELETE CASCADE
);
