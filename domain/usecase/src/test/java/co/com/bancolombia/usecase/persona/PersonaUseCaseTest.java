package co.com.bancolombia.usecase.persona;

import co.com.bancolombia.model.persona.Persona;
import co.com.bancolombia.model.persona.gateways.PersonaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonaUseCaseTest {

    @InjectMocks
    PersonaUseCase personaUseCase;

    @Mock
    PersonaRepository personaRepository;

    private static final Integer TEST_ID = 1;
    private static final String TEST_IDENTIFICACION = "1007779304";
    private static final String TEST_EMAIL = "miguelpechene72@gmail.com";
    private static final String TEST_PASSWORD = "HOl4MunD0";

    Persona crearPersona(Integer id){
        return new Persona(id,
                "MIGUEL ANGEL",
                "PECHENE PECHENE",
                LocalDate.now(),
                "PT MADERO",
                "3233820787",
                TEST_EMAIL,
                new BigDecimal("3000000"),
                1,
                TEST_IDENTIFICACION,
                3,
                TEST_PASSWORD);
    }

    @Test
    void mustCreate(){

        Persona persona = crearPersona(TEST_ID);

        when(personaRepository.existsByCorreoElectronico(TEST_EMAIL)).thenReturn(Mono.just(false));
        when(personaRepository.save(persona)).thenReturn(Mono.just(persona));

        Mono<Persona> result = personaUseCase.create(persona);

        StepVerifier.create(result)
                .assertNext(personaConsulta -> {
                    assertEquals(TEST_ID, personaConsulta.getId());
                })
                .verifyComplete();

    }

    @Test
    void mustCreateError(){

        Persona persona = crearPersona(TEST_ID);

        when(personaRepository.existsByCorreoElectronico(TEST_EMAIL)).thenReturn(Mono.just(true));

        Mono<Persona> result = personaUseCase.create(persona);

        StepVerifier.create(result)
                .expectError(IllegalArgumentException.class)
                .verify();

    }

    @Test
    void mustUpdate(){

        Persona persona = crearPersona(TEST_ID);

        when(personaRepository.save(persona)).thenReturn(Mono.just(persona));

        Mono<Persona> result = personaUseCase.update(persona);

        StepVerifier.create(result)
                .assertNext(personaConsulta -> {
                    assertEquals(TEST_ID, personaConsulta.getId());
                })
                .verifyComplete();

    }

    @Test
    void mustGetAll(){

        Persona persona1 = crearPersona(TEST_ID);
        Persona persona2 = crearPersona(TEST_ID+1);
        Persona persona3 = crearPersona(TEST_ID+2);

        when(personaRepository.findAll()).thenReturn(Flux.just(persona1,persona2,persona3));

        Flux<Persona> result = personaUseCase.getAll();

        StepVerifier.create(result)
                .assertNext(personaConsulta -> {
                    assertNotNull(personaConsulta);
                    assertEquals(TEST_ID, personaConsulta.getId());
                })
                .assertNext(personaConsulta -> {
                    assertNotNull(personaConsulta);
                    assertEquals(TEST_ID+1, personaConsulta.getId());
                })
                .assertNext(personaConsulta -> {
                    assertNotNull(personaConsulta);
                    assertEquals(TEST_ID+2, personaConsulta.getId());
                })
                .verifyComplete();

    }

    @Test
    void mustGetById(){

        Persona persona = crearPersona(TEST_ID);

        when(personaRepository.findById(TEST_ID)).thenReturn(Mono.just(persona));

        Mono<Persona> result = personaUseCase.getById(TEST_ID);

        StepVerifier.create(result)
                .assertNext(personaConsulta -> {
                    assertNotNull(personaConsulta);
                    assertEquals(TEST_ID, personaConsulta.getId());
                })
                .verifyComplete();

    }

    @Test
    void mustGetByIdentificacion(){

        Persona persona = crearPersona(TEST_ID);

        when(personaRepository.findByIdentificacion(TEST_IDENTIFICACION)).thenReturn(Mono.just(persona));

        Mono<Persona> result = personaUseCase.getByIdentificacion(TEST_IDENTIFICACION);

        StepVerifier.create(result)
                .assertNext(personaConsulta -> {
                    assertNotNull(personaConsulta);
                    assertEquals(TEST_IDENTIFICACION, personaConsulta.getIdentificacion());
                })
                .verifyComplete();

    }

    @Test
    void mustGetByCorreoElectronico(){

        Persona persona = crearPersona(TEST_ID);

        when(personaRepository.findByCorreoElectronico(TEST_EMAIL)).thenReturn(Mono.just(persona));

        Mono<Persona> result = personaUseCase.getByCorreoElectronico(TEST_EMAIL);

        StepVerifier.create(result)
                .assertNext(personaConsulta -> {
                    assertNotNull(personaConsulta);
                    assertEquals(TEST_EMAIL, personaConsulta.getCorreoElectronico());
                })
                .verifyComplete();

    }

    @Test
    void mustGetByCorreoElectronicoError(){

        when(personaRepository.findByCorreoElectronico(TEST_EMAIL)).thenReturn(Mono.empty());

        Mono<Persona> result = personaUseCase.getByCorreoElectronico(TEST_EMAIL);

        StepVerifier.create(result)
                .expectError(NoSuchElementException.class)
                .verify();

    }

    @Test
    void mustLogueo(){

        Persona persona = crearPersona(TEST_ID);

        when(personaRepository.findByCorreoElectronico(TEST_EMAIL)).thenReturn(Mono.just(persona));

        Mono<Persona> result = personaUseCase.logueo(TEST_EMAIL,TEST_PASSWORD);

        StepVerifier.create(result)
                .assertNext(personaConsulta -> {
                    assertNotNull(personaConsulta);
                    assertEquals(TEST_EMAIL, personaConsulta.getCorreoElectronico());
                })
                .verifyComplete();

    }

    @Test
    void mustLogueoErrorPassword(){

        Persona persona = crearPersona(TEST_ID);

        when(personaRepository.findByCorreoElectronico(TEST_EMAIL)).thenReturn(Mono.just(persona));

        Mono<Persona> result = personaUseCase.logueo(TEST_EMAIL,"fnjdsnf");

        StepVerifier.create(result)
                .expectError(SecurityException.class)
                .verify();

    }

}
