package com.devsu.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AccountResponse {

    private Long id;
    private Long accountNumber;
    private String accountType;
    private double initialBalance;
    private boolean state;
    private Long clientId;
    private List<MotionResponse> movimientos;

}
