package co.com.bancolombia.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonaDTO{

    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;

    private LocalDate fechaNacimiento;

    private String direccion;

    private String telefono;

    @Email(message = "El email debe tener un formato valido")
    @NotBlank(message = "El email es obligatorio")
    private String correoElectronico;

    @NotNull(message = "El salario base es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El salario base debe ser mayor a cero")
    @DecimalMax(value = "15000001", inclusive = false, message = "El salario base debe ser menor o igual a 15000000")
    private BigDecimal salarioBase;

    @NotNull(message = "El tipo de documento es obligatorio")
    private Integer idTipoDocumento;

    @NotBlank(message = "La identificacion es obligatoria")
    private String identificacion;

    @NotNull(message = "El rol es obligatorio")
    private Integer idRol;

    private String password = "user4ppCrediY4";

}
