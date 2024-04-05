package com.devsu.mapper;

import com.devsu.dto.request.MotionDTO;
import com.devsu.dto.request.MotionRequest;
import com.devsu.dto.response.MotionResponse;
import com.devsu.entity.MotionEntity;
import com.devsu.repository.AccountRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MotionMapper {

    @Mapping(source = "cuenta.id", target = "accountId")
    MotionResponse toMotionDTO(MotionEntity motionEntity);

    @Mapping(target = "id", ignore = true)
    MotionEntity toMotionEntity(MotionRequest motionRequest);

    @Mapping(target = "id", ignore = true)
    MotionEntity toMotionEntity2(MotionDTO motionRequest);

    @Mapping(target = "id", ignore = true)
    void updateMotionFromDto(MotionDTO motionRequest, @MappingTarget MotionEntity motionEntity);

    List<MotionResponse> toListMotionsDTO(List<MotionEntity> motionEntityList);

    }
