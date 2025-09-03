package co.com.bancolombia.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "persona")
public class PersonaEntity {

    @Id
    private Integer id;

    private String nombres;

    private String apellidos;

    private LocalDate fechaNacimiento;

    private String direccion;

    private String telefono;

    private String correoElectronico;

    private BigDecimal salarioBase;

    @Column("id_tipo_documento")
    private Integer idTipoDocumento;

    private String identificacion;

    @Column("id_rol")
    private Integer idRol;

    private String password;

}
