-- Utilisateurs (mots de passe : Admin123! et User1234! hashes en BCrypt)
INSERT INTO users (first_name, last_name, email, password, role) VALUES
    ('Admin', 'Ekod', 'admin@cineclub.fr', '$2b$10$RJGjbgE1dL.oSa7RmAqGSuh8.W/bUmMs5QpWUkV0KksDo48IITmNi', 'ADMIN'),
    ('Lenny', 'Louis', 'lenny@cineclub.fr', '$2b$10$x/FUXuwIJxLCNIRxWrs1tOi1lCTx2nWvzFks2eMYM2VOzG8wy0DHa', 'USER');

-- Realisateurs
INSERT INTO directors (first_name, last_name, nationality, birth_date) VALUES
    ('Christopher', 'Nolan', 'British', '1970-07-30'),
    ('Hayao', 'Miyazaki', 'Japanese', '1941-01-05'),
    ('Quentin', 'Tarantino', 'American', '1963-03-27');

-- Films
INSERT INTO movies (title, release_year, duration_minutes, genre, synopsis, director_id) VALUES
    ('Inception', 2010, 148, 'Sci-Fi', 'Un voleur qui s''infiltre dans les reves doit accomplir une mission impossible : l''inception.', 1),
    ('Interstellar', 2014, 169, 'Sci-Fi', 'Un groupe d''explorateurs voyage a travers un trou de ver pour sauver l''humanite.', 1),
    ('Le Voyage de Chihiro', 2001, 125, 'Animation', 'Une jeune fille se retrouve piegee dans un monde d''esprits apres que ses parents ont ete transformes en cochons.', 2),
    ('Princesse Mononoke', 1997, 134, 'Animation', 'Un jeune guerrier Emishi se retrouve au coeur d''un conflit entre les dieux de la foret et les humains.', 2),
    ('Pulp Fiction', 1994, 154, 'Crime', 'Les vies de deux tueurs a gages, d''un boxeur et d''un couple de braqueurs s''entrecroisent a Los Angeles.', 3);
