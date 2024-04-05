package com.devsu.mapper;

import com.devsu.dto.request.PersonRequest;
import com.devsu.dto.response.PersonResponse;
import com.devsu.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonResponse toPersonDTO(PersonEntity personEntity);

    @Mapping(target = "id", ignore = true)
    PersonEntity toPersonEntity(PersonRequest personRequest);

    @Mapping(target = "id", ignore = true)
    void updatePersonFromDto(PersonRequest personRequest, @MappingTarget PersonEntity personEntity);

}
