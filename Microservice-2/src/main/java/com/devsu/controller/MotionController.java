package com.devsu.controller;

import com.devsu.dto.request.MotionDTO;
import com.devsu.dto.request.MotionRequest;
import com.devsu.dto.response.MotionResponse;
import com.devsu.service.MotionService;
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
@RequestMapping("/api/motions")
@RequiredArgsConstructor
@Tag(name = "MOVIMIENTO", description = "Operaciones permitidas sobre la entidad Movimiento")
public class MotionController {

    private final MotionService motionService;

    @Operation(summary = "Crear un nuevo Movimiento")
    @ApiResponse(responseCode = "201", description = "Movimiento creado exitosamente")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MotionResponse> createMotion(@RequestBody @Valid MotionDTO motionRequest) {
        MotionResponse createdClient = motionService.createMotion(motionRequest);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un Movimiento existente por su ID")
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MotionResponse> updateMotion(@RequestBody @Valid MotionDTO motionRequest, @Positive @PathVariable Long id) {
        return ResponseEntity.ok(motionService.updateMotion(motionRequest, id));
    }

    @Operation(summary = "Obtener la información de todos los Movimientos")
    @ApiResponse(responseCode = "200", description = "Movimientos obtenidos exitosamente")
    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MotionResponse>> getAllMotions() {
        return ResponseEntity.ok(motionService.getAllMotions());
    }

    @Operation(summary = "Obtener información de un Movimiento por su ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MotionResponse> getMotionById(@Positive(message = "el ID solo acepta numeros positivos")
            @PathVariable Long id) {
        return ResponseEntity.ok(motionService.getMotionById(id));
    }



}
