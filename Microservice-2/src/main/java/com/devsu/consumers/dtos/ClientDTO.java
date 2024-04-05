package com.devsu.consumers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {
    private Long clientId;
    private String state;
    private String password;
    private PersonDTO person;
}
