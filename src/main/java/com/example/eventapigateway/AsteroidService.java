package com.example.eventapigateway;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AsteroidService {

    private final AddressUtils addressUtils;
    private final WebClient webClient;

    public AsteroidService(AddressUtils addressUtils, WebClient webClient) {
        this.addressUtils = addressUtils;
        this.webClient = webClient;
    }

    public Mono<AsteroidInfo> findAllAsteroids() {
        return webClient
                .get()
                .uri(addressUtils.getInstances("nasa-service") + "/api/meteors")
                .retrieve()
                .bodyToMono(AsteroidInfo.class);
    }

    public record AsteroidInfo(List<Asteroid> asteroids) { }

    public record Asteroid(String id, String name) { }
}
