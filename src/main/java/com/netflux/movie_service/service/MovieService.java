package com.netflux.movie_service.service;

import com.netflux.movie_service.entity.Genre;
import com.netflux.movie_service.entity.Movie;
import com.netflux.movie_service.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    public List<Movie> getMoviesByGenre(Genre genre) {
        return movieRepository.findByGenre(genre);
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}
