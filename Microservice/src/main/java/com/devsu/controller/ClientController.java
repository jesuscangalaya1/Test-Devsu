package com.devsu.controller;

import com.devsu.dto.request.ClientRequest;
import com.devsu.dto.response.ClientResponse;
import com.devsu.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "CLIENTE", description = "Operaciones permitidas sobre la entidad Cliente")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Crear un nuevo cliente")
    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientResponse> created(@RequestBody @Valid ClientRequest clientRequest) {
        ClientResponse createdClient = clientService.createClient(clientRequest);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un cliente existente por su ID")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientResponse> updateClient(@RequestBody @Valid ClientRequest clientRequest, @Positive @PathVariable Long id) {
        return ResponseEntity.ok(clientService.updateClient(clientRequest, id));
    }

    @Operation(summary = "Obtener la información de todos los clientes paginados")
    @ApiResponse(responseCode = "200", description = "Clientes obtenidos exitosamente")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @Operation(summary = "Obtener información de un cliente por su ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientResponse> getClientById(@Positive(message = "el ID solo acepta numeros positivos")
            @PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }


    @Operation(summary = "Eliminar un cliente por su ID")
    @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteClient(@Positive(message = "el ID solo acepta numeros positivos")
            @PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
