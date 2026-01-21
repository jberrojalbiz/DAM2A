-- =========================
-- PELICULAS
-- =========================
INSERT IGNORE INTO peliculas (titulo, director, duracion, valoracion, fecha_estreno, genero) VALUES
('Interstellar', 'Christopher Nolan', 169, 8.6, '2014-11-07', 'Sci-Fi'),
('The Godfather', 'Francis Ford Coppola', 175, 9.2, '1972-03-24', 'Crime'),
('Spirited Away', 'Hayao Miyazaki', 125, 8.6, '2001-07-20', 'Animation'),
('Parasite', 'Bong Joon-ho', 132, 8.5, '2019-05-30', 'Thriller'),
('The Dark Knight', 'Christopher Nolan', 152, 9.0, '2008-07-18', 'Action');

-- =========================
-- REVIEWS
-- (asume ids 1..5 tras tabla limpia)
-- =========================
INSERT IGNORE INTO reviews (autor, puntuacion, comentario, fecha, pelicula_id) VALUES
('Laura', 9.0, 'Película impresionante a nivel visual y emocional.', '2025-01-20 10:00:00', 1),
('Carlos', 8.5, 'Muy buena, aunque un poco larga.', '2025-01-20 11:30:00', 1),

('Ana', 9.5, 'Una obra maestra del cine.', '2025-01-21 09:00:00', 2),
('Pedro', 9.0, 'Guion y actuaciones increíbles.', '2025-01-21 09:45:00', 2),

('Marta', 8.0, 'Intensa y muy bien dirigida.', '2025-01-22 12:15:00', 3),

('Javi', 8.7, 'Visualmente espectacular.', '2025-01-23 18:00:00', 4),

('Lucía', 9.2, 'Oscura y brillante a partes iguales.', '2025-01-24 20:30:00', 5);
