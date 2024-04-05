package com.devsu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {

    @NotBlank(message = "La contraseña no debe estar en blanco")
    @Size(min = 2, message = "La contraseña debe tener al menos {min} caracteres.")
    @Schema(description = "Contraseña del cliente", example = "password123")
    private String password;

    @NotNull(message = "La información de la persona no puede ser nula")
    @Schema(description = "Información de la persona asociada al cliente")
    private PersonRequest person;

}
