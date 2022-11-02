package com.amaris.service.impl;


import com.amaris.entity.AccountEntity;
import com.amaris.entity.AccountRole;
import com.amaris.entity.RoleEntity;
import com.amaris.repository.AccountRepository;
import com.amaris.repository.AccountRoleRepository;
import com.amaris.utils.filter.CustomUserDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.amaris.common.Constants.ACCOUNT_NOT_FOUND;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    private final AccountRoleRepository accountRoleMapRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomUserDetailsServiceImpl(AccountRepository accountRepository, AccountRoleRepository accountRoleMapRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.accountRoleMapRepository = accountRoleMapRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity account = accountRepository.findAccountByEmail(username);

        if (account == null) {
            throw new NotFoundException(ACCOUNT_NOT_FOUND);
        } else {
            List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

            for (RoleEntity role : accountRoleMapRepository.findAllByAccountId(account.getId())) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }

            return new CustomUserDetail(account, grantedAuthorities);
        }
    }
}
