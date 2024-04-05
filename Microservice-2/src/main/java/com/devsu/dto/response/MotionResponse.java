package com.devsu.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class MotionResponse {
    private Long id;
    private LocalDate date;
    private String movementType;
    private double value;
    private double balance;
    private Long accountId; // ID de la cuenta que realizó el movimiento


}
