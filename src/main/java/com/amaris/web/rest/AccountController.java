package com.amaris.web.rest;


import com.amaris.dto.request.AccountDto;
import com.amaris.dto.request.CreateAccountDto;
import com.amaris.service.impl.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAll(){
        List<AccountDto> accountDtoList= accountService.getAll();
        return ResponseEntity.ok(accountDtoList);
    }

    @PostMapping()
    public ResponseEntity<CreateAccountDto> create(@Valid @RequestBody CreateAccountDto accountDto) {
        accountService.createAccount(accountDto);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping
    public ResponseEntity<AccountDto> update(@RequestBody @Valid AccountDto accountDto) {
        accountService.updateAccount(accountDto);
        return ResponseEntity.ok(accountDto);
    }
}
