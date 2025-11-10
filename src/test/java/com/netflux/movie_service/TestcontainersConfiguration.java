package com.netflux.movie_service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	@SuppressWarnings("resource")
	PostgreSQLContainer<?> postgresContainer() {
		DockerImageName postgres_docker_image = DockerImageName.parse("postgres:latest");
		return new PostgreSQLContainer<>(postgres_docker_image)
				.withDatabaseName("movies")
				.withInitScript("init.sql");
	}

}
