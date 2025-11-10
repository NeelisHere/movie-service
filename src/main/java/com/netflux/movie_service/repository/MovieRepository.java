package com.netflux.movie_service.repository;

import com.netflux.movie_service.entity.Genre;
import com.netflux.movie_service.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByGenre(Genre genre);
}
