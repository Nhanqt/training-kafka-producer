package com.amaris.service;


import com.amaris.dto.request.AccountDto;
import com.amaris.dto.request.CreateAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public interface AccountInterface {
    AccountDto createAccount(CreateAccountDto accountDto);
    Page<AccountDto> getAll(String firstname, String lastname, String email, String phone, String accountNumber);
    List<AccountDto> getAll();
    AccountDto updateAccount(AccountDto accountDto);
    boolean verifyAccount(String email, String password);

}
