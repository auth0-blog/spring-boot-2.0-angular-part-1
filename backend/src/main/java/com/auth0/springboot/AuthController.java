package com.auth0.springboot;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {
	private static final String SOCKET_ID_KEY = "socketId";
	private static final String CHANNEL_ID_KEY = "channel";

	private PusherService pusherService;

	public AuthController(PusherService pusherService) {
		this.pusherService = pusherService;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping
	public Mono<Auth> auth(@RequestBody Map<String, String> body) {
		return Mono.just(new Auth(
				pusherService.auth(body.get(SOCKET_ID_KEY), body.get(CHANNEL_ID_KEY))
		));
	}
}

@Data
@AllArgsConstructor
class Auth {
	private String auth;
}
