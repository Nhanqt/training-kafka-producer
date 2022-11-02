package com.amaris.service.mapper;


import com.amaris.dto.request.AccountDto;
import com.amaris.dto.request.CreateAccountDto;
import com.amaris.dto.request.LoginDto;
import com.amaris.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    CreateAccountDto createToDto(AccountEntity account);
    AccountEntity createToEntity(CreateAccountDto accountDto);
    AccountDto toDto(AccountEntity account);
    AccountEntity toEntity(AccountDto accountDto);
    List<AccountDto> toDtoList(List<AccountEntity> accountEntities);


    AccountEntity loginEntity(@MappingTarget AccountEntity account, LoginDto loginDto);
}
