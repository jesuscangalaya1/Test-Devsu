package com.devsu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MotionDTO {
    @Schema(description = "Tipo de movimiento", example = "Depósito")
    private String movementType;

    @Schema(description = "Valor del movimiento", example = "100.0")
    private double value;

    @Schema(description = "ID de la cuenta", example = "1")
    private Long cuenta_id;

}
