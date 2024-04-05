package com.devsu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
public class PersonRequest {

    @NotBlank(message = "El nombre no debe estar en blanco")
    @Size(max = 100, min = 2, message = "El nombre debe tener como máximo {max} caracteres y como mínimo {min} caracteres.")
    @Schema(description = "Nombre de la persona", example = "Jesus")
    private String name;

    @NotBlank(message = "El género no debe estar en blanco")
    @Schema(description = "Género de la persona", example = "M")
    private String gender;

    @NotNull(message = "La edad no puede ser nula")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 150, message = "La edad no puede ser mayor a {value}")
    @Schema(description = "Edad de la persona", example = "23")
    private Integer age;

    @NotBlank(message = "La identificación no debe estar en blanco")
    @Schema(description = "Identificación de la persona", example = "123456789")
    private String identification;

    @NotBlank(message = "La dirección no debe estar en blanco")
    @Schema(description = "Dirección de la persona", example = "Los olivos")
    private String address;

    @NotNull(message = "El teléfono no puede ser nulo")
    @Schema(description = "Teléfono de la persona", example = "1234567890")
    private Integer phone;
}
