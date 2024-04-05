package com.devsu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    @Schema(description = "Número de cuenta", example = "123456")
    private Long accountNumber;
    @Schema(description = "Tipo de cuenta", example = "Savings")
    private String accountType;
    @Schema(description = "Saldo inicial", example = "1000.0")
    private double initialBalance;

    @Schema(description = "ID del cliente", example = "1")
    private Long clientId;
}
