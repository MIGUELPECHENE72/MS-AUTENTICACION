package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.CreatePersonaDTO;
import co.com.bancolombia.api.dto.EditPersonaDTO;
import co.com.bancolombia.api.dto.PersonaDTO;
import co.com.bancolombia.api.mapper.PersonaDTOMapper;
import co.com.bancolombia.model.persona.Persona;
import co.com.bancolombia.usecase.persona.PersonaUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

    private final PersonaUseCase personaUseCase;

    private final PersonaDTOMapper personaDTOMapper;

    public Mono<ServerResponse> listenGetPersonaById(ServerRequest serverRequest) {

        int id = Integer.parseInt(serverRequest.pathVariable("id"));

        // Llama a getById() y usa flatMap para trabajar de manera reactiva
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
        return serverRequest.bodyToMono(CreatePersonaDTO.class)  // Recibes el DTO desde la solicitud
                .flatMap(createPersonaDTO -> {
                    // Mapeas CreatePersonaDTO a Persona
                    Persona persona = personaDTOMapper.toModel(createPersonaDTO);
                    // Guardas la persona en el repositorio
                    return personaUseCase.save(persona);
                })
                .flatMap(savedPersona -> {
                    // Aquí conviertes la Persona guardada a PersonaDTO
                    PersonaDTO personaDTO = personaDTOMapper.toResponse(savedPersona);
                    // Devuelves el PersonaDTO como respuesta
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(personaDTO);
                });
    }

    public Mono<ServerResponse> listenUpdatePersona(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(EditPersonaDTO.class)  // Recibes el DTO desde la solicitud
                .flatMap(editPersonaDTO -> {
                    // Mapeas CreatePersonaDTO a Persona
                    Persona persona = personaDTOMapper.toModel(editPersonaDTO);
                    // Guardas la persona en el repositorio
                    return personaUseCase.save(persona);
                })
                .flatMap(savedPersona -> {
                    // Aquí conviertes la Persona guardada a PersonaDTO
                    PersonaDTO personaDTO = personaDTOMapper.toResponse(savedPersona);
                    // Devuelves el PersonaDTO como respuesta
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(personaDTO);
                });
    }
}
