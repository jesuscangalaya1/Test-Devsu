package com.devsu.controller;

import com.devsu.consumers.dtos.AccountReportDTO;
import com.devsu.dto.request.AccountRequest;
import com.devsu.dto.response.AccountResponse;
import com.devsu.service.AccountService;
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
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "CUENTAS", description = "Operaciones permitidas sobre la entidad Cuenta")
public class AccountController {

    private final AccountService accountService;


    @Operation(summary = "Generar un reporte de cuenta por rango de fechas y nombre del cliente")
    @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente")
    @GetMapping(value = "/reportes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountReportDTO>> generateAccountReport(
            @RequestParam(value = "fechaInicio", defaultValue = "2024-04-03") String fechaInicio,
            @RequestParam(value = "fechaFin", defaultValue = "2024-04-04") String fechaFin,
            @RequestParam(value = "clienteName", defaultValue = "Jesus") String clienteName) {
        return ResponseEntity.ok(accountService.generateAccountReport(fechaInicio, fechaFin, clienteName));
    }

    @Operation(summary = "Crear una nueva cuenta")
    @ApiResponse(responseCode = "201", description = "Cuenta creado exitosamente")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResponse> created(@RequestBody @Valid AccountRequest accountRequest) throws ExecutionException, InterruptedException {
        AccountResponse createdClient = accountService.createAccount(accountRequest);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una cuenta existente por su ID")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResponse> updateClient(@RequestBody @Valid AccountRequest accountRequest, @Positive @PathVariable Long id) {
        return ResponseEntity.ok(accountService.updateAccount(accountRequest, id));
    }

    @Operation(summary = "Obtener la información de todos las cuentas")
    @ApiResponse(responseCode = "200", description = "Cuentas obtenidos exitosamente")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountResponse>> getAllClients() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @Operation(summary = "Obtener información de una cuenta por su ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResponse> getClientById(@Positive(message = "el ID solo acepta numeros positivos")
            @PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }


    @Operation(summary = "Eliminar una cuenta por su ID")
    @ApiResponse(responseCode = "204", description = "Cuenta eliminado exitosamente")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteClient(@Positive(message = "el ID solo acepta numeros positivos")
            @PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

}
