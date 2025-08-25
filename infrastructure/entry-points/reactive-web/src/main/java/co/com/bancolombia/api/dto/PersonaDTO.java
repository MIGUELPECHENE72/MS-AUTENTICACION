package co.com.bancolombia.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PersonaDTO(Integer id,
                         String nombres,
                         String apellidos,
                         LocalDate fechaNacimiento,
                         String direccion,
                         String telefono,
                         String correoElectronico,
                         BigDecimal salarioBase) {
}
