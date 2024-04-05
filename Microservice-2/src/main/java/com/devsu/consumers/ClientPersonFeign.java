package com.devsu.consumers;

import com.devsu.consumers.dtos.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.Positive;
import java.util.List;

@FeignClient(name = "cliente-persona", url = "http://localhost:8080/api")
public interface ClientPersonFeign {

    @GetMapping( value ="/clients/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDTO getClientById(@Positive @PathVariable("id") Long id);

    @GetMapping(value ="/clients", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ClientDTO> getAllClients();
}
