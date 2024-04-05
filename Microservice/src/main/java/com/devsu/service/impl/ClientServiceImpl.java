package com.devsu.service.impl;

import com.devsu.dto.request.ClientRequest;
import com.devsu.dto.response.ClientResponse;
import com.devsu.entity.ClientEntity;
import com.devsu.entity.PersonEntity;
import com.devsu.exceptions.BusinessException;
import com.devsu.mapper.ClientMapper;
import com.devsu.mapper.PersonMapper;
import com.devsu.repository.ClientRepository;
import com.devsu.repository.PersonRepository;
import com.devsu.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.devsu.util.AppConstants.BAD_REQUEST;
import static com.devsu.util.AppConstants.BAD_REQUEST_CLIENT;

@Service
public class ClientServiceImpl implements ClientService {


    private final ClientRepository clientRepository;


    private final PersonRepository personRepository;


    private final ClientMapper clientMapper;


    private final PersonMapper personMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,
                             PersonRepository personRepository,
                             ClientMapper clientMapper,
                             PersonMapper personMapper) {
        this.clientRepository = clientRepository;
        this.personRepository = personRepository;
        this.clientMapper = clientMapper;
        this.personMapper = personMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> getAllClients() {
        List<ClientEntity> clientEntities = clientRepository.findAllByStateTrue();
        return clientEntities.stream()
                .map(clientMapper::toClientDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse getClientById(Long id) {
        ClientEntity clientEntity = clientRepository.findByClientIdAndStateTrue(id)
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_CLIENT + id));

        return clientMapper.toClientDTO(clientEntity);
    }

    @Override
    @Transactional
    public ClientResponse createClient(ClientRequest clientRequest) {
        PersonEntity personEntity = personMapper.toPersonEntity(clientRequest.getPerson());
        personRepository.save(personEntity);
        ClientEntity clientEntity = clientMapper.toClientEntity(clientRequest);
        clientEntity.setPerson(personEntity);
        clientRepository.save(clientEntity);
        return clientMapper.toClientDTO(clientEntity);
    }

    @Override
    @Transactional
    public ClientResponse updateClient(ClientRequest clientRequest, Long id) {
        // Buscar el producto existente por su ID
        ClientEntity clientEntity = clientRepository.findByClientIdAndStateTrue(id)
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_CLIENT + id));
        if (clientEntity != null) {
            clientMapper.updateClientFromDto(clientRequest, clientEntity);
            clientRepository.save(clientEntity);
        }
        return clientMapper.toClientDTO(clientEntity);
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        Optional<ClientEntity> client = clientRepository.findByClientIdAndStateTrue(id);
        if (!client.isPresent()) {
            throw new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_CLIENT + id);
        }
        // Desactivar (eliminar lógicamente) un cliente por su ID
        clientRepository.desactivarClient(id);
    }

}
