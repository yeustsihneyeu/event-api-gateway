package com.example.eventapigateway;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ActionViewService {

    private final AddressUtils addressUtils;
    private final WebClient webClient;

    public ActionViewService(AddressUtils addressUtils, WebClient webClient) {
        this.addressUtils = addressUtils;
        this.webClient = webClient;
    }

    public Mono<ActionView> findActiveViewById(String id) {
        return webClient
                .get()
                .uri(addressUtils.getInstances("query-service") + "/action-views/" + id)
                .retrieve()
                .bodyToMono(ActionView.class);
    }

    public record ActionView(String actionId, String name, int cost) { }
}
