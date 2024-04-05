package com.devsu.mapper;

import com.devsu.dto.request.ClientRequest;
import com.devsu.dto.response.ClientResponse;
import com.devsu.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientResponse toClientDTO(ClientEntity clientEntity);

    @Mapping(target = "clientId", ignore = true) // Ignorar el mapeo del campo 'id'
    ClientEntity toClientEntity(ClientRequest clientRequest);

    @Mapping(target = "clientId", ignore = true)
    void updateClientFromDto(ClientRequest clientRequest, @MappingTarget ClientEntity productEntity);


    List<ClientResponse> toListClientsDTO(List<ClientEntity> clientEntityList);
}
