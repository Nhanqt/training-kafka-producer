package com.amaris.utils.filter;

import com.amaris.entity.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Builder
public class CustomUserDetail implements UserDetails {

    @Getter
    private int id;

    @Getter
    private String password;

    @Getter
    private String email;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetail(AccountEntity account,  Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.password = account.getPassword();
        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
