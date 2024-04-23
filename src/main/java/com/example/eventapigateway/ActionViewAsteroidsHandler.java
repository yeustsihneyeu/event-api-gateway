package com.example.eventapigateway;

import com.example.eventapigateway.ActionViewService.ActionView;
import com.example.eventapigateway.AsteroidService.AsteroidInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Optional;


@Component
public class ActionViewAsteroidsHandler {

    private final AsteroidService asteroidService;
    private final ActionViewService actionViewService;

    public ActionViewAsteroidsHandler(AsteroidService asteroidService, ActionViewService actionViewService) {
        this.asteroidService = asteroidService;
        this.actionViewService = actionViewService;
    }

    public Mono<ServerResponse> getActionViewAsteroids(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        final var asteroidInfoMono = asteroidService.findAllAsteroids()
                .map(Optional::of)
                .onErrorReturn(Optional.empty());

        final var actionViewMono = actionViewService.findActiveViewById(id)
                .map(Optional::of)
                .onErrorReturn(Optional.empty());

        final var combined = Mono.zip(asteroidInfoMono, actionViewMono);
        final var activeViewAsteroidsMono = combined.map(this::makeActiveViewAsteroids);

        return activeViewAsteroidsMono
                .flatMap(it -> ServerResponse.ok().body(BodyInserters.fromValue(it)));
    }

    private ActiveViewAsteroids makeActiveViewAsteroids(
            Tuple2<Optional<AsteroidInfo>, Optional<ActionView>> tuple2
    ) {
        return new ActiveViewAsteroids(tuple2.getT2().orElse(null), tuple2.getT1().orElse(null));
    }

    public record ActiveViewAsteroids(ActionView actionView, AsteroidInfo asteroidInfo) { }

}
