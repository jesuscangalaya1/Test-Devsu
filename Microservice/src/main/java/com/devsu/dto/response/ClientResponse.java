package com.devsu.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientResponse {

    private Long clientId;
    private boolean state;

    private String password;
    private PersonResponse person;
}
