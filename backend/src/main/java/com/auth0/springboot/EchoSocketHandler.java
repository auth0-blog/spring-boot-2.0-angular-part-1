package com.auth0.springboot;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

public class EchoSocketHandler implements WebSocketHandler {

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		return session.send(
				Flux.<String>generate(sink -> sink.next(String.format("{ message: 'got local message', date: '%s' }", new Date())))
						.delayElements(Duration.ofSeconds(3)).map(session::textMessage))
				.and(session.receive()
						.doOnNext(System.out::println));
	}
}
