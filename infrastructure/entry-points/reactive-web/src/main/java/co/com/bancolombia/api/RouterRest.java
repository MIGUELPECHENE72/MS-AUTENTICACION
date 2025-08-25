package co.com.bancolombia.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/v1/usuarios/{id}"), handler::listenGetPersonaById)
                .andRoute(POST("/api/v1/usuarios"), handler::listenSavePersona)
                .andRoute(PUT("/api/v1/usuarios"), handler::listenUpdatePersona)
                .and(route(GET("/api/v1/usuarios"), handler::listenGetAllPersonas));
    }
}
