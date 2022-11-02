package com.amaris.service.mapper;


import com.amaris.dto.request.AccountRoleDto;
import com.amaris.entity.AccountRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountRoleMapper {
    AccountRole toEntity(AccountRoleDto accountRoleDto);
}
