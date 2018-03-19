package com.auth0.springboot.diagram;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DiagramRepository extends ReactiveCrudRepository<Diagram, String> {
}
