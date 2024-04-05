package com.devsu.service.impl;

import com.devsu.dto.request.MotionDTO;
import com.devsu.dto.request.MotionRequest;
import com.devsu.dto.response.MotionResponse;
import com.devsu.entity.AccountEntity;
import com.devsu.entity.MotionEntity;
import com.devsu.exceptions.BusinessException;
import com.devsu.mapper.MotionMapper;
import com.devsu.repository.AccountRepository;
import com.devsu.repository.MotionRepository;
import com.devsu.service.AccountService;
import com.devsu.service.MotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.devsu.util.AppConstants.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class MotionServiceImpl implements MotionService {

    private final MotionRepository motionRepository;
    private final AccountRepository accountRepository;
    private final MotionMapper motionMapper;
    private final AccountService accountService;

    @Autowired
    public MotionServiceImpl(MotionRepository motionRepository,
                             AccountRepository accountRepository,
                             MotionMapper motionMapper,
                             AccountService accountService) {
        this.motionRepository = motionRepository;
        this.accountRepository = accountRepository;
        this.motionMapper = motionMapper;
        this.accountService = accountService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MotionResponse> getAllMotions() {
        return motionRepository.findAll().stream()
                .map(motionMapper::toMotionDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MotionResponse getMotionById(Long id) {
        MotionEntity motionEntity = motionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_MOTION+ id));

        return motionMapper.toMotionDTO(motionEntity);
    }

    @Override
    @Transactional
    public MotionResponse createMotion(MotionDTO motionRequest) {
        if (!accountService.checkBalance(motionRequest.getCuenta_id(), motionRequest.getValue())) {
            throw new BusinessException("Saldo no disponible", HttpStatus.BAD_REQUEST, "El saldo de la cuenta no es suficiente para realizar la transacción");
        }
        // Buscar la AccountEntity usando accountId
        AccountEntity accountEntity = accountRepository.findByIdAndStateTrue(motionRequest.getCuenta_id())
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_ACOUNT + id));

        // Mapear MotionRequest a MotionEntity
        MotionEntity motionEntity = motionMapper.toMotionEntity2(motionRequest);
        // Establecer la AccountEntity en la MotionEntity
        motionEntity.setCuenta(accountEntity);
        // Establecer la fecha actual
        motionEntity.setDate(LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)));
        // Obtener el saldo actualizado de la cuenta
        double updatedBalance = accountRepository.findById(motionRequest.getCuenta_id())
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_ACOUNT + id))
                .getInitialBalance() - motionRequest.getValue();
        // Establecer el balance en la MotionEntity
        motionEntity.setBalance(updatedBalance);
        // Guardar la MotionEntity
        motionEntity = motionRepository.save(motionEntity);

        // Devolver la MotionResponse con el balance actualizado
        MotionResponse motionResponse = motionMapper.toMotionDTO(motionEntity);
        motionResponse.setBalance(updatedBalance);
        return motionResponse;
    }
    @Override
    @Transactional
    public MotionResponse updateMotion(MotionDTO motionRequest, Long id) {
        MotionEntity motionEntity = motionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_MOTION + id));
        motionMapper.updateMotionFromDto(motionRequest, motionEntity);
        motionEntity = motionRepository.save(motionEntity);
        return motionMapper.toMotionDTO(motionEntity);
    }

    @Override
    @Transactional
    public void deleteMotion(Long id) {
        motionRepository.deleteById(id);
    }
}
