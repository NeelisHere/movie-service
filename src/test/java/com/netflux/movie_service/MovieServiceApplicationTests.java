package com.netflux.movie_service;

import com.netflux.movie_service.entity.Genre;
import com.netflux.movie_service.entity.Movie;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import io.restassured.RestAssured;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@Import(TestcontainersConfiguration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieServiceApplicationTests {
    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAllSetup() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
    }

    @BeforeEach
    void beforeEachSetup() {
        RestAssured.port = port;
    }

	@Test
    @Order(1)
	void healthCheck() {
		String url = "/actuator/health";
        RestAssured.given()
                .when()
                .get(url)
                .then()
                .statusCode(HttpStatus.OK.value());
	}

	@Test
    @Order(2)
	void testAllMovies() {
		String url = "/api/v1/movies";
        List<Movie> movies = RestAssured.given()
                .when()
                .get(url)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(new TypeRef<>() {});
        log.info("movies: {}", movies);
		Assertions.assertThat(movies)
				.as("Response body should not be null")
				.isNotNull();
		Assertions.assertThat(movies.size())
				.as("Response body length should be 6 initially")
				.isEqualTo(6);
	}

	@Test
    @Order(3)
	void testMoviesByGenre() {
		String url = "/api/v1/movies/ACTION";
        List<Movie> movies = RestAssured.given()
                .when()
                .get(url)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(new TypeRef<>() {});

		Assertions.assertThat(movies).isNotNull();
		Assertions.assertThat(movies.size()).isEqualTo(3);
		Assertions.assertThat(movies)
				.extracting(Movie::getGenre)
				.containsOnly(Genre.ACTION);
	}

    @Test
    @Order(4)
	void testAddMovie() {
		String url = "/api/v1/movies";
        Movie requestBody = Movie.builder()
                .genre(Genre.COMEDY)
                .title("Cool Movie")
                .releaseYear(1900)
                .build();
        Movie movie = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(url)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(Movie.class);
        Assertions.assertThat(movie.getId()).isNotNull();
	}

}
