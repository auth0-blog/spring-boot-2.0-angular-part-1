package com.auth0.springboot;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DiagramRepository extends ReactiveCrudRepository<Diagram, String> {
}
