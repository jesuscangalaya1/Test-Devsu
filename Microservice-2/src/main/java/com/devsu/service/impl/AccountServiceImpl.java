package com.devsu.service.impl;

import com.devsu.consumers.ClientPersonFeign;
import com.devsu.consumers.dtos.AccountReportDTO;
import com.devsu.consumers.dtos.ClientDTO;
import com.devsu.dto.request.AccountRequest;
import com.devsu.dto.response.AccountResponse;
import com.devsu.dto.response.MotionResponse;
import com.devsu.entity.AccountEntity;
import com.devsu.exceptions.BusinessException;
import com.devsu.mapper.AccountMapper;
import com.devsu.mapper.MotionMapper;
import com.devsu.repository.AccountRepository;
import com.devsu.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.devsu.util.AppConstants.*;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final MotionMapper motionMapper;
    private final ClientPersonFeign clientPersonFeign;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountMapper accountMapper, MotionMapper motionMapper, ClientPersonFeign clientPersonFeign) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.motionMapper = motionMapper;
        this.clientPersonFeign = clientPersonFeign;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountReportDTO> generateAccountReport(String fechaInicio, String fechaFin, String clienteName) {
        LocalDate fechaInicioLocalDate;
        LocalDate fechaFinLocalDate;
        try {
            fechaInicioLocalDate = LocalDate.parse(fechaInicio);
            fechaFinLocalDate = LocalDate.parse(fechaFin);
        } catch (DateTimeParseException e) {
            throw new BusinessException("Las fechas proporcionadas no son válidas", HttpStatus.BAD_REQUEST, BAD_REQUEST_INVALID_DATE_FORMAT);
        }

        // Obtener todos los clientes desde el otro microservicio
        List<ClientDTO> clientes = clientPersonFeign.getAllClients();

        // Filtrar la lista para encontrar el cliente con el nombre que estamos buscando
        ClientDTO cliente = clientes.stream()
                .filter(c -> c.getPerson().getName().equalsIgnoreCase(clienteName))
                .findFirst()
                .orElseThrow(() -> new BusinessException("El cliente con nombre " + clienteName + " no existe", HttpStatus.BAD_REQUEST, BAD_REQUEST_CLIENT_NOT_FOUND));

        // Recuperar las cuentas para el cliente
        List<AccountEntity> accounts = accountRepository.findByClientId(cliente.getClientId());

        // Usar programación funcional para generar los reportes
        List<AccountReportDTO> reportes = accounts.stream()
                .flatMap(account -> account.getMovimientos().stream()
                        .filter(motion -> motion.getDate() != null && !motion.getDate().isBefore(fechaInicioLocalDate) && !motion.getDate().isAfter(fechaFinLocalDate))
                        .map(motion -> AccountReportDTO.builder()
                                .fecha(motion.getDate().toString())
                                .cliente(cliente.getPerson().getName())
                                .numeroCuenta(account.getAccountNumber().toString())
                                .tipo(account.getAccountType())
                                .saldoInicial(account.getInitialBalance())
                                .estado(account.isState())
                                .movimiento(motion.getValue())
                                .saldoDisponible(motion.getBalance())
                                .build()))
                .collect(Collectors.toList());

        if (reportes.isEmpty()) {
            throw new BusinessException("No se encontraron movimientos en las fechas proporcionadas", HttpStatus.BAD_REQUEST, BAD_REQUEST_DATE_EMPTY);
        }

        return reportes;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAllByStateTrue().stream()
                .map(accountMapper::toAccountDTO)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(Long id) {
        AccountEntity accountEntity = accountRepository.findByIdAndStateTrue(id)
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_ACOUNT+ id));

        return accountMapper.toAccountDTO(accountEntity);
    }
    @Override
    @Transactional
    public AccountResponse createAccount(AccountRequest accountRequest) throws ExecutionException, InterruptedException {
        // Validar que el cliente existe
        ClientDTO cliente = clientPersonFeign.getClientById(accountRequest.getClientId());
        log.info("Respuesta del microservicio de Cliente-Persona: {}", cliente);

        if (cliente == null) {
            log.error("El cliente con id {} no existe", accountRequest.getClientId());
            throw new BusinessException("El cliente no existe", HttpStatus.BAD_REQUEST, BAD_REQUEST_CLIENT_NOT_FOUND);
        }
        log.info("El cliente con id {} existe", accountRequest.getClientId());

        // Crear la cuenta
        AccountEntity accountEntity = accountMapper.toAccountEntity(accountRequest);
        accountEntity = accountRepository.save(accountEntity);
        return accountMapper.toAccountDTO(accountEntity);
    }
    @Override
    @Transactional
    public AccountResponse updateAccount(AccountRequest accountRequest, Long id) {
        AccountEntity accountEntity = accountRepository.findByIdAndStateTrue(id)
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_ACOUNT+ id));

        accountMapper.updateAccountFromDto(accountRequest, accountEntity);
        accountEntity = accountRepository.save(accountEntity);
        return accountMapper.toAccountDTO(accountEntity);
    }
    @Override
    @Transactional
    public void deleteAccount(Long id) {
        Optional<AccountEntity> account = accountRepository.findByIdAndStateTrue(id);
        if (!account.isPresent()) {
            throw new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_ACOUNT+ id);
        }
        accountRepository.desactivarAccount(id);
    }
    @Override
    public boolean checkBalance(Long accountId, double amount) {
        AccountEntity accountEntity = accountRepository.findByIdAndStateTrue(accountId)
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_ACOUNT+ accountId));
        return accountEntity.getInitialBalance() >= amount;
    }

}
