package com.devsu.controller;

import com.devsu.dto.request.ClientRequest;
import com.devsu.dto.response.ClientResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllClients() throws Exception {
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetClientById() throws Exception {
        mockMvc.perform(get("/api/clients/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateClient() throws Exception {
        ClientRequest clientRequest = new ClientRequest();
        // Set properties for clientRequest

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateClient() throws Exception {
        ClientRequest clientRequest = new ClientRequest();
        // Set properties for clientRequest

        mockMvc.perform(put("/api/clients/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteClient() throws Exception {
        mockMvc.perform(delete("/api/clients/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
