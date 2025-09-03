package co.com.bancolombia.usecase.persona;

import co.com.bancolombia.model.persona.Persona;
import co.com.bancolombia.model.persona.gateways.PersonaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

}
