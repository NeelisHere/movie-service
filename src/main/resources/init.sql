CREATE TABLE IF NOT EXISTS movies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    release_year INTEGER,
    genre VARCHAR(100)
);