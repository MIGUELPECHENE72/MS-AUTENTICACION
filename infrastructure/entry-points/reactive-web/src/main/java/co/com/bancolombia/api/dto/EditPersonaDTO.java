package co.com.bancolombia.api.dto;

import java.time.LocalDate;

public record EditPersonaDTO(Integer id,
                             String nombres,
                             String apellidos,
                             LocalDate fechaNacimiento,
                             String direccion,
                             String telefono,
                             String correoElectronico,
                             Long salarioBase) {
}
