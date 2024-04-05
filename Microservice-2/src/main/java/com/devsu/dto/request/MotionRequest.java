package com.devsu.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MotionRequest {

    @Schema(description = "Fecha del movimiento", example = "2022-12-31")
    private LocalDate date;

    @Schema(description = "Tipo de movimiento", example = "Depósito")
    private String movementType;

    @Schema(description = "Valor del movimiento", example = "100.0")
    private double value;

    @Schema(description = "Saldo", example = "1000.0")
    private double balance;

    @JsonProperty("cuenta_id")
    @Schema(description = "ID de la cuenta", example = "1")
    private Long accountId;
}
