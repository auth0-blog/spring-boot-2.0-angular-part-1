package com.auth0.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getStreaming() throws URISyntaxException {
		WebSocketClient client = new ReactorNettyWebSocketClient();

		EmitterProcessor<String> output = EmitterProcessor.create();

		Mono<Void> sessionMono = client.execute(
				new URI("ws://localhost:8080/echo"),
				session -> session.receive().map(WebSocketMessage::getPayloadAsText).subscribeWith(output).then()
		);

		return output.doOnSubscribe(s -> sessionMono.subscribe());
	}

	@GetMapping(path = "/remote", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getRemoteStreaming() throws URISyntaxException {
		Flux<String> input = Flux.<String>generate(sink -> sink.next(String.format("{got remote message: 'message', date: '%s' }", new Date())))
				.delayElements(Duration.ofSeconds(5));

		WebSocketClient client = new ReactorNettyWebSocketClient();
		EmitterProcessor<String> output = EmitterProcessor.create();

		Mono<Void> sessionMono = client.execute(URI.create("ws://echo.websocket.org"), session -> session.send(input.map(session::textMessage))
				.thenMany(session.receive().map(WebSocketMessage::getPayloadAsText).subscribeWith(output).then()).then());

		return output.doOnSubscribe(s -> sessionMono.subscribe());
	}

	@Bean
	public HandlerMapping webSocketMapping() {
		Map<String, WebSocketHandler> map = new HashMap<>();

		map.put("/echo", session -> session.send(
				Flux.<String>generate(sink -> sink.next(String.format("{ message: 'got local message', date: '%s' }", new Date())))
						.delayElements(Duration.ofSeconds(3)).map(session::textMessage))
		);

		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		mapping.setUrlMap(map);
		mapping.setOrder(1);
		return mapping;
	}

	@Bean
	public WebSocketHandlerAdapter handlerAdapter() {
		return new WebSocketHandlerAdapter();
	}
}
