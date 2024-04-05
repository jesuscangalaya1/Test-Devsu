package com.devsu.service;

import com.devsu.consumers.ClientPersonFeign;
import com.devsu.consumers.dtos.AccountReportDTO;
import com.devsu.consumers.dtos.ClientDTO;
import com.devsu.consumers.dtos.PersonDTO;
import com.devsu.dto.request.AccountRequest;
import com.devsu.dto.response.AccountResponse;
import com.devsu.entity.AccountEntity;
import com.devsu.mapper.AccountMapper;
import com.devsu.mapper.MotionMapper;
import com.devsu.repository.AccountRepository;
import com.devsu.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private MotionMapper motionMapper;

    @Mock
    private ClientPersonFeign clientPersonFeign;

    @InjectMocks
    private AccountServiceImpl accountService;

    private ClientDTO clientDTO;
    private String fechaInicio;
    private String fechaFin;
    private String clienteName;

    @BeforeEach
    void setUp() {
        fechaInicio = "2024-04-04";
        fechaFin = "2024-04-04";
        clienteName = "jorge";
        clientDTO = new ClientDTO();
        clientDTO.setPerson(new PersonDTO());
        clientDTO.getPerson().setName(clienteName);
        clientDTO.setClientId(1L);
    }

    @Test
    @DisplayName("Should generate account report when valid data is provided")
    void shouldGenerateAccountReport() {
        // Arrange
        when(clientPersonFeign.getAllClients()).thenReturn(Collections.singletonList(clientDTO));
        when(accountRepository.findByClientId(clientDTO.getClientId())).thenReturn(new ArrayList<>());

        // Act
        List<AccountReportDTO> result = accountService.generateAccountReport(fechaInicio, fechaFin, clienteName);

        // Assert
        assertNotNull(result);
        verify(clientPersonFeign, times(1)).getAllClients();
        verify(accountRepository, times(1)).findByClientId(clientDTO.getClientId());
    }

    @Test
    @DisplayName("Should return all accounts when getAllAccounts is called")
    void shouldGetAllAccounts() {
        // Arrange
        when(accountRepository.findAllByStateTrue()).thenReturn(new ArrayList<>());

        // Act
        List<AccountResponse> result = accountService.getAllAccounts();

        // Assert
        assertNotNull(result);
        verify(accountRepository, times(1)).findAllByStateTrue();
    }


    @Test
    @DisplayName("Should delete account when deleteAccount is called with valid id")
    void shouldDeleteAccount() {
        // Arrange
        Long id = 1L;
        when(accountRepository.findByIdAndStateTrue(id)).thenReturn(Optional.of(new AccountEntity()));

        // Act
        accountService.deleteAccount(id);

        // Assert
        verify(accountRepository, times(1)).findByIdAndStateTrue(id);
        verify(accountRepository, times(1)).desactivarAccount(id);
    }

    @Test
    @DisplayName("Should check balance when checkBalance is called with valid data")
    void shouldCheckBalance() {
        // Arrange
        Long accountId = 1L;
        double amount = 100.0;
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setInitialBalance(200.0);
        when(accountRepository.findByIdAndStateTrue(accountId)).thenReturn(Optional.of(accountEntity));

        // Act
        boolean result = accountService.checkBalance(accountId, amount);

        // Assert
        assertTrue(result);
        verify(accountRepository, times(1)).findByIdAndStateTrue(accountId);
    }
}
