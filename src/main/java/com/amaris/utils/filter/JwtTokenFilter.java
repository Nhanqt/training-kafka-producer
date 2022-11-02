package com.amaris.utils.filter;

import com.amaris.entity.AccountEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    final JwtTokenUtil jwtTokenUtil;
    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(!hasAuthorizationHeader(request)){
            filterChain.doFilter(request, response);
            return;
        }
        String accessToken = getAccessToken(request);
        if(!jwtTokenUtil.validationAccessToken(accessToken)){
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContext(accessToken,request);
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationContext(String accessToken, HttpServletRequest request) {
        CustomUserDetail userDetails = jwtTokenUtil.getUserDetail(accessToken);
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, accessToken, userDetails.getAuthorities()

        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private boolean hasAuthorizationHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }
        return true;
    }

    private String getAccessToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }
}
