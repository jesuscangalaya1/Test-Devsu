package com.devsu.service;

import com.devsu.dto.request.ClientRequest;
import com.devsu.dto.response.ClientResponse;

import java.util.List;

public interface ClientService {

    List<ClientResponse> getAllClients();

    ClientResponse getClientById(Long id);

    ClientResponse createClient(ClientRequest clientRequest);

    ClientResponse updateClient(ClientRequest clientRequest, Long id);

    void deleteClient(Long id);
}
