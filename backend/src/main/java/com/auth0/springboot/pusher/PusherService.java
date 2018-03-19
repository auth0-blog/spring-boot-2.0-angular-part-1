package com.auth0.springboot.pusher;

import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PusherService {
	private static final String CHANNEL = "private-collaborative-diagrams";
	private static final String UPDATE_DIAGRAMS_LIST = "update-diagrams-list";

	private Pusher pusher;

	public PusherService(
			@Value("${pusher.app.id}") String appId,
			@Value("${pusher.app.key}") String appKey,
			@Value("${pusher.app.secret}") String appSecret) {

		pusher = new Pusher(appId, appKey, appSecret);
		pusher.setCluster("us2");
		pusher.setEncrypted(true);
	}

	public String auth(String socketId, String channel) {
		return pusher.authenticate(socketId, channel);
	}
}
