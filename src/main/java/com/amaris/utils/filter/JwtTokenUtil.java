package com.amaris.utils.filter;

import com.amaris.dto.request.JwtDto;
import com.amaris.entity.AccountEntity;
import com.amaris.repository.AccountRepository;
import com.amaris.service.impl.AccountService;
import com.google.gson.Gson;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final long EXPIRE_DURATION = 24*60*60*1000;//24h

    private final AccountRepository accountRepository;

    public JwtTokenUtil(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private String secretKey = "nhanqt";
    public String generateAccessToken(AccountEntity account){
        String password = accountRepository.getPassword(account.getEmail());
        int accountId = accountRepository.getAccountId(account.getEmail());
        List<String> roleName = accountRepository.roleName(account.getEmail());
        JwtDto jwtDto = new JwtDto();
        jwtDto.setAccountId(accountId);
        jwtDto.setEmail(account.getEmail());
        jwtDto.setRoleName(roleName);
        jwtDto.setPassword(password);
        return Jwts.builder()
                .setSubject(String.valueOf(jwtDto.getAccountId()))
                .claim("email", jwtDto.getEmail())
                .claim("role", jwtDto.getRoleName())
                .claim("accountId", jwtDto.getAccountId())
                .setIssuer("TRAINING")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }

    public boolean validationAccessToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException ex){
            LOGGER.error("JWT expired",ex);
        }catch (IllegalArgumentException ex){
            LOGGER.error("Token is null, empty or has only whitespace",ex);
        }catch (MalformedJwtException ex){
            LOGGER.error("JWT is invalid",ex);
        }catch (UnsupportedJwtException ex){
            LOGGER.error("JWT is not supported",ex);
        }catch (SignatureException ex){
            LOGGER.error("Signature validation failed",ex);
        }
        return false;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public CustomUserDetail getUserDetail(String token) {

        Claims claims = parseClaims(token);
        return CustomUserDetail.builder()
                .email((String) claims.get("email"))
                .authorities(((List<String>) claims.get("role"))
                        .stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
                )
                .build();
    }


    private Claims parseClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
