package co.com.bancolombia.usecase.persona;

import co.com.bancolombia.model.persona.Persona;
import co.com.bancolombia.model.persona.gateways.PersonaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class PersonaUseCase {

    private final PersonaRepository personaRepository;

    public Mono<Persona> create(Persona persona) {
        return personaRepository.existsByCorreoElectronico(persona.getCorreoElectronico())
                .flatMap(exist -> {
                    if (exist) {
                        return Mono.error(new IllegalArgumentException("El correo electrónico ya está registrado"));
                    }
                    return personaRepository.save(persona);
                });
    }

    public Mono<Persona> update(Persona persona) {
        return personaRepository.save(persona);
    }

    public Flux<Persona> getAll(){
        return personaRepository.findAll();
    }

    public Mono<Persona> getById(Integer id){
        return personaRepository.findById(id);
    }

    public Mono<Persona> getByIdentificacion(String id){
        return personaRepository.findByIdentificacion(id);
    }

    public Mono<Persona> getByCorreoElectronico(String correoElectronico){
        return personaRepository.findByCorreoElectronico(correoElectronico)
                .switchIfEmpty(Mono.error(new NoSuchElementException("No se ha encontrado la persona")));
    }

    public Mono<Persona> logueo(String email, String password){
        return getByCorreoElectronico(email)
                .flatMap(persona -> {
                    if(persona.getPassword().equals(password)){
                        return Mono.just(persona);
                    }else {
                        return Mono.error(new SecurityException("Credenciales invalidas."));
                    }
                });
    }

}
