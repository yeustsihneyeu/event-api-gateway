package com.example.eventapigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ActionViewAsteroidsConfiguration {

    @Bean
    public RouterFunction<ServerResponse> actionViewAsteroidsHandlerRouting(ActionViewAsteroidsHandler actionViewAsteroidsHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/query/action-views/{id}"),
                actionViewAsteroidsHandler::getActionViewAsteroids);
    }
}
