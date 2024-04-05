package com.devsu.service;

import com.devsu.dto.request.ClientRequest;
import com.devsu.dto.response.ClientResponse;
import com.devsu.entity.ClientEntity;
import com.devsu.entity.PersonEntity;
import com.devsu.exceptions.BusinessException;
import com.devsu.mapper.ClientMapper;
import com.devsu.mapper.PersonMapper;
import com.devsu.repository.ClientRepository;
import com.devsu.repository.PersonRepository;
import com.devsu.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientEntity clientEntity;
    private ClientResponse clientResponse;
    private ClientRequest clientRequest;
    private PersonEntity personEntity;

    @BeforeEach
    public void setUp() {
        personEntity = new PersonEntity();
        clientEntity = new ClientEntity();
        clientEntity.setPerson(personEntity);
        clientResponse = new ClientResponse();
        clientRequest = new ClientRequest();
    }

    @Test
    public void testGetAllClients() {
        when(clientRepository.findAllByStateTrue()).thenReturn(Collections.singletonList(clientEntity));
        when(clientMapper.toClientDTO(clientEntity)).thenReturn(clientResponse);

        List<ClientResponse> result = clientService.getAllClients();

        assertEquals(1, result.size());
        verify(clientRepository, times(1)).findAllByStateTrue();
    }

    @Test
    public void testGetClientById() {
        when(clientRepository.findByClientIdAndStateTrue(1L)).thenReturn(Optional.of(clientEntity));
        when(clientMapper.toClientDTO(clientEntity)).thenReturn(clientResponse);

        ClientResponse result = clientService.getClientById(1L);

        assertEquals(clientResponse, result);
        verify(clientRepository, times(1)).findByClientIdAndStateTrue(1L);
    }

    @Test
    public void testGetClientByIdNotFound() {
        when(clientRepository.findByClientIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> clientService.getClientById(1L));
        verify(clientRepository, times(1)).findByClientIdAndStateTrue(1L);
    }

    // Continúa con las pruebas para los demás métodos en ClientServiceImpl
}
