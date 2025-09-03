package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.persona.Persona;
import co.com.bancolombia.r2dbc.entity.PersonaEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveRepository extends ReactiveCrudRepository<PersonaEntity, Integer>, ReactiveQueryByExampleExecutor<PersonaEntity> {

    Mono<Boolean> existsByCorreoElectronico(String correoElectronico);

    Mono<Persona> findByIdentificacion(String identificacion);

}
