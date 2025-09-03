package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.CreatePersonaDTO;
import co.com.bancolombia.api.dto.EditPersonaDTO;
import co.com.bancolombia.api.dto.PersonaDTO;
import co.com.bancolombia.api.mapper.PersonaDTOMapper;
import co.com.bancolombia.api.util.RequestValidator;
import co.com.bancolombia.api.util.exception.ResourceNotFoundException;
import co.com.bancolombia.usecase.persona.PersonaUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class Handler {

    private final PersonaUseCase personaUseCase;

    private final PersonaDTOMapper personaDTOMapper;

    private final RequestValidator requestValidator;

    private final TransactionalOperator transactionalOperator;

    public Mono<ServerResponse> listenGetPersonaById(ServerRequest serverRequest) {

        int id = Integer.parseInt(serverRequest.pathVariable("id"));

        return personaUseCase.getById(id)
                .flatMap(persona -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(personaDTOMapper.toResponse(persona))
                )
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No se ha encontrado una persona con el id: " + id)));

    }

    public Mono<ServerResponse> listenGetPersonaByIdentificacion(ServerRequest serverRequest) {

        String identificacion = serverRequest.pathVariable("identificacion");

        return personaUseCase.getByIdentificacion(identificacion)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No se encontraron personas con la identificación: " + identificacion)))
                .flatMap(persona -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(personaDTOMapper.toResponse(persona))
                );
    }

    public Mono<ServerResponse> listenGetAllPersonas(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(personaUseCase.getAll()
                        .map(persona -> personaDTOMapper.toResponse(persona)),
                        PersonaDTO.class
                );
    }

    public Mono<ServerResponse> listenSavePersona(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreatePersonaDTO.class)
                .doOnSubscribe(subscription -> log.info("******Inicia llamado a crear persona"))
                .flatMap(createPersonaDTO ->
                        requestValidator.validadorPersona(createPersonaDTO)
                                .flatMap(validatedDTO -> personaUseCase.create(
                                        personaDTOMapper.toModel(validatedDTO))
                                        .transform(transactionalOperator::transactional)
                                )
                                .flatMap(savedPersona -> ServerResponse.ok()
                                            .contentType(APPLICATION_JSON)
                                            .bodyValue(personaDTOMapper.toResponse(savedPersona))
                                )
                )
                .doOnTerminate(() -> log.info("*****Finalizó el proceso de creación de la persona."));
    }

    public Mono<ServerResponse> listenUpdatePersona(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(EditPersonaDTO.class)
                .doOnSubscribe(subscription -> log.info("******Inicia llamado a actualizar persona"))
                .flatMap(createPersonaDTO ->
                        requestValidator.validadorPersona(createPersonaDTO)
                                .flatMap(editPersonaDTO -> personaUseCase.update(
                                        personaDTOMapper.toModel(editPersonaDTO))
                                        .transform(transactionalOperator::transactional)
                                )
                                .flatMap(savedPersona -> ServerResponse.ok()
                                            .contentType(APPLICATION_JSON)
                                            .bodyValue(personaDTOMapper.toResponse(savedPersona))
                                )
                )
                .doOnTerminate(() -> log.info("*****Finalizó el proceso de actualización de la persona."));
    }

}
