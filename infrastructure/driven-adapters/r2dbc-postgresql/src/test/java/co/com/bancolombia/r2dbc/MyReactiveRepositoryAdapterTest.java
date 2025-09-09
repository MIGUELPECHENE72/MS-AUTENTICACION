package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.persona.Persona;
import co.com.bancolombia.r2dbc.entity.PersonaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    MyReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    MyReactiveRepository repository;

    @Mock
    ObjectMapper mapper;


    @Test
    void mustFindValueById() {

        PersonaEntity personaEntity = new PersonaEntity(
                1,
                "MIGUEL ANGEL",
                "PECHENE PECHENE",
                LocalDate.of(2000, 5, 17),
                "PT MADERO",
                "3023011900",
                "miguelpechene72@gmail.com",
                new BigDecimal("7000000"),
                1,
                "1007779304",
                1,
                "Hol4Mund0"
        );

        Persona persona = new Persona(
                1,
                "MIGUEL ANGEL",
                "PECHENE PECHENE",
                LocalDate.of(2000, 5, 17),
                "PT MADERO",
                "3023011900",
                "miguelpechene72@gmail.com",
                new BigDecimal("7000000"),
                1,
                "1007779304",
                1,
                "Hol4Mund0"
        );

        when(repository.findById(1)).thenReturn(Mono.just(personaEntity));
        when(mapper.map(personaEntity, Persona.class)).thenReturn(persona);

        Mono<Persona> result = repositoryAdapter.findById(1);

        StepVerifier.create(result)
                .assertNext(personaConsulta -> {
                    assertNotNull(personaConsulta);
                    assertEquals(1, personaConsulta.getId());
                })
                .verifyComplete();
    }

    /*
    @Test
    void mustFindAllValues() {
        when(repository.findAll()).thenReturn(Flux.just(personaEntity));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Flux<Object> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

    @Test
    void mustFindByExample() {
        when(repository.findAll(any(Example.class))).thenReturn(Flux.just("test"));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Flux<Object> result = repositoryAdapter.findByExample("test");

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        when(repository.save(personaEntity)).thenReturn(Mono.just(personaEntity));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Mono<Object> result = repositoryAdapter.save("test");

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

     */
}
