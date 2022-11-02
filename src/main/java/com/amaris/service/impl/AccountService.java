package com.amaris.service.impl;


import com.amaris.config.SecurityConfig;
import com.amaris.dto.request.AccountDto;
import com.amaris.dto.request.AccountRoleDto;
import com.amaris.dto.request.CreateAccountDto;
import com.amaris.entity.AccountEntity;
import com.amaris.entity.AccountRole;
import com.amaris.entity.RoleEntity;
import com.amaris.exception.exceptions.NotAllowException;
import com.amaris.repository.AccountRepository;
import com.amaris.repository.AccountRoleRepository;
import com.amaris.repository.RoleRepository;
import com.amaris.service.AccountInterface;
import com.amaris.service.mapper.AccountMapper;
import com.amaris.service.mapper.AccountRoleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.amaris.common.Constants.*;

@Service
@Transactional(readOnly = true)
public class AccountService implements AccountInterface {

    private final SecurityConfig securityConfig;
    private final AccountRepository accountRepository;

    private final AccountRoleRepository accountRoleRepository;
    private final RoleRepository roleRepository;
    private final AccountMapper accountMapper;

    private final AccountRoleMapper accountRoleMap;

    public AccountService(RoleRepository roleRepository, AccountRepository accountRepository, AccountMapper accountMapper, SecurityConfig securityConfig, AccountRoleRepository accountRoleRepository, AccountRoleMapper accountRoleMap) {
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.securityConfig = securityConfig;
        this.accountRoleRepository = accountRoleRepository;
        this.accountRoleMap = accountRoleMap;
    }

    public void checkExistEmail(String email){
        accountRepository.findByEmail(email).orElseThrow(() -> new NotAllowException(EMAIL_NOT_EXIST));
    }
    @Override
    public boolean verifyAccount(String email, String password) {
        for (AccountEntity account : accountRepository.findAll()) {
            if (email.equals(account.getEmail()) && securityConfig.passwordEncoder().matches(password, account.getPassword()))
                return true;
        }

        return false;
    }
    @Override
    @Transactional
    public AccountDto createAccount(CreateAccountDto accountDto) {
        validateAccount(accountDto.getEmail(), accountDto.getId());
        String password = securityConfig.passwordEncoder().encode(accountDto.getPassword());
        accountDto.setCreatedDate(LocalDateTime.now());
        accountDto.setPassword(password);
        AccountEntity account = accountMapper.createToEntity(accountDto);
        AccountEntity accountEntity = accountRepository.save(account);
        for (Integer roleId : accountDto.getRoleIds()) {
            AccountRole accountRole = new AccountRole();
            accountRole.setAccountId(accountEntity.getId());
            accountRole.setRoleId(roleId);
            accountRoleRepository.save(accountRole);
        }
        return accountMapper.toDto(accountEntity);
    }

    private void validateAccount(String email, int id){
        accountRepository.findByEmail(email).ifPresent((isExistEmail -> {
            throw new NotAllowException(EMAIL_EXIST);
        }));
        accountRepository.findById(id).ifPresent((isExistAccountId -> {
            throw new NotAllowException(ID_EXIST);
        }));
    }
    @Override
    @Transactional
    public Page<AccountDto> getAll(String firstname, String lastname, String email, String phone, String accountNumber) {
        return null;
    }

    @Override
    public List<AccountDto> getAll() {
        List<AccountEntity> accountEntities = accountRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<AccountDto> accountDto = accountMapper.toDtoList(accountEntities);
        return accountDto;
    }

    @Override
    @Transactional
    public AccountDto updateAccount(AccountDto accountDto) {
        accountDto.setUpdatedDate(LocalDateTime.now());
        AccountEntity account = accountMapper.toEntity(accountDto);

        AccountEntity accountEntity = accountRepository.save(account);

        return accountMapper.toDto(accountEntity);
    }


}
