package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.CreatePersonaDTO;
import co.com.bancolombia.api.dto.EditPersonaDTO;
import co.com.bancolombia.api.dto.PersonaDTO;
import co.com.bancolombia.api.util.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios/{id}",
                    produces = { "application/json" },
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "listenGetPersonaById",
                    operation = @Operation(
                            operationId = "getPersonaById",
                            summary = "Obtener una Persona por ID",
                            tags = { "usuarios" },
                            parameters = {
                                    @Parameter(
                                            name = "id",
                                            description = "ID de la persona",
                                            required = true,
                                            in = ParameterIn.PATH,
                                            example = "1"
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Solicitud exitosa",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = PersonaDTO.class),
                                                    examples = @ExampleObject(
                                                            name = "Ejemplo de respuesta",
                                                            summary = "Solicitud válida",
                                                            value = """
                                                            {
                                                                "id": 1,
                                                                "nombres": "MIGUEL ANGEL",
                                                                "apellidos": "PECHENE PECHENE",
                                                                "fechaNacimiento": "2000-05-17",
                                                                "direccion": "PT MADERO",
                                                                "telefono": "3023011900",
                                                                "correoElectronico": "miguelpechene72@gmail.com",
                                                                "salarioBase": 5500000,
                                                                "idTipoDocumento": 1,
                                                                "identificacion": "1007779304",
                                                                "idRol": 1
                                                            }
                                                            """
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios/identificacion/{identificacion}",
                    produces = { "application/json" },
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "listenGetPersonaByIdentificacion",
                    operation = @Operation(
                            operationId = "getPersonaByIdentificacion",
                            summary = "Obtener una Persona por identificación",
                            tags = { "usuarios" },
                            parameters = {
                                    @Parameter(
                                            name = "identificacion",
                                            description = "identificación de la persona",
                                            required = true,
                                            in = ParameterIn.PATH,
                                            example = "1007779304"
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Solicitud exitosa",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = PersonaDTO.class),
                                                    examples = @ExampleObject(
                                                            name = "Ejemplo de respuesta",
                                                            summary = "Solicitud válida",
                                                            value = """
                                                            {
                                                                "id": 1,
                                                                "nombres": "MIGUEL ANGEL",
                                                                "apellidos": "PECHENE PECHENE",
                                                                "fechaNacimiento": "2000-05-17",
                                                                "direccion": "PT MADERO",
                                                                "telefono": "3023011900",
                                                                "correoElectronico": "miguelpechene72@gmail.com",
                                                                "salarioBase": 5500000,
                                                                "idTipoDocumento": 1,
                                                                "identificacion": "1007779304",
                                                                "idRol": 1
                                                            }
                                                            """
                                                    )
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Solicitud no encontrada",
                                            content = @Content(
                                                mediaType = "application/json",
                                                schema = @Schema(implementation = ErrorResponse.class),
                                                examples = @ExampleObject(
                                                name = "Ejemplo de respuesta",
                                                        summary = "Solicitud válida",
                                                        value = """
                                                            {
                                                                "errorCode": "Not Found",
                                                                "message": "No se encontraron personas con la identificación: 1007779302"
                                                            }
                                                        """
                                                    )
                                            )
                                    )
                            }
                    )
            ),
            /*
            @RouterOperation(
                    path = "/api/v1/solicitudDTO/{id}",
                    produces = { "application/json" },
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "listenGetSolicitudDTOById",
                    operation = @Operation(
                            operationId = "getSolicitudDTOById",
                            summary = "Obtener una solicitudDTO por ID",
                            tags = { "solicitud" },
                            parameters = {
                                    @Parameter(
                                            name = "id",
                                            description = "ID de la solicitud",
                                            required = true,
                                            in = ParameterIn.PATH,
                                            example = "1"
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Solicitud exitosa",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation =
                                                            co.com.bancolombia.usecase.solicitudDTO.dto
                                                                    .SolicitudDTO.class),
                                                    examples = @ExampleObject(
                                                            name = "Ejemplo de respuesta",
                                                            summary = "Solicitud válida",
                                                            value = """
                                                            {
                                                                "solicitud": {
                                                                    "id": 1,
                                                                    "identificacion": "1007779304",
                                                                    "monto": 5000000,
                                                                    "plazo": 12,
                                                                    "tipo": 1,
                                                                    "estado": 1
                                                                },
                                                                "tipo": {
                                                                    "id": 1,
                                                                    "nombre": "Credito Libre Consumo",
                                                                    "estado": "S"
                                                                },
                                                                "estado": {
                                                                    "id": 1,
                                                                    "nombre": "Pendiente de revisión",
                                                                    "estado": "S"
                                                                }
                                                            }
                                                            """
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
                            }
                    )
            ),

             */
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    produces = { "application/json" },
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "listenGetAllPersonas",
                    operation = @Operation(
                            operationId = "getAllPersonas",
                            summary = "Obtener todas las personas existentes en el sistema",
                            tags = { "usuarios" },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Solicitud exitosa",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = PersonaDTO.class),
                                                    examples = @ExampleObject(
                                                            name = "Ejemplo de respuesta",
                                                            summary = "Solicitud válida",
                                                            value = """
                                                            [
                                                                {
                                                                    "id": 1,
                                                                    "nombres": "MIGUEL ANGEL",
                                                                    "apellidos": "PECHENE PECHENE",
                                                                    "fechaNacimiento": "2000-05-17",
                                                                    "direccion": "PT MADERO",
                                                                    "telefono": "3023011900",
                                                                    "correoElectronico": "miguelpechene72@gmail.com",
                                                                    "salarioBase": 5500000,
                                                                    "idTipoDocumento": 1,
                                                                    "identificacion": "1007779304",
                                                                    "idRol": 1
                                                                },
                                                                {
                                                                    "id": 2,
                                                                    "nombres": "JUAN MIGUEL",
                                                                    "apellidos": "PEREZ PECHENE",
                                                                    "fechaNacimiento": "1984-10-20",
                                                                    "direccion": "CARRERA 10A # 2 - 73",
                                                                    "telefono": "3023011901",
                                                                    "correoElectronico": "miguelpechene71@gmail.com",
                                                                    "salarioBase": 2100000,
                                                                    "idTipoDocumento": 1,
                                                                    "identificacion": "1007779304",
                                                                    "idRol": 1
                                                                }
                                                            ]
                                                            """
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    produces = { "application/json" },
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "listenSavePersona",
                    operation = @Operation(
                            operationId = "savePersona",
                            summary = "Crear una persona",
                            tags = { "usuarios" },
                            requestBody = @RequestBody(
                                    description = "Datos necesarios para crear una nueva persona",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CreatePersonaDTO.class),
                                            examples = @ExampleObject(
                                                    name = "Ejemplo de body",
                                                    value = """
                                                    {
                                                        "nombres": "JUAN MIGUEL",
                                                        "apellidos": "PEREZ PECHENE",
                                                        "fechaNacimiento": "1984-10-20",
                                                        "direccion": "CARRERA 10A # 2 - 73",
                                                        "telefono": "3023011901",
                                                        "correoElectronico": "miguelpechene71@gmail.com",
                                                        "salarioBase": 2100000,
                                                        "idTipoDocumento": 1,
                                                        "identificacion": "1007779304",
                                                        "idRol": 1
                                                    }
                                                    """
                                            )
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Solicitud exitosa",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = PersonaDTO.class),
                                                    examples = @ExampleObject(
                                                            name = "Ejemplo de respuesta",
                                                            summary = "Solicitud válida",
                                                            value = """
                                                            {
                                                                "id": 2,
                                                                "nombres": "JUAN MIGUEL",
                                                                "apellidos": "PEREZ PECHENE",
                                                                "fechaNacimiento": "1984-10-20",
                                                                "direccion": "CARRERA 10A # 2 - 73",
                                                                "telefono": "3023011901",
                                                                "correoElectronico": "miguelpechene71@gmail.com",
                                                                "salarioBase": 2100000,
                                                                "idTipoDocumento": 1,
                                                                "identificacion": "1007779304",
                                                                "idRol": 1
                                                            }
                                                            """
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada"),
                                    @ApiResponse(responseCode = "400", description = "Error de validación")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    produces = { "application/json" },
                    method = RequestMethod.PUT,
                    beanClass = Handler.class,
                    beanMethod = "listenUpdatePersona",
                    operation = @Operation(
                            operationId = "UpdatePersona",
                            summary = "Editar una persona",
                            tags = { "usuarios" },
                            requestBody = @RequestBody(
                                    description = "Datos necesarios para editar una persona",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = EditPersonaDTO.class),
                                            examples = @ExampleObject(
                                                    name = "Ejemplo de body",
                                                    value = """
                                                    {
                                                        "id": 2,
                                                        "nombres": "JUAN MIGUEL",
                                                        "apellidos": "PEREZ PECHENE",
                                                        "fechaNacimiento": "1984-10-20",
                                                        "direccion": "CARRERA 10A # 2 - 73",
                                                        "telefono": "3023011901",
                                                        "correoElectronico": "miguelpechene71@gmail.com",
                                                        "salarioBase": 2100000,
                                                        "idTipoDocumento": 1,
                                                        "identificacion": "1007779304",
                                                        "idRol": 1
                                                    }
                                                    """
                                            )
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Solicitud exitosa",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = PersonaDTO.class),
                                                    examples = @ExampleObject(
                                                            name = "Ejemplo de respuesta",
                                                            summary = "Solicitud válida",
                                                            value = """
                                                            {
                                                                "id": 2,
                                                                "nombres": "JUAN MIGUEL",
                                                                "apellidos": "PEREZ PECHENE",
                                                                "fechaNacimiento": "1984-10-20",
                                                                "direccion": "CARRERA 10A # 2 - 73",
                                                                "telefono": "3023011901",
                                                                "correoElectronico": "miguelpechene71@gmail.com",
                                                                "salarioBase": 2100000,
                                                                "idTipoDocumento": 1,
                                                                "identificacion": "1007779304",
                                                                "idRol": 1
                                                            }
                                                            """
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada"),
                                    @ApiResponse(responseCode = "400", description = "Error de validación")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/v1/usuarios/{id}"), handler::listenGetPersonaById)
                .andRoute(POST("/api/v1/usuarios"), handler::listenSavePersona)
                .andRoute(PUT("/api/v1/usuarios"), handler::listenUpdatePersona)
                .andRoute(GET("/api/v1/usuarios/identificacion/{identificacion}"), handler::listenGetPersonaByIdentificacion)
                .andRoute(GET("/api/v1/usuarios"), handler::listenGetAllPersonas);
    }
}
