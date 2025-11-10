package com.netflux.movie_service.controller;

import com.netflux.movie_service.entity.Genre;
import com.netflux.movie_service.entity.Movie;
import com.netflux.movie_service.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/movies")
@Tag(name = "Movies", description = "Movie management APIs")
public class MovieController {
    private final MovieService movieService;

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping(value = "/{genre}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all movies by genre")
    public ResponseEntity<List<Movie>> getMoviesByGenre(@PathVariable(value = "genre") Genre genre) {
        return ResponseEntity.ok(movieService.getMoviesByGenre(genre));
    }

    @PostMapping(value = {"", "/"},
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Add a new movie")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.addMovie(movie));
    }
}
