package com.devsu.mapper;

import com.devsu.dto.request.AccountRequest;
import com.devsu.dto.response.AccountResponse;
import com.devsu.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "movimientos", target = "movimientos")
    AccountResponse toAccountDTO(AccountEntity accountEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    AccountEntity toAccountEntity(AccountRequest accountRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    void updateAccountFromDto(AccountRequest accountRequest, @MappingTarget AccountEntity accountEntity);

}
