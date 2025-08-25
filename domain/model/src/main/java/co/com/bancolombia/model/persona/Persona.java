package co.com.bancolombia.model.persona;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Persona {

    private Integer id;

    private String nombres;

    private String apellidos;

    private LocalDate fechaNacimiento;

    private String direccion;

    private String telefono;

    private String correoElectronico;

    private Long salarioBase;

}
