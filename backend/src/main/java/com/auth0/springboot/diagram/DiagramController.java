package com.auth0.springboot.diagram;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("diagrams")
public class DiagramController {
	private DiagramRepository diagramRepository;

	public DiagramController(DiagramRepository diagramRepository) {
		this.diagramRepository = diagramRepository;
	}

	@GetMapping
	public Flux<Diagram> getDiagrams() {
		return diagramRepository.findAll();
	}

	@GetMapping("/{id}")
	public Mono<Diagram> getDiagram(@PathVariable("id") String id) {
		return diagramRepository.findById(id);
	}

	@PostMapping
	public Mono<Void> saveDiagram(@RequestBody Diagram diagram) {
		System.out.println(diagram);
		return diagramRepository
				.save(diagram)
				.then();
	}
}
