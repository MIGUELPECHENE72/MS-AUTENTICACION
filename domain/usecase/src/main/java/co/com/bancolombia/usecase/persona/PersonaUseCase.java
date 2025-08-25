package co.com.bancolombia.usecase.persona;

import co.com.bancolombia.model.persona.Persona;
import co.com.bancolombia.model.persona.gateways.PersonaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PersonaUseCase {

    private final PersonaRepository personaRepository;

    public Mono<Persona> save(Persona persona){
        return personaRepository.save(persona);
    }

    public Flux<Persona> getAll(){
        return personaRepository.findAll();
    }

    public Mono<Persona> getById(Integer id){
        return personaRepository.findById(id);
    }

}
