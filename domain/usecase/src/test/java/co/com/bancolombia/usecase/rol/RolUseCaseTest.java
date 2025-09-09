package co.com.bancolombia.usecase.rol;

import co.com.bancolombia.model.rol.Rol;
import co.com.bancolombia.model.rol.gateways.RolRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RolUseCaseTest {

    @InjectMocks
    RolUseCase rolUseCase;

    @Mock
    RolRepository rolRepository;

    private static final Integer TEST_ID = 1;

    Rol crearRol(Integer id){
        return new Rol(id,"ADMINISTRADOR","S");
    }

    @Test
    void mustGetById(){

        Rol rol = crearRol(TEST_ID);

        when(rolRepository.findById(TEST_ID)).thenReturn(Mono.just(rol));

        Mono<Rol> result = rolUseCase.getById(TEST_ID);

        StepVerifier.create(result)
                .assertNext(rolConsulta -> {
                    assertNotNull(rolConsulta);
                    assertEquals(TEST_ID, rolConsulta.getId());
                })
                .verifyComplete();
    }

}
