package com.devsu.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonResponse {

    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private Integer phone;
}
