package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.CreatePersonaDTO;
import co.com.bancolombia.api.dto.EditPersonaDTO;
import co.com.bancolombia.api.dto.PersonaDTO;
import co.com.bancolombia.api.mapper.PersonaDTOMapper;
import co.com.bancolombia.api.util.RequestValidator;
import co.com.bancolombia.model.persona.Persona;
import co.com.bancolombia.usecase.persona.PersonaUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class Handler {

    private final PersonaUseCase personaUseCase;

    private final PersonaDTOMapper personaDTOMapper;

    private final RequestValidator requestValidator;

    public Mono<ServerResponse> listenGetPersonaById(ServerRequest serverRequest) {

        int id = Integer.parseInt(serverRequest.pathVariable("id"));

        return personaUseCase.getById(id)
                .flatMap(persona -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(personaDTOMapper.toResponse(persona))
                )
                .switchIfEmpty(ServerResponse.notFound().build()); // Si no se encuentra la persona, devolver un 404

    }

    public Mono<ServerResponse> listenGetAllPersonas(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(personaUseCase.getAll(),PersonaDTO.class);
    }

    public Mono<ServerResponse> listenSavePersona(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreatePersonaDTO.class)
                .doOnSubscribe(subscription -> log.info("******Inicia llamado a crear persona"))
                .flatMap(createPersonaDTO ->
                        requestValidator.validadorPersona(createPersonaDTO)
                                .flatMap(validatedDTO -> {
                                    Persona persona = personaDTOMapper.toModel(validatedDTO);
                                    return personaUseCase.create(persona);
                                })
                                .flatMap(savedPersona -> {
                                    PersonaDTO personaDTO = personaDTOMapper.toResponse(savedPersona);
                                    return ServerResponse.ok()
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(personaDTO);
                                })
                )
                .onErrorResume(e -> {
                    log.error("*****Ha ocurrido un error de validación: {}", e.getMessage(), e);
                    return ServerResponse.badRequest()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("Error de validación: " + e.getMessage());
                })
                .doOnTerminate(() -> log.info("*****Finalizó el proceso de creación de la persona."));
    }

    public Mono<ServerResponse> listenUpdatePersona(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(EditPersonaDTO.class)
                .doOnSubscribe(subscription -> log.info("******Inicia llamado a actualizar persona"))
                .flatMap(createPersonaDTO ->
                        requestValidator.validadorPersona(createPersonaDTO)
                                .flatMap(editPersonaDTO -> {
                                    Persona persona = personaDTOMapper.toModel(editPersonaDTO);
                                    return personaUseCase.update(persona);
                                })
                                .flatMap(savedPersona -> {
                                    PersonaDTO personaDTO = personaDTOMapper.toResponse(savedPersona);
                                    return ServerResponse.ok()
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(personaDTO);
                                })
                )
                .onErrorResume(e -> {
                    log.error("*****Ha ocurrido un error de validación: {}", e.getMessage(), e);
                    return ServerResponse.badRequest()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("Error de validación: " + e.getMessage());
                })
                .doOnTerminate(() -> log.info("*****Finalizó el proceso de actualización de la persona."));
    }
}
