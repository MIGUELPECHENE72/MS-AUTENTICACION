package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.CreatePersonaDTO;
import co.com.bancolombia.api.dto.EditPersonaDTO;
import co.com.bancolombia.api.dto.PersonaDTO;
import co.com.bancolombia.model.persona.Persona;
import org.mapstruct.Mapper;
import reactor.core.publisher.Mono;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonaDTOMapper {

    PersonaDTO toResponse(Persona persona);

    List<PersonaDTO> toResponseList(List<Persona> personas);

    Persona toModel(CreatePersonaDTO createPersonaDTO);

    Persona toModel(EditPersonaDTO editPersonaDTO);

}
